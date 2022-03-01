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
import org.mybeer.model.ingredient.Spice;
import org.mybeer.hibernate.SpiceDao;
import java.math.BigDecimal;
import java.io.IOException;


public class SpiceEditorController {
  private Spice spice;
  @FXML
  private Button backButton;
  @FXML
  private Button saveButton;
  @FXML
  private TextField idTextField;
  @FXML
  private TextField oldIdTextField;
  @FXML
  private TextField nameTextField;
  @FXML
  private TextField typeTextField;
  @FXML
  private TextField gravityAdditionTextField;
  @FXML
  private TextField remarkTextField;

  public SpiceEditorController() {
    this.spice = new Spice();
}

  public SpiceEditorController(Long id) {
    this.spice = new SpiceDao().getById(id).orElseThrow();
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
    saveButton.setOnAction(event -> new SpiceDao().save(spice));
    final NumberStringConverter numberConverter = new NumberStringConverter();
    final BigDecimalStringConverter bigDecimalConverter = new BigDecimalStringConverter();

    idTextField.textProperty().setValue("" + spice.getId());
    idTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      spice.setId(numberConverter.fromString(newValue).longValue());
    });

    oldIdTextField.textProperty().setValue("" + spice.getOldId());
    oldIdTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      spice.setOldId(numberConverter.fromString(newValue).intValue());
    });

    nameTextField.textProperty().setValue("" + spice.getName());
    nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      spice.setName(newValue);
    });

    typeTextField.textProperty().setValue("" + spice.getType());
    typeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      spice.setType(newValue);
    });

    gravityAdditionTextField.textProperty().setValue("" + spice.getGravityAddition());
    gravityAdditionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      spice.setGravityAddition(bigDecimalConverter.fromString(newValue));
    });

    remarkTextField.textProperty().setValue("" + spice.getRemark());
    remarkTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      spice.setRemark(newValue);
    });

  }
}