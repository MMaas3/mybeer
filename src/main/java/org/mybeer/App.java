package org.mybeer;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.mybeer.hibernate.RecipeDao;
import org.mybeer.hibernate.SessionFactorySingleton;
import org.mybeer.model.recipe.Recipe;
import org.mybeer.view.RecipeController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class App extends Application {
  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage stage) {

    final TableView<Recipe> recipeTable = new TableView<>();
    final TableColumn<Recipe, String> nameColumn = new TableColumn<>();
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    recipeTable.getColumns().add(nameColumn);
    final ObservableList<Recipe> recipes = FXCollections.observableArrayList(recipes());
    recipeTable.setItems(recipes);

    final Button openRecipe = new Button("Open recipe");
    openRecipe.setOnAction(event -> {
      final ObservableList<Recipe> selectedItems = recipeTable.getSelectionModel().getSelectedItems();
      if (selectedItems.isEmpty()) {
        return;
      }
      try {
        final FXMLLoader fxmlLoader = new FXMLLoader();
        AnchorPane recipe = fxmlLoader.load(new FileInputStream("src/main/resources/view/recipe.fxml"));
        RecipeController controller = fxmlLoader.getController();
        controller.setRecipe(selectedItems.get(0));
        stage.setScene(new Scene(recipe));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    final Pane pane = new Pane();
    pane.getChildren().add(recipeTable);
    pane.getChildren().add(openRecipe);

    Scene scene = new Scene(pane, 640, 480);

    stage.setScene(scene);
    stage.show();
  }

  private List<Recipe> recipes() {
    try (Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
      final RecipeDao recipeDao = new RecipeDao();
      return recipeDao.getAll(session).collect(Collectors.toList());
    }
  }
}
