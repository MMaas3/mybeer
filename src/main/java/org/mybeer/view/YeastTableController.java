package org.mybeer.view;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.BigDecimalStringConverter;
import org.mybeer.model.ingredient.Yeast;
import org.mybeer.model.ingredient.YeastForm;
import org.mybeer.model.recipe.AdditionMoment;
import org.mybeer.model.recipe.YeastAddition;

import java.math.BigDecimal;
import java.util.Set;

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
    setUpYeastBox(yeastBox);
    momentBox.setItems(FXCollections.observableArrayList(AdditionMoment.values()));
    fillYeastTable();
    addButton.setOnAction((actionEvent) -> {
      final BigDecimal amount = new BigDecimal(amountField.textProperty().getValue());
      amountField.textProperty().set("");
      final Yeast yeast = yeastBox.getValue();
      yeastBox.setValue(null);
      final AdditionMoment moment = momentBox.getValue();
      momentBox.setValue(null);
      final YeastAddition yeastAddition = new YeastAddition();
      yeastAddition.setYeast(yeast);
      yeastAddition.setAmount(amount);
      yeastAddition.setAdditionMoment(moment);
      if (yeast.getForm() != YeastForm.DRY) {
        yeastAddition.setAmountWeight(true);
      }
      yeastAdditions.add(yeastAddition);
      table.getItems().add(yeastAddition);
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
      setUpYeastBox(comboBox);
      comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
        param.getValue().setYeast(newValue);
      });
      comboBox.setValue(yeast);

      return Bindings.createObjectBinding(() -> comboBox);
    });
    yeastColumn.setEditable(true);
    table.getColumns().add(yeastColumn);

    final TableColumn<YeastAddition, ComboBox<AdditionMoment>> momentColumn = new TableColumn<>("Moment");
    momentColumn.setCellValueFactory(param -> {
      final YeastAddition yeastAddition = param.getValue();
      final AdditionMoment additionMoment = yeastAddition.getAdditionMoment();
      final ComboBox<AdditionMoment> additionMomentComboBox = new ComboBox<>();

      additionMomentComboBox.setItems(FXCollections.observableArrayList(AdditionMoment.values()));
      additionMomentComboBox.setValue(additionMoment);
      additionMomentComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
        yeastAddition.setAdditionMoment(newValue);
      });

      return Bindings.createObjectBinding(() -> additionMomentComboBox);
    });
    table.getColumns().add(momentColumn);

  }

  private void setUpYeastBox(ComboBox<Yeast> comboBox) {
    ComboBoxUtils.setUpComboBoxForEntity(Yeast.class, comboBox, Yeast::getName);
  }

}
