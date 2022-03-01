package org.mybeer.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.NumberStringConverter;
import org.mybeer.model.ingredient.Hop;
import org.mybeer.hibernate.HopDao;
import java.math.BigDecimal;
import org.mybeer.model.ingredient.HopForm;
import java.io.IOException;


public class HopEditorController {
  private Hop hop;
  @FXML
  private Button backButton;
  @FXML
  private Button saveButton;
  @FXML
  private TextField idTextField;
  @FXML
  private TextField alphaAcidPercentageMinTextField;
  @FXML
  private TextField alphaAcidPercentageMaxTextField;
  @FXML
  private TextField betaAcidPercentageMinTextField;
  @FXML
  private TextField betaAcidPercentageMaxTextField;
  @FXML
  private TextField cohumuloneMinTextField;
  @FXML
  private TextField cohumuloneMaxTextField;
  @FXML
  private TextField oilMinTextField;
  @FXML
  private TextField oilMaxTextField;
  @FXML
  private TextField humuloneMinTextField;
  @FXML
  private TextField humuloneMaxTextField;
  @FXML
  private TextField caryophylleneMinTextField;
  @FXML
  private TextField caryophylleneMaxTextField;
  @FXML
  private TextField myrceneMinTextField;
  @FXML
  private TextField myrceneMaxTextField;
  @FXML
  private TextField farneseneMinTextField;
  @FXML
  private TextField farneseneMaxTextField;
  @FXML
  private ComboBox<HopForm> formComboBox;
  @FXML
  private TextField nameTextField;
  @FXML
  private TextField oldIdTextField;
  @FXML
  private TextField alphaBetaBalanceTextField;
  @FXML
  private TextField tasteTextField;
  @FXML
  private TextField substituteTextField;

  public HopEditorController() {
    this.hop = new Hop();
}

  public HopEditorController(Long id) {
    this.hop = new HopDao().getById(id).orElseThrow();
}

  @FXML
  public void initialize() {
    backButton.setOnAction(event -> {
      final Node source = (Node) event.getSource();
      final Stage stage = (Stage) source.getScene().getWindow();
      try {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/Screen.fxml"));
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    saveButton.setOnAction(event -> new HopDao().save(hop));
    final NumberStringConverter numberConverter = new NumberStringConverter();
    final BigDecimalStringConverter bigDecimalConverter = new BigDecimalStringConverter();

    idTextField.textProperty().setValue("" + hop.getId());
    idTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setId(numberConverter.fromString(newValue).longValue());
    });

    alphaAcidPercentageMinTextField.textProperty().setValue("" + hop.getAlphaAcidPercentageMin());
    alphaAcidPercentageMinTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setAlphaAcidPercentageMin(bigDecimalConverter.fromString(newValue));
    });

    alphaAcidPercentageMaxTextField.textProperty().setValue("" + hop.getAlphaAcidPercentageMax());
    alphaAcidPercentageMaxTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setAlphaAcidPercentageMax(bigDecimalConverter.fromString(newValue));
    });

    betaAcidPercentageMinTextField.textProperty().setValue("" + hop.getBetaAcidPercentageMin());
    betaAcidPercentageMinTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setBetaAcidPercentageMin(bigDecimalConverter.fromString(newValue));
    });

    betaAcidPercentageMaxTextField.textProperty().setValue("" + hop.getBetaAcidPercentageMax());
    betaAcidPercentageMaxTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setBetaAcidPercentageMax(bigDecimalConverter.fromString(newValue));
    });

    cohumuloneMinTextField.textProperty().setValue("" + hop.getCohumuloneMin());
    cohumuloneMinTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setCohumuloneMin(bigDecimalConverter.fromString(newValue));
    });

    cohumuloneMaxTextField.textProperty().setValue("" + hop.getCohumuloneMax());
    cohumuloneMaxTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setCohumuloneMax(bigDecimalConverter.fromString(newValue));
    });

    oilMinTextField.textProperty().setValue("" + hop.getOilMin());
    oilMinTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setOilMin(bigDecimalConverter.fromString(newValue));
    });

    oilMaxTextField.textProperty().setValue("" + hop.getOilMax());
    oilMaxTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setOilMax(bigDecimalConverter.fromString(newValue));
    });

    humuloneMinTextField.textProperty().setValue("" + hop.getHumuloneMin());
    humuloneMinTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setHumuloneMin(bigDecimalConverter.fromString(newValue));
    });

    humuloneMaxTextField.textProperty().setValue("" + hop.getHumuloneMax());
    humuloneMaxTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setHumuloneMax(bigDecimalConverter.fromString(newValue));
    });

    caryophylleneMinTextField.textProperty().setValue("" + hop.getCaryophylleneMin());
    caryophylleneMinTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setCaryophylleneMin(bigDecimalConverter.fromString(newValue));
    });

    caryophylleneMaxTextField.textProperty().setValue("" + hop.getCaryophylleneMax());
    caryophylleneMaxTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setCaryophylleneMax(bigDecimalConverter.fromString(newValue));
    });

    myrceneMinTextField.textProperty().setValue("" + hop.getMyrceneMin());
    myrceneMinTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setMyrceneMin(bigDecimalConverter.fromString(newValue));
    });

    myrceneMaxTextField.textProperty().setValue("" + hop.getMyrceneMax());
    myrceneMaxTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setMyrceneMax(bigDecimalConverter.fromString(newValue));
    });

    farneseneMinTextField.textProperty().setValue("" + hop.getFarneseneMin());
    farneseneMinTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setFarneseneMin(bigDecimalConverter.fromString(newValue));
    });

    farneseneMaxTextField.textProperty().setValue("" + hop.getFarneseneMax());
    farneseneMaxTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setFarneseneMax(bigDecimalConverter.fromString(newValue));
    });

    ComboBoxUtils.setUpComboBoxForEnum(HopForm.class, formComboBox, hopForm -> hopForm.name());

    formComboBox.setValue(hop.getForm());
    nameTextField.textProperty().setValue("" + hop.getName());
    nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setName(newValue);
    });

    oldIdTextField.textProperty().setValue("" + hop.getOldId());
    oldIdTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setOldId(numberConverter.fromString(newValue).intValue());
    });

    alphaBetaBalanceTextField.textProperty().setValue("" + hop.getAlphaBetaBalance());
    alphaBetaBalanceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setAlphaBetaBalance(bigDecimalConverter.fromString(newValue));
    });

    tasteTextField.textProperty().setValue("" + hop.getTaste());
    tasteTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setTaste(newValue);
    });

    substituteTextField.textProperty().setValue("" + hop.getSubstitute());
    substituteTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      hop.setSubstitute(newValue);
    });

  }
}