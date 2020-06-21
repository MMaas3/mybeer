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

public class HopsTableController {

  private Set<HopAddition> hopAdditions;
  private Supplier<BigDecimal> calculateBitterness;
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
  private TextField alphaAcid;
  @FXML
  private TextField timeField;

  public void init(Set<HopAddition> hopAdditions, Supplier<BigDecimal> calculateBitterness) {

    this.hopAdditions = hopAdditions;
    this.calculateBitterness = calculateBitterness;
    fillBitternessField();
    fillComboBox(hopsBox);
    hopsBox.valueProperty().addListener((observable, oldValue, newValue) -> {
      alphaAcid.textProperty().setValue("" + newValue.getAlphaAcidPercentageMax());
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
      final Hop hop = hopsBox.getValue();
      final AdditionMoment moment = momentBox.getValue();
      final BigDecimal alphaAcid = new BigDecimal(this.alphaAcid.textProperty().getValue());
      final int time = Integer.parseInt(this.timeField.textProperty().getValue());

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

  private void fillBitternessField() {
    bitternessField.textProperty()
                   .bindBidirectional(new SimpleObjectProperty<>(calculateBitterness.get()),
                       new BigDecimalStringConverter());
  }

  private void fillHopsTable() {
    table.setItems(FXCollections.observableArrayList(hopAdditions));
    table.setEditable(true);
    final TableColumn<HopAddition, BigDecimal> amountColumn = new TableColumn<>("Amount");
    amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    final BigDecimalStringConverter bigDecimalConverter = new BigDecimalStringConverter();
    amountColumn.setCellFactory(TextFieldTableCell.forTableColumn(bigDecimalConverter));
    amountColumn.setOnEditCommit((event -> {
      event.getTableView().getItems().get(event.getTablePosition().getRow()).setAmount(event.getNewValue());
    }));
    amountColumn.setEditable(true);
    table.getColumns().add(amountColumn);

    final TableColumn<HopAddition, ComboBox<Hop>> hopColumn = new TableColumn<>("Hop");
    hopColumn.setCellValueFactory(param -> {
      final HopAddition value = param.getValue();
      final ComboBox<Hop> comboBox = new ComboBox<>();
      fillComboBox(comboBox);
      comboBox.setValue(value.getHop());
      comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
        param.getValue().setHop(newValue);
        param.getValue().setHopsAlphaAcid(newValue.getAlphaAcidPercentageMin());
        table.refresh();
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

    table.getItems().addListener(new ListChangeListener<HopAddition>() {
      @Override
      public void onChanged(Change<? extends HopAddition> c) {
        fillBitternessField();
      }
    });
  }

  private void fillComboBox(ComboBox<Hop> comboBox) {
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
