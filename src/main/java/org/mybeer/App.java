package org.mybeer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage stage) {
    stage.setScene(createScene("/view/Screen.fxml"));
    stage.show();
  }

  private Scene createScene(String fxmlFile) {
    final FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(getClass().getResource(fxmlFile));
    Parent fxml = null;
    try {
      fxml = fxmlLoader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return new Scene(fxml);
  }
}
