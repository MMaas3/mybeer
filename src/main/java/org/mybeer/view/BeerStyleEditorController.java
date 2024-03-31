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
import org.mybeer.model.BeerStyle;
import org.mybeer.hibernate.BeerStyleDao;
import java.math.BigDecimal;
import java.io.IOException;


public class BeerStyleEditorController {
  private BeerStyle beerStyle;
  @FXML
  private Button backButton;
  @FXML
  private Button saveButton;
  @FXML
  private TextField idTextField;
  @FXML
  private TextField beerTypeTextField;
  @FXML
  private TextField categoryTextField;
  @FXML
  private TextField categoryNumberTextField;
  @FXML
  private TextField nameTextField;
  @FXML
  private TextField guideNameTextField;
  @FXML
  private TextField styleLetterTextField;
  @FXML
  private TextField minimumOGTextField;
  @FXML
  private TextField maximumOGTextField;
  @FXML
  private TextField minimumFGTextField;
  @FXML
  private TextField maximumFGTextField;
  @FXML
  private TextField minimumAlcoholByVolumeTextField;
  @FXML
  private TextField maximumAlcoholByVolumeTextField;
  @FXML
  private TextField minimumBitternessTextField;
  @FXML
  private TextField maximumBitternessTextField;
  @FXML
  private TextField minimumCarbonationTextField;
  @FXML
  private TextField maximumCarbonationTextField;
  @FXML
  private TextField minimumColourTextField;
  @FXML
  private TextField maximumColourTextField;
  @FXML
  private TextField phMinTextField;
  @FXML
  private TextField phMaxTextField;

  public BeerStyleEditorController() {
    this.beerStyle = new BeerStyle();
}

  public BeerStyleEditorController(Long id) {
    this.beerStyle = new BeerStyleDao().getById(id).orElseThrow();
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
    saveButton.setOnAction(event -> new BeerStyleDao().save(beerStyle));
    final NumberStringConverter numberConverter = new NumberStringConverter();
    final BigDecimalStringConverter bigDecimalConverter = new BigDecimalStringConverter();

    idTextField.textProperty().setValue("" + beerStyle.getId());
    idTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setId(numberConverter.fromString(newValue).longValue());
    });

    beerTypeTextField.textProperty().setValue("" + beerStyle.getBeerType());
    beerTypeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setBeerType(newValue);
    });

    categoryTextField.textProperty().setValue("" + beerStyle.getCategory());
    categoryTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setCategory(newValue);
    });

    categoryNumberTextField.textProperty().setValue("" + beerStyle.getCategoryNumber());
    categoryNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setCategoryNumber(numberConverter.fromString(newValue).intValue());
    });

    nameTextField.textProperty().setValue("" + beerStyle.getName());
    nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setName(newValue);
    });

    guideNameTextField.textProperty().setValue("" + beerStyle.getGuideName());
    guideNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setGuideName(newValue);
    });

    styleLetterTextField.textProperty().setValue("" + beerStyle.getStyleLetter());
    styleLetterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setStyleLetter(newValue.charAt(0));
    });

    minimumOGTextField.textProperty().setValue("" + beerStyle.getMinimumOG());
    minimumOGTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setMinimumOG(bigDecimalConverter.fromString(newValue));
    });

    maximumOGTextField.textProperty().setValue("" + beerStyle.getMaximumOG());
    maximumOGTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setMaximumOG(bigDecimalConverter.fromString(newValue));
    });

    minimumFGTextField.textProperty().setValue("" + beerStyle.getMinimumFG());
    minimumFGTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setMinimumFG(bigDecimalConverter.fromString(newValue));
    });

    maximumFGTextField.textProperty().setValue("" + beerStyle.getMaximumFG());
    maximumFGTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setMaximumFG(bigDecimalConverter.fromString(newValue));
    });

    minimumAlcoholByVolumeTextField.textProperty().setValue("" + beerStyle.getMinimumAlcoholByVolume());
    minimumAlcoholByVolumeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setMinimumAlcoholByVolume(bigDecimalConverter.fromString(newValue));
    });

    maximumAlcoholByVolumeTextField.textProperty().setValue("" + beerStyle.getMaximumAlcoholByVolume());
    maximumAlcoholByVolumeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setMaximumAlcoholByVolume(bigDecimalConverter.fromString(newValue));
    });

    minimumBitternessTextField.textProperty().setValue("" + beerStyle.getMinimumBitterness());
    minimumBitternessTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setMinimumBitterness(numberConverter.fromString(newValue).intValue());
    });

    maximumBitternessTextField.textProperty().setValue("" + beerStyle.getMaximumBitterness());
    maximumBitternessTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setMaximumBitterness(numberConverter.fromString(newValue).intValue());
    });

    minimumCarbonationTextField.textProperty().setValue("" + beerStyle.getMinimumCarbonation());
    minimumCarbonationTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setMinimumCarbonation(bigDecimalConverter.fromString(newValue));
    });

    maximumCarbonationTextField.textProperty().setValue("" + beerStyle.getMaximumCarbonation());
    maximumCarbonationTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setMaximumCarbonation(bigDecimalConverter.fromString(newValue));
    });

    minimumColourTextField.textProperty().setValue("" + beerStyle.getMinimumColour());
    minimumColourTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setMinimumColour(bigDecimalConverter.fromString(newValue));
    });

    maximumColourTextField.textProperty().setValue("" + beerStyle.getMaximumColour());
    maximumColourTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setMaximumColour(bigDecimalConverter.fromString(newValue));
    });

    phMinTextField.textProperty().setValue("" + beerStyle.getPhMin());
    phMinTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setPhMin(bigDecimalConverter.fromString(newValue));
    });

    phMaxTextField.textProperty().setValue("" + beerStyle.getPhMax());
    phMaxTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      beerStyle.setPhMax(bigDecimalConverter.fromString(newValue));
    });

  }
}