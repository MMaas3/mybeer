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
import org.mybeer.model.ingredient.Yeast;
import org.mybeer.hibernate.YeastDao;
import org.mybeer.model.ingredient.YeastForm;
import org.mybeer.model.ingredient.YeastType;
import java.math.BigDecimal;
import org.mybeer.model.ingredient.Flocculation;
import java.io.IOException;


public class YeastEditorController {
  private Yeast yeast;
  @FXML
  private Button backButton;
  @FXML
  private Button saveButton;
  @FXML
  private TextField idTextField;
  @FXML
  private TextField nameTextField;
  @FXML
  private ComboBox<YeastForm> formComboBox;
  @FXML
  private ComboBox<YeastType> typeComboBox;
  @FXML
  private TextField oldIdTextField;
  @FXML
  private TextField attenuationMinTextField;
  @FXML
  private TextField attenuationMaxTextField;
  @FXML
  private TextField tempMinTextField;
  @FXML
  private TextField tempMaxTextField;
  @FXML
  private TextField alcoholToleranceMinTextField;
  @FXML
  private TextField alcoholToleranceMaxTextField;
  @FXML
  private TextField doseMinTextField;
  @FXML
  private TextField doseMaxTextField;
  @FXML
  private ComboBox<Flocculation> flocculationComboBox;
  @FXML
  private TextField stylesTextField;
  @FXML
  private TextField aromaTasteTextField;
  @FXML
  private TextField remarksTextField;

  public YeastEditorController() {
    this.yeast = new Yeast();
}

  public YeastEditorController(Long id) {
    this.yeast = new YeastDao().getById(id).orElseThrow();
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
    saveButton.setOnAction(event -> new YeastDao().save(yeast));
    final NumberStringConverter numberConverter = new NumberStringConverter();
    final BigDecimalStringConverter bigDecimalConverter = new BigDecimalStringConverter();

    idTextField.textProperty().setValue("" + yeast.getId());
    idTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      yeast.setId(numberConverter.fromString(newValue).longValue());
    });

    nameTextField.textProperty().setValue("" + yeast.getName());
    nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      yeast.setName(newValue);
    });

    ComboBoxUtils.setUpComboBoxForEnum(YeastForm.class, formComboBox, yeastForm -> yeastForm.name());

    formComboBox.setValue(yeast.getForm());
    ComboBoxUtils.setUpComboBoxForEnum(YeastType.class, typeComboBox, yeastType -> yeastType.name());

    typeComboBox.setValue(yeast.getType());
    oldIdTextField.textProperty().setValue("" + yeast.getOldId());
    oldIdTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      yeast.setOldId(numberConverter.fromString(newValue).intValue());
    });

    attenuationMinTextField.textProperty().setValue("" + yeast.getAttenuationMin());
    attenuationMinTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      yeast.setAttenuationMin(bigDecimalConverter.fromString(newValue));
    });

    attenuationMaxTextField.textProperty().setValue("" + yeast.getAttenuationMax());
    attenuationMaxTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      yeast.setAttenuationMax(bigDecimalConverter.fromString(newValue));
    });

    tempMinTextField.textProperty().setValue("" + yeast.getTempMin());
    tempMinTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      yeast.setTempMin(bigDecimalConverter.fromString(newValue));
    });

    tempMaxTextField.textProperty().setValue("" + yeast.getTempMax());
    tempMaxTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      yeast.setTempMax(bigDecimalConverter.fromString(newValue));
    });

    alcoholToleranceMinTextField.textProperty().setValue("" + yeast.getAlcoholToleranceMin());
    alcoholToleranceMinTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      yeast.setAlcoholToleranceMin(bigDecimalConverter.fromString(newValue));
    });

    alcoholToleranceMaxTextField.textProperty().setValue("" + yeast.getAlcoholToleranceMax());
    alcoholToleranceMaxTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      yeast.setAlcoholToleranceMax(bigDecimalConverter.fromString(newValue));
    });

    doseMinTextField.textProperty().setValue("" + yeast.getDoseMin());
    doseMinTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      yeast.setDoseMin(bigDecimalConverter.fromString(newValue));
    });

    doseMaxTextField.textProperty().setValue("" + yeast.getDoseMax());
    doseMaxTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      yeast.setDoseMax(bigDecimalConverter.fromString(newValue));
    });

    ComboBoxUtils.setUpComboBoxForEnum(Flocculation.class, flocculationComboBox, flocculation -> flocculation.name());

    flocculationComboBox.setValue(yeast.getFlocculation());
    stylesTextField.textProperty().setValue("" + yeast.getStyles());
    stylesTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      yeast.setStyles(newValue);
    });

    aromaTasteTextField.textProperty().setValue("" + yeast.getAromaTaste());
    aromaTasteTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      yeast.setAromaTaste(newValue);
    });

    remarksTextField.textProperty().setValue("" + yeast.getRemarks());
    remarksTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      yeast.setRemarks(newValue);
    });

  }
}