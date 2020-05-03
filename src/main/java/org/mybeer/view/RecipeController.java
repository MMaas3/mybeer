package org.mybeer.view;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.NumberStringConverter;
import org.hibernate.Session;
import org.mybeer.hibernate.RecipeDao;
import org.mybeer.hibernate.SessionFactorySingleton;
import org.mybeer.model.ingredient.Yeast;
import org.mybeer.model.mash.MashScheme;
import org.mybeer.model.mash.MashStep;
import org.mybeer.model.recipe.FermentableAddition;
import org.mybeer.model.recipe.HopAddition;
import org.mybeer.model.recipe.Recipe;
import org.mybeer.model.recipe.YeastAddition;

import java.math.BigDecimal;
import java.util.Optional;

public class RecipeController {
  @FXML
  private TextField nameField;
  @FXML
  private TextField boilTimeField;
  @FXML
  private TextField volumeField;
  private Recipe recipe;
  @FXML
  private TableView<FermentableAddition> fermetablesTable;
  @FXML
  private TableView<HopAddition> hopsTable;
  @FXML
  private TableView<YeastAddition> yeastTable;
  @FXML
  private TextField mashWaterField;
  @FXML
  private TextField spargeWaterField;
  @FXML
  private TextField thicknessField;
  @FXML
  private TableView<MashStep> mashTable;
  @FXML
  private Button backButton;

  public void init(Long recipeId, EventHandler<ActionEvent> actionEventEventHandler) {
    this.backButton.setOnAction(actionEventEventHandler);
    try (final Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
      final Optional<Recipe> recipeOptional = new RecipeDao().getById(recipeId, session);
      if(recipeOptional.isEmpty()) {
        throw new RuntimeException("Recipe does not exist");
      }

      this.recipe = recipeOptional.get();
      nameField.textProperty().bindBidirectional(new SimpleStringProperty(this.recipe.getName()));
      boilTimeField.textProperty()
                   .bindBidirectional(new SimpleIntegerProperty(this.recipe.getBoilTime()), new NumberStringConverter());
      volumeField.textProperty()
                 .bindBidirectional(new SimpleObjectProperty<>(this.recipe.getVolume()), new BigDecimalStringConverter());

      fillFermentablesTable();
      fillHopsTable();
      fillYeastTable();
      fillMashTab();
    }
  }

  private void fillMashTab() {
    final MashScheme mashScheme = recipe.getMashScheme();
    mashWaterField.textProperty().bindBidirectional(
        new SimpleObjectProperty<>(mashScheme.getMashWater()), new BigDecimalStringConverter()
    );
    spargeWaterField.textProperty().bindBidirectional(
        new SimpleObjectProperty<>(mashScheme.getSpargeWater()), new BigDecimalStringConverter()
    );
    thicknessField.textProperty().bindBidirectional(
        new SimpleObjectProperty<>(mashScheme.getThickness()), new BigDecimalStringConverter()
    );

    mashTable.setItems(FXCollections.observableArrayList(mashScheme.getMashSteps()));
    this.addPropertyColumn("Temperature", "temperature", mashTable);
    this.addPropertyColumn("Time", "time", mashTable);
    this.addPropertyColumn("Thickness", "thickness", mashTable);
  }

  private void fillYeastTable() {
    yeastTable.setItems(FXCollections.observableArrayList(recipe.getYeastAdditions()));
    this.<YeastAddition, BigDecimal>addPropertyColumn("Amount", "amount", yeastTable);
    final TableColumn<YeastAddition, String> yeastColumn = new TableColumn<>("Yeast");
    yeastColumn.setCellValueFactory(param -> {
      final String name = param.getValue().getYeast().getName();
      return Bindings.createObjectBinding(() -> name);
    });
    // yeastColumn.setCellFactory(col -> {
    //   final TableCell<YeastAddition, String> cell = new TableCell<>();
    //   cell.itemProperty().addListener(new ChangeListener<String>() {
    //     @Override
    //     public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    //       // https://stackoverflow.com/questions/35131428/combobox-in-a-tableview-cell-in-javafx
    //     }
    //   });
    //   return cell;
    // });
    yeastTable.getColumns().add(yeastColumn);
    this.<YeastAddition, String>addPropertyColumn("Addtion moment", "additionMoment", yeastTable);
  }

  private void fillHopsTable() {
    hopsTable.setItems(FXCollections.observableArrayList(this.recipe.getHopAdditions()));
    this.<HopAddition, BigDecimal>addPropertyColumn("Amount", "amount", hopsTable);
    this.<HopAddition, String>addPropertyColumn("Moment", "additionMoment", hopsTable);
    final TableColumn<HopAddition, String> hopColumn = new TableColumn<>("Hop");
    hopColumn.setCellValueFactory(param -> {
      final HopAddition value = param.getValue();
      return Bindings.createObjectBinding(() -> value.getHop().getName());
    });
    this.hopsTable.getColumns().add(hopColumn);

    this.<HopAddition, Integer>addPropertyColumn("Contact time", "contactTime", hopsTable);

  }

  private void fillFermentablesTable() {
    fermetablesTable.setItems(FXCollections.observableArrayList(this.recipe.getFermentableAdditions()));

    this.<FermentableAddition, BigDecimal>addPropertyColumn("Amount", "amount", this.fermetablesTable);

    final TableColumn<FermentableAddition, String> fermentableColumn = new TableColumn<>("Fermentable");
    fermentableColumn.setCellValueFactory(
        param -> {
          final FermentableAddition addition = param.getValue();
          return Bindings.createObjectBinding(() -> addition.getFermentable().getName());
        });
    fermetablesTable.getColumns().add(fermentableColumn);

    this.<FermentableAddition, String>addPropertyColumn("Moment", "additionMoment", this.fermetablesTable);
  }

  private <T, U> void addPropertyColumn(String label, String property, TableView<T> table) {
    final TableColumn<T, U> column = new TableColumn<>(label);
    column.setCellValueFactory(new PropertyValueFactory<>(property));
    table.getColumns().add(column);
  }

}
