package org.mybeer.view;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.NumberStringConverter;
import org.hibernate.Session;
import org.mybeer.hibernate.RecipeDao;
import org.mybeer.hibernate.SessionFactorySingleton;
import org.mybeer.model.recipe.FermentableAddition;
import org.mybeer.model.recipe.Recipe;

import java.math.BigDecimal;
import java.util.Optional;

public class RecipeController {
  @FXML
  private TextField nameField;
  @FXML
  private TextField boilTimeField;
  @FXML
  private TextField volumeField;
  private Recipe recipe;
  @FXML
  private TableView<FermentableAddition> fermetablesTable;

  public void setRecipe(Long recipeId) {
    try (final Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
      final Optional<Recipe> recipeOptional = new RecipeDao().getById(recipeId, session);
      if(recipeOptional.isEmpty()) {
        throw new RuntimeException("Recipe does not exist");
      }

      this.recipe = recipeOptional.get();
      nameField.textProperty().bindBidirectional(new SimpleStringProperty(this.recipe.getName()));
      boilTimeField.textProperty()
                   .bindBidirectional(new SimpleIntegerProperty(this.recipe.getBoilTime()), new NumberStringConverter());
      volumeField.textProperty()
                 .bindBidirectional(new SimpleObjectProperty<>(this.recipe.getVolume()), new BigDecimalStringConverter());

      fermetablesTable.setItems(FXCollections.observableArrayList(this.recipe.getFermentableAdditions()));

      final TableColumn<FermentableAddition, BigDecimal> amountColumn = new TableColumn<>("Amount");
      amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
      fermetablesTable.getColumns().add(amountColumn);


      final TableColumn<FermentableAddition, String> fermentableColumn = new TableColumn<>("Fermentable");
      fermentableColumn.setCellValueFactory(
          param -> {
            final FermentableAddition addition = param.getValue();
            return new ObservableValue<>() {
              @Override
              public void addListener(ChangeListener<? super String> listener) {

              }

              @Override
              public void removeListener(ChangeListener<? super String> listener) {

              }

              @Override
              public String getValue() {
                return addition.getFermentable().getName();
              }

              @Override
              public void addListener(InvalidationListener listener) {

              }

              @Override
              public void removeListener(InvalidationListener listener) {

              }
            };
          });
      fermetablesTable.getColumns().add(fermentableColumn);

      final TableColumn<FermentableAddition, String> momentColumn = new TableColumn<>("Moment");
      momentColumn.setCellValueFactory(new PropertyValueFactory<>("additionMoment"));
      fermetablesTable.getColumns().add(momentColumn);
    }
  }
}
