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
import org.mybeer.hibernate.HopDao;
import org.mybeer.hibernate.SessionFactorySingleton;
import org.mybeer.model.ingredient.Hop;
import org.mybeer.model.recipe.AdditionMoment;
import org.mybeer.model.recipe.HopAddition;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.mybeer.view.AutoCompleteComboBoxListener.addAutoCompleteListener;

public class HopsTableController {

  private Set<HopAddition> hopAdditions;
  private Supplier<BigDecimal> calculateBitterness;
  private SimpleObjectProperty<BigDecimal> bitternessProp;
  @FXML
  private TableView<HopAddition> table;
  @FXML
  private TextField bitternessField;
  @FXML
  private TextField amountField;
  @FXML
  private ComboBox<Hop> hopsBox;
  @FXML
  private ComboBox<AdditionMoment> momentBox;
  @FXML
  private Button addButton;
  @FXML
  private Button removeButton;
  @FXML
  private TextField alphaAcidField;
  @FXML
  private TextField timeField;

  public void init(Set<HopAddition> hopAdditions, Supplier<BigDecimal> calculateBitterness,
                   SimpleObjectProperty<BigDecimal> bitternessProp) {

    this.hopAdditions = hopAdditions;
    this.calculateBitterness = calculateBitterness;
    this.bitternessProp = bitternessProp;
    bitternessField.textProperty().bindBidirectional(bitternessProp, new BigDecimalStringConverter());
    setupComboBox(hopsBox);
    hopsBox.valueProperty().addListener((observable, oldValue, newValue) -> {
      if(newValue != null) {
        alphaAcidField.textProperty().setValue("" + newValue.getAlphaAcidPercentageMax());
      }
    });
    momentBox.setItems(FXCollections.observableArrayList(AdditionMoment.values()));

    removeButton.setOnAction((actionEvent) -> {
      final HopAddition hopAddition = table.getSelectionModel().getSelectedItem();
      if (hopAddition != null) {
        hopAdditions.remove(hopAddition);
        table.getItems().remove(hopAddition);
      }
    });
    addButton.setOnAction((event -> {
      final BigDecimal amount = new BigDecimal(amountField.textProperty().getValue());
      amountField.textProperty().setValue("");
      final Hop hop = hopsBox.getValue();
      hopsBox.valueProperty().set(null);
      final AdditionMoment moment = momentBox.getValue();
      momentBox.setValue(null);
      final BigDecimal alphaAcid = new BigDecimal(this.alphaAcidField.textProperty().getValue());
      alphaAcidField.textProperty().set("");
      final int time = Integer.parseInt(this.timeField.textProperty().getValue());
      timeField.textProperty().set("");

      final HopAddition hopAddition = new HopAddition();
      hopAddition.setAmount(amount);
      hopAddition.setHop(hop);
      hopAddition.setAdditionMoment(moment);
      hopAddition.setHopsAlphaAcid(alphaAcid);
      hopAddition.setContactTime(time);
      hopAdditions.add(hopAddition);
      table.getItems().add(hopAddition);
    }));
    fillHopsTable();
  }

  private void fillHopsTable() {
    table.setItems(FXCollections.observableArrayList(hopAdditions));
    table.setEditable(true);
    final TableColumn<HopAddition, BigDecimal> amountColumn = new TableColumn<>("Amount");
    amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    final BigDecimalStringConverter bigDecimalConverter = new BigDecimalStringConverter();
    amountColumn.setCellFactory(TextFieldTableCell.forTableColumn(bigDecimalConverter));
    amountColumn.setOnEditCommit((event -> {
      final HopAddition hopAddition = event.getTableView().getItems().get(event.getTablePosition().getRow());
      event.getTableView().getItems().remove(hopAddition);
      hopAddition.setAmount(event.getNewValue());
      event.getTableView().getItems().add(hopAddition);
    }));
    amountColumn.setEditable(true);
    table.getColumns().add(amountColumn);

    final TableColumn<HopAddition, ComboBox<Hop>> hopColumn = new TableColumn<>("Hop");
    hopColumn.setCellValueFactory(param -> {
      final HopAddition addition = param.getValue();
      final ComboBox<Hop> comboBox = new ComboBox<>();
      setupComboBox(comboBox);
      comboBox.setValue(addition.getHop());
      comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
        table.getItems().remove(addition);
        addition.setHop(newValue);
        addition.setHopsAlphaAcid(newValue.getAlphaAcidPercentageMin());
        table.getItems().add(addition);
      });

      return Bindings.createObjectBinding(() -> comboBox);
    });
    this.table.getColumns().add(hopColumn);

    final TableColumn<HopAddition, BigDecimal> alphaAcidColumn = new TableColumn<>("Alpha Acid %");
    alphaAcidColumn.setCellValueFactory(new PropertyValueFactory<>("hopsAlphaAcid"));
    alphaAcidColumn.setCellFactory(TextFieldTableCell.forTableColumn(bigDecimalConverter));
    alphaAcidColumn.setEditable(true);
    alphaAcidColumn.setOnEditCommit((event -> {
      event.getTableView().getItems().get(event.getTablePosition().getRow()).setHopsAlphaAcid(event.getNewValue());
    }));
    table.getColumns().add(alphaAcidColumn);


    this.<HopAddition, String>addPropertyColumn("Moment", "additionMoment", table, true, false);
    this.<HopAddition, Integer>addPropertyColumn("Contact time", "contactTime", table, true, true);

    table.getItems().addListener(new ListChangeListener<>() {
      @Override
      public void onChanged(Change<? extends HopAddition> c) {
        bitternessProp.setValue(calculateBitterness.get());
      }
    });
  }

  private void setupComboBox(ComboBox<Hop> comboBox) {
    try (final Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
      final HopDao hopDao = new HopDao();
      final List<Hop> hops = hopDao.getAll(session).collect(Collectors.toList());
      comboBox.setItems(FXCollections.observableArrayList(hops));

      comboBox.setConverter(new StringConverter<>() {
        @Override
        public String toString(Hop hop) {
          return hop == null ? "" : hop.getName();
        }

        @Override
        public Hop fromString(String string) {
          return hops.stream().filter(hop -> hop.getName().equals(string)).findFirst().orElseThrow();
        }
      });
    }
    addAutoCompleteListener(comboBox, (text) -> hop -> hop.getName().toLowerCase().contains(text.toLowerCase()));
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
}
