package org.mybeer;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.mybeer.model.recipe.Recipe;
import org.mybeer.view.OverviewTableViewUtils;
import org.mybeer.view.RecipeEditorController;

import java.io.IOException;
import java.util.Comparator;

public class App extends Application {
  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage stage) throws IOException {
    stage.setScene(recipeOverviewScene());
    stage.show();
  }

  private Scene recipeOverviewScene() {
    final FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(getClass().getResource("/view/RecipeOverview.fxml"));
    Parent recipeOverview = null;
    try {
      recipeOverview = fxmlLoader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return new Scene(recipeOverview);
  }

  private Scene fermetableOveviewScene() {
    final FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(getClass().getResource("/view/FermentableOverview.fxml"));
    Parent fermetableOverview = null;
    try {
      fermetableOverview = fxmlLoader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return new Scene(fermetableOverview);
  }
}
