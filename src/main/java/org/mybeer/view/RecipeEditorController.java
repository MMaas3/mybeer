package org.mybeer.view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.NumberStringConverter;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mybeer.calculator.GravityCalculator;
import org.mybeer.calculator.WaterCalculator;
import org.mybeer.calculator.bitterness.BitternessCalculator;
import org.mybeer.calculator.bitterness.RayDanielsBitternessMethod;
import org.mybeer.calculator.colour.ColourCalculator;
import org.mybeer.calculator.colour.DanielMoreyMethod;
import org.mybeer.hibernate.BrewingSystemDao;
import org.mybeer.hibernate.RecipeDao;
import org.mybeer.hibernate.SessionFactorySingleton;
import org.mybeer.model.BrewingSystem;
import org.mybeer.model.mash.MashScheme;
import org.mybeer.model.mash.MashStep;
import org.mybeer.model.recipe.Recipe;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class RecipeEditorController {
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
  private SimpleObjectProperty<BigDecimal> colourProp;

  public RecipeEditorController(Long id) {
    try (final Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
      final Transaction transaction = session.beginTransaction();
      this.recipe = new RecipeDao().getById(session, id).orElseThrow();
      Hibernate.initialize(recipe.getFermentableAdditions());
      Hibernate.initialize(recipe.getHopAdditions());
      Hibernate.initialize(recipe.getSpiceAdditions());
      Hibernate.initialize(recipe.getYeastAdditions());
      transaction.commit();
    }
  }

  public RecipeEditorController() {
    this.recipe = newRecipe();
  }

  @FXML
  public void initialize() {
    this.backButton.setOnAction(event -> {
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
    this.saveButton.setOnAction((actionEvent) -> {
      try (final Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
        final RecipeDao recipeDao = new RecipeDao();
        final Transaction transaction = session.beginTransaction();
        recipeDao.save(recipe);
        transaction.commit();
      }
    });

    populateForm();
  }

  private Recipe newRecipe() {
    final Recipe recipe = new Recipe();
    final BrewingSystem brewingSystem = new BrewingSystemDao().findByName("Maas Pico Brouwerij").get();
    recipe.setSystem(brewingSystem);
    recipe.setVolume(BigDecimal.valueOf(20));
    recipe.setEfficiency(BigDecimal.valueOf(0.75));

    return recipe;
  }

  private void populateForm() {
    final SimpleStringProperty nameProp = new SimpleStringProperty(this.recipe.getName());
    nameField.textProperty().addListener((observable, oldValue, newValue) -> {
      recipe.setName(newValue);
    });
    nameField.textProperty().bindBidirectional(nameProp);
    final SimpleObjectProperty<String> descriptionProp = new SimpleObjectProperty<>(this.recipe.getDescription());
    descriptionProp.addListener((observable, oldValue, newValue) -> {
      recipe.setDescription(newValue);
    });
    descriptionField.textProperty().bindBidirectional(descriptionProp);
    final SimpleIntegerProperty boilTimeProp = new SimpleIntegerProperty(this.recipe.getBoilTime());
    final NumberStringConverter numberConverter = new NumberStringConverter();
    boilTimeField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == null) {
        newValue = "0";
      }
      recipe.setBoilTime(numberConverter.fromString(newValue).intValue());
      if (totalWaterProp != null) {
        totalWaterProp.setValue(calculateTotalWater(recipe));
      }
    });
    boilTimeField.textProperty().bindBidirectional(boilTimeProp, numberConverter);
    final BigDecimalStringConverter bigDecimalConverter = new BigDecimalStringConverter();
    final SimpleObjectProperty<BigDecimal> efficiencyProp = new SimpleObjectProperty<>(recipe.getEfficiency());

    efficiencyField.textProperty().bindBidirectional(efficiencyProp, bigDecimalConverter);
    efficiencyField.textProperty().addListener(((observable, oldValue, newValue) -> {
      recipe.setEfficiency(bigDecimalConverter.fromString(newValue));
      colourProp.setValue(calculateColour(recipe));
      gravityProp.setValue(calculateGravity(recipe));
      totalWaterProp.setValue(calculateTotalWater(recipe));
    }));

    final SimpleObjectProperty<BigDecimal> volumeProp = new SimpleObjectProperty<>(this.recipe.getVolume());
    volumeField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue == null) {
        newValue = "0";
      }
      recipe.setVolume(bigDecimalConverter.fromString(newValue));
      if (colourProp != null && gravityProp != null && totalWaterProp != null) {
        colourProp.setValue(calculateColour(recipe));
        gravityProp.setValue(calculateGravity(recipe));
        totalWaterProp.setValue(calculateTotalWater(recipe));
      }
    });
    volumeField.textProperty().bindBidirectional(volumeProp, bigDecimalConverter);

    final BigDecimal gravity = calculateGravity(recipe);
    gravityProp = new SimpleObjectProperty<>(gravity);
    gravityProp.addListener((observable, oldValue, newValue) -> {
      bitternessProp.setValue(calculateBitterness(recipe, newValue));
    });
    bitternessProp = new SimpleObjectProperty<>(calculateBitterness(recipe, gravity));
    gravityField.textProperty().bindBidirectional(gravityProp, bigDecimalConverter);
    totalWaterProp = new SimpleObjectProperty<>(calculateTotalWater(recipe));
    colourProp = new SimpleObjectProperty<>(calculateColour(recipe));
    fermentableTableController.init(recipe.getFermentableAdditions(), () -> {
      colourProp.setValue(calculateColour(recipe));
      totalWaterProp.set(calculateTotalWater(recipe));
      gravityProp.setValue(calculateGravity(recipe));
    }, colourProp);
    hopsTableController.init(recipe.getHopAdditions(), () -> calculateBitterness(recipe, gravity), bitternessProp);
    yeastTableController.init(recipe.getYeastAdditions());
    fillMashTab();
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
