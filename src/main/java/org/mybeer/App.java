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
import org.mybeer.view.RecipeController;

import java.io.IOException;
import java.util.Comparator;

public class App extends Application {
  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage stage) throws IOException {
    // stage.setScene(createRecipeListScene(stage));
    stage.setScene(fermetableOveviewScene());
    // stage.setScene(createRecipeScene(15L, stage));
    stage.show();
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

  private Scene createRecipeListScene(Stage stage) {
    final TableView<Recipe> recipeTable = new TableView<>();
    final TableColumn<Recipe, String> nameColumn = new TableColumn<>();
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    recipeTable.getColumns().add(nameColumn);

    OverviewTableViewUtils.fillTableWithAllOfType(Recipe.class, recipeTable, Comparator.comparingLong(Recipe::getId).reversed());

    final Button openRecipeButton = new Button("Open recipe");
    openRecipeButton.setOnAction(event -> {
      final ObservableList<Recipe> selectedItems = recipeTable.getSelectionModel().getSelectedItems();
      if (selectedItems.isEmpty()) {
        return;
      }
      final Long id = selectedItems.get(0).getId();
      stage.setScene(createRecipeScene(id, stage));
    });
    final Button newRecipeButton = new Button("New recipe");
    newRecipeButton.setOnAction(event -> {
      stage.setScene(createRecipeScene(null, stage));
    });
    newRecipeButton.setLayoutX(100.0);

    final Pane pane = new Pane();
    pane.getChildren().add(recipeTable);
    pane.getChildren().add(openRecipeButton);
    pane.getChildren().add(newRecipeButton);

    return new Scene(pane);
  }

  private Scene createRecipeScene(Long id, Stage stage) {
    final FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(getClass().getResource("/view/recipe.fxml"));
    AnchorPane recipePane = null;
    try {
      recipePane = fxmlLoader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    RecipeController controller = fxmlLoader.getController();

    // TODO find a better way to pass data between scenes:
    // https://dev.to/devtony101/javafx-3-ways-of-passing-information-between-scenes-1bm8
    controller.init(id, event -> stage.setScene(this.createRecipeListScene(stage)));

    return new Scene(recipePane);
  }
}
