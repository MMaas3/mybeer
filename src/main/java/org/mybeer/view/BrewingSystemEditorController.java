package org.mybeer.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.NumberStringConverter;
import org.mybeer.model.BrewingSystem;
import org.mybeer.hibernate.BrewingSystemDao;
import java.math.BigDecimal;
import java.io.IOException;


public class BrewingSystemEditorController {
  private BrewingSystem brewingSystem;
  @FXML
  private Button backButton;
  @FXML
  private Button saveButton;
  @FXML
  private TextField idTextField;
  @FXML
  private TextField nameTextField;
  @FXML
  private TextField mashTunVolumeTextField;
  @FXML
  private TextField mashTunHeightTextField;
  @FXML
  private TextField copperVolumeTextField;
  @FXML
  private TextField copperHeightTextField;
  @FXML
  private TextField hotLiquorTankVolumeTextField;
  @FXML
  private TextField hotLiquorTankHeightTextField;
  @FXML
  private TextField waterLossMaltTextField;
  @FXML
  private TextField evaporationRateTextField;
  @FXML
  private TextField kettleLossTextField;
  @FXML
  private TextField yeastLossTextField;
  @FXML
  private TextField restLossTextField;
  @FXML
  private TextField leafHopLossTextField;
  @FXML
  private TextField pelletHopLossTextField;

  public BrewingSystemEditorController() {
    this.brewingSystem = new BrewingSystem();
}

  public BrewingSystemEditorController(Long id) {
    this.brewingSystem = new BrewingSystemDao().getById(id).orElseThrow();
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
    saveButton.setOnAction(event -> new BrewingSystemDao().save(brewingSystem));
    final NumberStringConverter numberConverter = new NumberStringConverter();
    final BigDecimalStringConverter bigDecimalConverter = new BigDecimalStringConverter();

    idTextField.textProperty().setValue("" + brewingSystem.getId());
    idTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      brewingSystem.setId(numberConverter.fromString(newValue).longValue());
    });

    nameTextField.textProperty().setValue("" + brewingSystem.getName());
    nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      brewingSystem.setName(newValue);
    });

    mashTunVolumeTextField.textProperty().setValue("" + brewingSystem.getMashTunVolume());
    mashTunVolumeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      brewingSystem.setMashTunVolume(bigDecimalConverter.fromString(newValue));
    });

    mashTunHeightTextField.textProperty().setValue("" + brewingSystem.getMashTunHeight());
    mashTunHeightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      brewingSystem.setMashTunHeight(bigDecimalConverter.fromString(newValue));
    });

    copperVolumeTextField.textProperty().setValue("" + brewingSystem.getCopperVolume());
    copperVolumeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      brewingSystem.setCopperVolume(bigDecimalConverter.fromString(newValue));
    });

    copperHeightTextField.textProperty().setValue("" + brewingSystem.getCopperHeight());
    copperHeightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      brewingSystem.setCopperHeight(bigDecimalConverter.fromString(newValue));
    });

    hotLiquorTankVolumeTextField.textProperty().setValue("" + brewingSystem.getHotLiquorTankVolume());
    hotLiquorTankVolumeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      brewingSystem.setHotLiquorTankVolume(bigDecimalConverter.fromString(newValue));
    });

    hotLiquorTankHeightTextField.textProperty().setValue("" + brewingSystem.getHotLiquorTankHeight());
    hotLiquorTankHeightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      brewingSystem.setHotLiquorTankHeight(bigDecimalConverter.fromString(newValue));
    });

    waterLossMaltTextField.textProperty().setValue("" + brewingSystem.getWaterLossMalt());
    waterLossMaltTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      brewingSystem.setWaterLossMalt(bigDecimalConverter.fromString(newValue));
    });

    evaporationRateTextField.textProperty().setValue("" + brewingSystem.getEvaporationRate());
    evaporationRateTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      brewingSystem.setEvaporationRate(bigDecimalConverter.fromString(newValue));
    });

    kettleLossTextField.textProperty().setValue("" + brewingSystem.getKettleLoss());
    kettleLossTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      brewingSystem.setKettleLoss(bigDecimalConverter.fromString(newValue));
    });

    yeastLossTextField.textProperty().setValue("" + brewingSystem.getYeastLoss());
    yeastLossTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      brewingSystem.setYeastLoss(bigDecimalConverter.fromString(newValue));
    });

    restLossTextField.textProperty().setValue("" + brewingSystem.getRestLoss());
    restLossTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      brewingSystem.setRestLoss(bigDecimalConverter.fromString(newValue));
    });

    leafHopLossTextField.textProperty().setValue("" + brewingSystem.getLeafHopLoss());
    leafHopLossTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      brewingSystem.setLeafHopLoss(bigDecimalConverter.fromString(newValue));
    });

    pelletHopLossTextField.textProperty().setValue("" + brewingSystem.getPelletHopLoss());
    pelletHopLossTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      brewingSystem.setPelletHopLoss(bigDecimalConverter.fromString(newValue));
    });

  }
}