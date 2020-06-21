package org.mybeer.view;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import javafx.util.converter.BigDecimalStringConverter;
import org.hibernate.Session;
import org.mybeer.hibernate.SessionFactorySingleton;
import org.mybeer.hibernate.YeastDao;
import org.mybeer.model.ingredient.Yeast;
import org.mybeer.model.ingredient.YeastForm;
import org.mybeer.model.recipe.AdditionMoment;
import org.mybeer.model.recipe.YeastAddition;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class YeastTableController extends AnchorPane {
  @FXML
  private TableView<YeastAddition> table;
  private Set<YeastAddition> yeastAdditions;
  @FXML
  private TextField amountField;
  @FXML
  private ComboBox<Yeast> yeastBox;
  @FXML
  private ComboBox<AdditionMoment> momentBox;
  @FXML
  private Button addButton;
  @FXML
  private Button removeButton;

  public void init(Set<YeastAddition> yeastAdditions) {

    this.yeastAdditions = yeastAdditions;
    fillYeastBox(yeastBox);
    momentBox.setItems(FXCollections.observableArrayList(AdditionMoment.values()));
    fillYeastTable();
    addButton.setOnAction((actionEvent) -> {
      final BigDecimal amount = new BigDecimal(amountField.textProperty().getValue());
      final Yeast yeast = yeastBox.getValue();
      final AdditionMoment moment = momentBox.getValue();
      final YeastAddition yeastAddition = new YeastAddition();
      yeastAddition.setYeast(yeast);
      yeastAddition.setAmount(amount);
      yeastAddition.setAdditionMoment(moment);
      if (yeast.getForm() != YeastForm.DRY) {
        yeastAddition.setAmountWeight(true);
      }
      yeastAdditions.add(yeastAddition);
      table.getItems().add(yeastAddition);
      System.out.println("test");
    });
    removeButton.setOnAction((actionEvent) -> {
      final YeastAddition yeastAddition = table.getSelectionModel().getSelectedItem();
      if (yeastAddition != null) {
        yeastAdditions.remove(yeastAddition);
        table.getItems().remove(yeastAddition);
      }
    });
  }

  private void fillYeastTable() {
    table.setItems(FXCollections.observableArrayList(yeastAdditions));
    table.setEditable(true);

    final TableColumn<YeastAddition, BigDecimal> amountColumn = new TableColumn<>("Amount");
    amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    amountColumn.setComparator(amountColumn.getComparator());
    amountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
    amountColumn.setOnEditCommit((event -> {
      event.getTableView().getItems().get(event.getTablePosition().getRow()).setAmount(event.getNewValue());
    }));
    amountColumn.setEditable(true);
    table.getColumns().add(amountColumn);
    table.getSortOrder().add(amountColumn);


    final TableColumn<YeastAddition, ComboBox<Yeast>> yeastColumn = new TableColumn<>("Yeast");
    yeastColumn.setCellValueFactory(param -> {
      final Yeast yeast = param.getValue().getYeast();
      final ComboBox<Yeast> comboBox = new ComboBox<>();
      fillYeastBox(comboBox);
      comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
        param.getValue().setYeast(newValue);
      });
      comboBox.setValue(yeast);

      return Bindings.createObjectBinding(() -> comboBox);
    });
    yeastColumn.setEditable(true);
    table.getColumns().add(yeastColumn);

    final TableColumn<YeastAddition, String> additionMoment = new TableColumn<>("Addition moment");
    additionMoment.setCellValueFactory(new PropertyValueFactory<>("additionMoment"));
    table.getColumns().add(additionMoment);

  }

  private void fillYeastBox(ComboBox<Yeast> comboBox) {
    try (Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
      final List<Yeast> yeasts = new YeastDao().getAll(session).collect(Collectors.toList());
      comboBox.setItems(FXCollections.observableArrayList(yeasts));
      comboBox.setConverter(new StringConverter<>() {
        @Override
        public String toString(Yeast object) {
          return object == null ? "" : object.getName();
        }

        @Override
        public Yeast fromString(String string) {
          return yeasts.stream().filter(yeast11 -> yeast11.getName().equals(string)).findAny().orElseThrow();
        }
      });
    }
  }

}
