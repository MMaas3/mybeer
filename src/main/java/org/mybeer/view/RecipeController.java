package org.mybeer.view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.NumberStringConverter;
import org.hibernate.Session;
import org.mybeer.calculator.GravityCalculator;
import org.mybeer.calculator.WaterCalculator;
import org.mybeer.calculator.bitterness.BitternessCalculator;
import org.mybeer.calculator.bitterness.RayDanielsBitternessMethod;
import org.mybeer.calculator.colour.ColourCalculator;
import org.mybeer.calculator.colour.DanielMoreyMethod;
import org.mybeer.hibernate.FermentableDao;
import org.mybeer.hibernate.HopDao;
import org.mybeer.hibernate.RecipeDao;
import org.mybeer.hibernate.SessionFactorySingleton;
import org.mybeer.hibernate.YeastDao;
import org.mybeer.model.ingredient.Fermentable;
import org.mybeer.model.ingredient.Hop;
import org.mybeer.model.ingredient.Yeast;
import org.mybeer.model.mash.MashScheme;
import org.mybeer.model.mash.MashStep;
import org.mybeer.model.recipe.FermentableAddition;
import org.mybeer.model.recipe.HopAddition;
import org.mybeer.model.recipe.Recipe;
import org.mybeer.model.recipe.YeastAddition;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
  @FXML
  private TableView<HopAddition> hopsTable;
  @FXML
  private TableView<YeastAddition> yeastTable;
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
  private TextField colourField;
  @FXML
  private TextField bitternessField;
  @FXML
  private TextField totalWaterField;
  @FXML
  private Button refreshButton;

  public void init(Long recipeId, EventHandler<ActionEvent> actionEventEventHandler) {
    this.backButton.setOnAction(actionEventEventHandler);
    this.refreshButton.setOnAction((actionEvent) -> populateForm());
    try (final Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
      final Optional<Recipe> recipeOptional = new RecipeDao().getById(recipeId, session);
      if (recipeOptional.isEmpty()) {
        throw new RuntimeException("Recipe does not exist");
      }

      this.recipe = recipeOptional.get();
      populateForm();
    }
  }

  private void populateForm() {
    nameField.textProperty().bindBidirectional(new SimpleStringProperty(this.recipe.getName()));
    descriptionField.textProperty().bindBidirectional(new SimpleObjectProperty<>(this.recipe.getDescription()));
    boilTimeField.textProperty()
                 .bindBidirectional(new SimpleIntegerProperty(this.recipe.getBoilTime()), new NumberStringConverter());
    final BigDecimalStringConverter bigDecimalConverter = new BigDecimalStringConverter();
    // FIXME work with decimals
    bindField(bigDecimalConverter, efficiencyField, () -> recipe.getEfficiency(), (val) -> recipe.setEfficiency(val));
    bindField(bigDecimalConverter, volumeField, () -> recipe.getVolume(), (val) -> recipe.setVolume(val));
    final BigDecimal gravity = calculateGravity(recipe);
    gravityField.textProperty()
                .bindBidirectional(new SimpleObjectProperty<>(gravity), bigDecimalConverter);
    colourField.textProperty()
               .bindBidirectional(new SimpleObjectProperty<>(calculateColour(recipe)), bigDecimalConverter);
    bitternessField.textProperty()
                   .bindBidirectional(new SimpleObjectProperty<>(calculateBitterness(recipe, gravity)),
                       bigDecimalConverter);
    fillFermentablesTable();
    fillHopsTable();
    fillYeastTable();
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
    totalWaterField.textProperty()
                   .bindBidirectional(new SimpleObjectProperty<>(calculateTotalWater(recipe)), bigDecimalConverter);

    mashTable.setItems(FXCollections.observableArrayList(mashScheme.getMashSteps()));
    this.addPropertyColumn("Temperature", "temperature", mashTable, false, false);
    this.addPropertyColumn("Time", "time", mashTable, false, false);
    this.addPropertyColumn("Thickness", "thickness", mashTable, false, false);
  }

  private BigDecimal calculateTotalWater(Recipe recipe) {
    final WaterCalculator waterCalculator = new WaterCalculator();
    return waterCalculator.calculate(recipe);
  }

  private void fillYeastTable() {
    yeastTable.setItems(FXCollections.observableArrayList(recipe.getYeastAdditions()));
    // this.<YeastAddition, BigDecimal>addPropertyColumn("Amount", "amount", yeastTable, false, false);
    yeastTable.setEditable(true);

    final TableColumn<YeastAddition, BigDecimal> amountColumn = new TableColumn<>("Amount");
    amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    amountColumn.setComparator(amountColumn.getComparator());
    amountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
    amountColumn.setOnEditCommit((event -> {
      event.getTableView().getItems().get(event.getTablePosition().getRow()).setAmount(event.getNewValue());
    }));
    amountColumn.setEditable(true);
    yeastTable.getColumns().add(amountColumn);
    yeastTable.getSortOrder().add(amountColumn);


    final TableColumn<YeastAddition, ComboBox<Yeast>> yeastColumn = new TableColumn<>("Yeast");
    yeastColumn.setCellValueFactory(param -> {
      final ComboBox<Yeast> comboBox = new ComboBox<>();
      final Yeast yeast = param.getValue().getYeast();
      try (Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
        final List<Yeast> yeasts = new YeastDao().getAll(session).collect(Collectors.toList());
        comboBox.setItems(FXCollections.observableArrayList(yeasts));
        comboBox.setValue(yeast);
        comboBox.setConverter(new StringConverter<>() {
          @Override
          public String toString(Yeast object) {
            return object.getName();
          }

          @Override
          public Yeast fromString(String string) {
            return yeasts.stream().filter(yeast -> yeast.getName().equals(string)).findAny().orElseThrow();
          }
        });
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
          param.getValue().setYeast(newValue);
        });
      }

      return Bindings.createObjectBinding(() -> comboBox);
    });
    yeastColumn.setEditable(true);
    yeastTable.getColumns().add(yeastColumn);

    this.<YeastAddition, String>addPropertyColumn("Addtion moment", "additionMoment", yeastTable, false, false);
  }

  private void fillHopsTable() {
    hopsTable.setItems(FXCollections.observableArrayList(this.recipe.getHopAdditions()));
    hopsTable.setEditable(true);
    // this.<HopAddition, BigDecimal>addPropertyColumn("Amount", "amount", hopsTable, false, false);
    final TableColumn<HopAddition, BigDecimal> amountColumn = new TableColumn<>("Amount");
    amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    final BigDecimalStringConverter bigDecimalConverter = new BigDecimalStringConverter();
    amountColumn.setCellFactory(TextFieldTableCell.forTableColumn(bigDecimalConverter));
    amountColumn.setOnEditCommit((event -> {
      event.getTableView().getItems().get(event.getTablePosition().getRow()).setAmount(event.getNewValue());
    }));
    amountColumn.setEditable(true);
    hopsTable.getColumns().add(amountColumn);

    final TableColumn<HopAddition, ComboBox<Hop>> hopColumn = new TableColumn<>("Hop");
    hopColumn.setCellValueFactory(param -> {
      final HopAddition value = param.getValue();
      final ComboBox<Hop> comboBox = new ComboBox<>();
      try (final Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
        final HopDao hopDao = new HopDao();
        final List<Hop> hops = hopDao.getAll(session).collect(Collectors.toList());
        comboBox.setItems(FXCollections.observableArrayList(hops));

        comboBox.setConverter(new StringConverter<>() {
          @Override
          public String toString(Hop hop) {
            return hop.getName();
          }

          @Override
          public Hop fromString(String string) {
            return hops.stream().filter(hop -> hop.getName().equals(string)).findFirst().orElseThrow();
          }
        });
      }
      comboBox.setValue(value.getHop());
      comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
        param.getValue().setHop(newValue);
        param.getValue().setHopsAlphaAcid(newValue.getAlphaAcidPercentageMin());
      });

      return Bindings.createObjectBinding(() -> comboBox);
    });
    this.hopsTable.getColumns().add(hopColumn);

    final TableColumn<HopAddition, BigDecimal> alphaAcidColumn = new TableColumn<>("Alpha Acid %");
    alphaAcidColumn.setCellValueFactory(new PropertyValueFactory<>("hopsAlphaAcid"));
    alphaAcidColumn.setCellFactory(TextFieldTableCell.forTableColumn(bigDecimalConverter));
    alphaAcidColumn.setEditable(true);
    alphaAcidColumn.setOnEditCommit((event -> {
      event.getTableView().getItems().get(event.getTablePosition().getRow()).setHopsAlphaAcid(event.getNewValue());
    }));
    hopsTable.getColumns().add(alphaAcidColumn);


    this.<HopAddition, String>addPropertyColumn("Moment", "additionMoment", hopsTable, true, false);
    this.<HopAddition, Integer>addPropertyColumn("Contact time", "contactTime", hopsTable, true, true);

  }

  private void fillFermentablesTable() {
    fermetablesTable.setEditable(true);
    fermetablesTable.setItems(FXCollections.observableArrayList(this.recipe.getFermentableAdditions()));

    // this.<FermentableAddition, BigDecimal>addPropertyColumn("Amount", "amount", this.fermetablesTable, true, true);

    final TableColumn<FermentableAddition, BigDecimal> amountColumn = new TableColumn<>("Amount");
    amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    final BigDecimalStringConverter bigDecimalConverter = new BigDecimalStringConverter();
    amountColumn.setCellFactory(TextFieldTableCell.forTableColumn(bigDecimalConverter));
    amountColumn.setOnEditCommit((event -> {
      event.getTableView().getItems().get(event.getTablePosition().getRow()).setAmount(event.getNewValue());
    }));
    amountColumn.setEditable(true);
    fermetablesTable.getSortOrder().add(amountColumn);
    fermetablesTable.getColumns().add(amountColumn);

    final TableColumn<FermentableAddition, ComboBox<Fermentable>> fermentableColumn = new TableColumn<>("Fermentable");
    fermentableColumn.setCellValueFactory(param -> {
      final FermentableAddition fermentableAddition = param.getValue();
      final ComboBox<Fermentable> comboBox = new ComboBox<>();

      try (final Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
        final FermentableDao fermentableDao = new FermentableDao();
        final List<Fermentable> fermentables = fermentableDao.getAll(session).collect(Collectors.toList());
        comboBox.setItems(FXCollections.observableArrayList(fermentables));
        comboBox.setConverter(new StringConverter<Fermentable>() {
          @Override
          public String toString(Fermentable fermentable) {
            return fermentable.getName();
          }

          @Override
          public Fermentable fromString(String name) {
            return fermentables.stream().filter(fermentable -> fermentable.getName().equals(name)).findFirst()
                               .orElseThrow();
          }
        });
      }

      comboBox.setValue(fermentableAddition.getFermentable());
      comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
        param.getValue().setFermentable(newValue);
      });

      return Bindings.createObjectBinding(() -> comboBox);
    });
    fermentableColumn.setEditable(true);
    fermetablesTable.getColumns().add(fermentableColumn);

    final TableColumn<FermentableAddition, BigDecimal> fermentableColourColumn = new TableColumn<>("Colour (EBC)");
    fermentableColourColumn.setCellValueFactory(
        param -> {
          final FermentableAddition addition = param.getValue();
          return Bindings.createObjectBinding(() -> addition.getFermentable().getColour());
        });
    fermetablesTable.getColumns().add(fermentableColourColumn);

    this.<FermentableAddition, String>addPropertyColumn("Moment", "additionMoment", this.fermetablesTable, false,
        false);
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
