package org.mybeer.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.mybeer.model.recipe.Recipe;

public class RecipeController {
  @FXML
  private TextField nameField;

  public void setRecipe(Recipe recipe) {
    nameField.setText(recipe.getName());
  }
}
