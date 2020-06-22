package org.mybeer.view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.NumberStringConverter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mybeer.calculator.GravityCalculator;
import org.mybeer.calculator.WaterCalculator;
import org.mybeer.calculator.bitterness.BitternessCalculator;
import org.mybeer.calculator.bitterness.RayDanielsBitternessMethod;
import org.mybeer.calculator.colour.ColourCalculator;
import org.mybeer.calculator.colour.DanielMoreyMethod;
import org.mybeer.hibernate.RecipeDao;
import org.mybeer.hibernate.SessionFactorySingleton;
import org.mybeer.model.mash.MashScheme;
import org.mybeer.model.mash.MashStep;
import org.mybeer.model.recipe.FermentableAddition;
import org.mybeer.model.recipe.Recipe;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class RecipeController {
  @FXML
  private TextField nameField;
  @FXML
  private TextField boilTimeField;
  @FXML
  private TextField volumeField;
  private Recipe recipe;
  @FXML
  private TextField mashWaterField;
  @FXML
  private TextField spargeWaterField;
  @FXML
  private TextField thicknessField;
  @FXML
  private TableView<MashStep> mashTable;
  @FXML
  private Button backButton;
  @FXML
  private TextField descriptionField;
  @FXML
  private TextField gravityField;
  @FXML
  private TextField efficiencyField;
  @FXML
  private TextField totalWaterField;
  @FXML
  private Button refreshButton;
  @FXML
  private Button saveButton;
  @FXML
  private YeastTableController yeastTableController;
  @FXML
  private HopsTableController hopsTableController;
  @FXML
  private FermentableTableController fermentableTableController;
  private SimpleObjectProperty<BigDecimal> gravityProp;
  private SimpleObjectProperty<BigDecimal> bitternessProp;
  private SimpleObjectProperty<BigDecimal> totalWaterProp;


  public void init(Long recipeId, EventHandler<ActionEvent> backAction) {
    this.backButton.setOnAction(backAction);
    this.refreshButton.setOnAction((actionEvent) -> populateForm());
    this.saveButton.setOnAction((actionEvent) -> {
      try (final Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
        final RecipeDao recipeDao = new RecipeDao();
        final Transaction transaction = session.beginTransaction();
        recipeDao.save(recipe);
        transaction.commit();
      }
    });

    try (final Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
      final Optional<Recipe> recipeOptional = new RecipeDao().getById(recipeId, session);
      if (recipeOptional.isEmpty()) {
        throw new RuntimeException("Recipe does not exist");
      }

      this.recipe = recipeOptional.orElse(new Recipe());

      populateForm();
    }
  }

  private void populateForm() {
    nameField.textProperty().bindBidirectional(new SimpleStringProperty(this.recipe.getName()));
    descriptionField.textProperty().bindBidirectional(new SimpleObjectProperty<>(this.recipe.getDescription()));
    boilTimeField.textProperty()
                 .bindBidirectional(new SimpleIntegerProperty(this.recipe.getBoilTime()), new NumberStringConverter());
    final BigDecimalStringConverter bigDecimalConverter = new BigDecimalStringConverter();
    bindField(bigDecimalConverter, efficiencyField, () -> recipe.getEfficiency(), (val) -> recipe.setEfficiency(val));
    bindField(bigDecimalConverter, volumeField, () -> recipe.getVolume(), (val) -> recipe.setVolume(val));
    final BigDecimal gravity = calculateGravity(recipe);
    gravityProp = new SimpleObjectProperty<>(gravity);
    gravityProp.addListener((observable, oldValue, newValue) -> {
      bitternessProp.setValue(calculateBitterness(recipe, newValue));
    });
    bitternessProp = new SimpleObjectProperty<>(calculateBitterness(recipe, gravity));
    gravityField.textProperty().bindBidirectional(gravityProp, bigDecimalConverter);
    totalWaterProp = new SimpleObjectProperty<>(calculateTotalWater(recipe));
    fermentableTableController.init(recipe.getFermentableAdditions(), () -> calculateColour(recipe), () -> {
      totalWaterProp.set(calculateTotalWater(recipe));
      gravityProp.setValue(calculateGravity(recipe));
    });
    hopsTableController.init(recipe.getHopAdditions(), () -> calculateBitterness(recipe, gravity), bitternessProp);
    yeastTableController.init(recipe.getYeastAdditions());
    fillMashTab();
  }

  private <T> void bindField(StringConverter<T> converter, TextField field, Supplier<T> value, Consumer<T> consumer) {
    field.textProperty().bindBidirectional(new SimpleObjectProperty<T>(value.get()), converter);
    field.focusedProperty().addListener(((observable, oldValue, newValue) -> {
      System.out.println("out change focus");
      if (!newValue) {
        final String currentValue = field.textProperty().getValue();
        if (currentValue != null && !currentValue.isBlank()) {
          consumer.accept(converter.fromString(currentValue));
        }
      }
    }));
  }

  private BigDecimal calculateBitterness(Recipe recipe, BigDecimal gravity) {
    final BitternessCalculator bitternessCalculator = new BitternessCalculator(new RayDanielsBitternessMethod());
    return bitternessCalculator.calculate(recipe.getHopAdditions(), gravity, recipe.getVolume())
                               .setScale(0, RoundingMode.HALF_UP);
  }

  private BigDecimal calculateColour(Recipe recipe) {
    final ColourCalculator colourCalculator = new ColourCalculator(recipe.getVolume());
    return colourCalculator.calculateEBC(new DanielMoreyMethod(), recipe.getFermentableAdditions())
                           .setScale(0, RoundingMode.HALF_UP);
  }

  private BigDecimal calculateGravity(Recipe recipe) {
    final GravityCalculator gravityCalculator = new GravityCalculator();
    return gravityCalculator.calculate(recipe.getFermentableAdditions(), recipe.getVolume(), recipe.getEfficiency())
                            .setScale(3, RoundingMode.HALF_UP);
  }

  private void fillMashTab() {
    final MashScheme mashScheme = recipe.getMashScheme();
    final BigDecimalStringConverter bigDecimalConverter = new BigDecimalStringConverter();
    mashWaterField.textProperty()
                  .bindBidirectional(new SimpleObjectProperty<>(mashScheme.getMashWater()), bigDecimalConverter);
    spargeWaterField.textProperty()
                    .bindBidirectional(new SimpleObjectProperty<>(mashScheme.getSpargeWater()), bigDecimalConverter
                    );
    thicknessField.textProperty()
                  .bindBidirectional(new SimpleObjectProperty<>(mashScheme.getThickness()), bigDecimalConverter);
    totalWaterField.textProperty().bindBidirectional(totalWaterProp, bigDecimalConverter);

    mashTable.setItems(FXCollections.observableArrayList(mashScheme.getMashSteps()));
    this.addPropertyColumn("Temperature", "temperature", mashTable, false, false);
    this.addPropertyColumn("Time", "time", mashTable, false, false);
    this.addPropertyColumn("Thickness", "thickness", mashTable, false, false);
  }

  private BigDecimal calculateTotalWater(Recipe recipe) {
    final WaterCalculator waterCalculator = new WaterCalculator();
    return waterCalculator.calculate(recipe);
  }


  private <T, U> void addPropertyColumn(String label, String property, TableView<T> table, boolean orderBy,
                                        boolean reverseOrder) {
    final TableColumn<T, U> column = new TableColumn<>(label);
    column.setCellValueFactory(new PropertyValueFactory<>(property));
    table.getColumns().add(column);
    if (orderBy) {
      if (reverseOrder) {
        column.setComparator(column.getComparator().reversed());
      }
      table.getSortOrder().add(column);
    }
  }

}
