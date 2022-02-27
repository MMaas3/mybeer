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
import org.mybeer.model.ingredient.Fermentable;
import org.mybeer.hibernate.FermentableDao;
import java.math.BigDecimal;
import org.mybeer.model.ingredient.FermentableType;
import java.io.IOException;


public class FermentableEditorController {
  private Fermentable fermentable;
  @FXML
  private Button backButton;
  @FXML
  private Button saveButton;
  @FXML
  private TextField idTextField;
  @FXML
  private TextField colourTextField;
  @FXML
  private ComboBox<FermentableType> fermentableTypeComboBox;
  @FXML
  private TextField moistureTextField;
  @FXML
  private TextField nameTextField;
  @FXML
  private TextField potentialExtractTextField;
  @FXML
  private TextField yieldTextField;
  @FXML
  private TextField oldIdTextField;
  @FXML
  private TextField maximumPercentageTextField;
  @FXML
  private TextField proteinTextField;
  @FXML
  private TextField usageTextField;
  @FXML
  private TextField remarksTextField;

  public FermentableEditorController() {
    this.fermentable = new Fermentable();
}

  public FermentableEditorController(Long id) {
    this.fermentable = new FermentableDao().getById(id).orElseThrow();
}

  @FXML
  public void initialize() {
    backButton.setOnAction(event -> {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        try {
          final FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getClassLoader().getResource("view/FermentableOverview.fxml"));
          final Parent root = loader.load();
          stage.setScene(new Scene(root));
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    saveButton.setOnAction(event -> new FermentableDao().save(fermentable));
    final NumberStringConverter numberConverter = new NumberStringConverter();
    final BigDecimalStringConverter bigDecimalConverter = new BigDecimalStringConverter();

    idTextField.textProperty().setValue("" + fermentable.getId());
    idTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      fermentable.setId(numberConverter.fromString(newValue).longValue());
    });

    colourTextField.textProperty().setValue("" + fermentable.getColour());
    colourTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      fermentable.setColour(bigDecimalConverter.fromString(newValue));
    });

    ComboBoxUtils.setUpComboBoxForEnum(FermentableType.class, fermentableTypeComboBox, fermentableType -> fermentableType.name());

    fermentableTypeComboBox.setValue(fermentable.getFermentableType());
    moistureTextField.textProperty().setValue("" + fermentable.getMoisture());
    moistureTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      fermentable.setMoisture(bigDecimalConverter.fromString(newValue));
    });

    nameTextField.textProperty().setValue("" + fermentable.getName());
    nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      fermentable.setName(newValue);
    });

    potentialExtractTextField.textProperty().setValue("" + fermentable.getPotentialExtract());
    potentialExtractTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      fermentable.setPotentialExtract(bigDecimalConverter.fromString(newValue));
    });

    yieldTextField.textProperty().setValue("" + fermentable.getYield());
    yieldTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      fermentable.setYield(bigDecimalConverter.fromString(newValue));
    });

    oldIdTextField.textProperty().setValue("" + fermentable.getOldId());
    oldIdTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      fermentable.setOldId(numberConverter.fromString(newValue).intValue());
    });

    maximumPercentageTextField.textProperty().setValue("" + fermentable.getMaximumPercentage());
    maximumPercentageTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      fermentable.setMaximumPercentage(bigDecimalConverter.fromString(newValue));
    });

    proteinTextField.textProperty().setValue("" + fermentable.getProtein());
    proteinTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      fermentable.setProtein(bigDecimalConverter.fromString(newValue));
    });

    usageTextField.textProperty().setValue("" + fermentable.getUsage());
    usageTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      fermentable.setUsage(newValue);
    });

    remarksTextField.textProperty().setValue("" + fermentable.getRemarks());
    remarksTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      fermentable.setRemarks(newValue);
    });

  }
}