package org.mybeer.view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.BigDecimalStringConverter;
import org.hibernate.Session;
import org.mybeer.hibernate.FermentableDao;
import org.mybeer.hibernate.SessionFactorySingleton;
import org.mybeer.model.ingredient.Fermentable;
import org.mybeer.model.recipe.AdditionMoment;
import org.mybeer.model.recipe.FermentableAddition;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FermentableTableController {
  @FXML
  private TextField colourField;
  @FXML
  private TableView<FermentableAddition> table;
  @FXML
  private TextField amountField;
  @FXML
  private ComboBox<Fermentable> fermentableBox;
  @FXML
  private ComboBox<AdditionMoment> momentBox;
  @FXML
  private Button addButton;
  @FXML
  private Button removeButton;
  private Set<FermentableAddition> fermentableAdditions;
  private Supplier<BigDecimal> calculateColour;
  private Updater gravityUpdater;
  private Updater waterUpdater;

  public void init(Set<FermentableAddition> fermentableAdditions, Supplier<BigDecimal> calculateColour,
                   Updater gravityUpdater, Updater waterUpdater) {
    this.fermentableAdditions = fermentableAdditions;
    this.calculateColour = calculateColour;
    this.gravityUpdater = gravityUpdater;
    this.waterUpdater = waterUpdater;
    fillColourField();
    fillFermentableComboBox(fermentableBox);
    momentBox.setItems(FXCollections.observableArrayList(AdditionMoment.values()));

    addButton.setOnAction(actionEvent -> {
      final BigDecimal amount = new BigDecimal(amountField.textProperty().getValue());
      final Fermentable fermentable = fermentableBox.getValue();
      final AdditionMoment additionMoment = momentBox.getValue();
      final FermentableAddition fermentableAddition = new FermentableAddition();
      fermentableAddition.setFermentable(fermentable);
      fermentableAddition.setAmount(amount);
      fermentableAddition.setAdditionMoment(additionMoment);
      fermentableAdditions.add(fermentableAddition);
      table.getItems().add(fermentableAddition);
    });

    removeButton.setOnAction((actionEvent) -> {
      final FermentableAddition fermentableAddition = table.getSelectionModel().getSelectedItem();
      if (fermentableAddition != null) {
        fermentableAdditions.remove(fermentableAddition);
        table.getItems().remove(fermentableAddition);
      }
    });


    fillFermentablesTable();
  }

  private void fillColourField() {
    colourField.textProperty()
               .bindBidirectional(new SimpleObjectProperty<>(calculateColour.get()), new BigDecimalStringConverter());
  }

  private void fillFermentablesTable() {
    table.setEditable(true);
    table.setItems(FXCollections.observableArrayList(fermentableAdditions));

    final TableColumn<FermentableAddition, BigDecimal> amountColumn = new TableColumn<>("Amount");
    amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    final BigDecimalStringConverter bigDecimalConverter = new BigDecimalStringConverter();
    amountColumn.setCellFactory(TextFieldTableCell.forTableColumn(bigDecimalConverter));
    amountColumn.setOnEditCommit((event -> {
      event.getTableView().getItems().get(event.getTablePosition().getRow()).setAmount(event.getNewValue());
    }));
    amountColumn.setEditable(true);
    table.getSortOrder().add(amountColumn);
    table.getColumns().add(amountColumn);

    final TableColumn<FermentableAddition, ComboBox<Fermentable>> fermentableColumn = new TableColumn<>("Fermentable");
    fermentableColumn.setCellValueFactory(param -> {
      final FermentableAddition fermentableAddition = param.getValue();
      final ComboBox<Fermentable> comboBox = new ComboBox<>();

      fillFermentableComboBox(comboBox);

      comboBox.setValue(fermentableAddition.getFermentable());
      comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
        param.getValue().setFermentable(newValue);
      });

      return Bindings.createObjectBinding(() -> comboBox);
    });
    fermentableColumn.setEditable(true);
    table.getColumns().add(fermentableColumn);

    final TableColumn<FermentableAddition, BigDecimal> fermentableColourColumn = new TableColumn<>("Colour (EBC)");
    fermentableColourColumn.setCellValueFactory(
        param -> {
          final FermentableAddition addition = param.getValue();
          return Bindings.createObjectBinding(() -> addition.getFermentable().getColour());
        });
    table.getColumns().add(fermentableColourColumn);

    this.<FermentableAddition, String>addPropertyColumn("Moment", "additionMoment", this.table, false, false);

    table.getItems().addListener((ListChangeListener<FermentableAddition>) c -> {
      fillColourField();
      gravityUpdater.update();
      waterUpdater.update();
    });
  }

  private void fillFermentableComboBox(ComboBox<Fermentable> comboBox) {
    try (final Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
      final FermentableDao fermentableDao = new FermentableDao();
      final List<Fermentable> fermentables = fermentableDao.getAll(session).collect(Collectors.toList());
      comboBox.setItems(FXCollections.observableArrayList(fermentables));
      comboBox.setConverter(new StringConverter<>() {
        @Override
        public String toString(Fermentable fermentable) {

          return fermentable == null ? "" : fermentable.getName();
        }

        @Override
        public Fermentable fromString(String name) {
          return fermentables.stream().filter(fermentable -> fermentable.getName().equals(name)).findFirst()
                             .orElseThrow();
        }
      });
    }
  }

  private <T, U> void addPropertyColumn(String label, String property, TableView<T> table, boolean orderBy,
                                        boolean reverseOrder) {
    final TableColumn<T, U> column = new TableColumn<>(label);
    column.setCellValueFactory(new PropertyValueFactory<>(property));
    table.getColumns().add(column);
    if (orderBy) {
      if (reverseOrder) {
        column.setComparator(column.getComparator().reversed());
      }
      table.getSortOrder().add(column);
    }
  }

  public interface Updater {
    void update();
  }
}
