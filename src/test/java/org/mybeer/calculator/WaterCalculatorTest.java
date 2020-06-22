package org.mybeer.calculator;

import org.junit.Test;
import org.mybeer.model.BrewingSystem;
import org.mybeer.model.ingredient.Fermentable;
import org.mybeer.model.ingredient.FermentableType;
import org.mybeer.model.ingredient.Hop;
import org.mybeer.model.recipe.AdditionMoment;
import org.mybeer.model.recipe.FermentableAddition;
import org.mybeer.model.recipe.HopAddition;
import org.mybeer.model.recipe.Recipe;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class WaterCalculatorTest {
  @Test
  public void calculateIgnoresSugars() {
    final Recipe recipe = new Recipe();
    recipe.setBoilTime(60);
    recipe.setVolume(BigDecimal.valueOf(15));
    addFermentableAddition(recipe, FermentableType.GRAIN, BigDecimal.valueOf(4000), AdditionMoment.MASH);
    addFermentableAddition(recipe, FermentableType.GRAIN, BigDecimal.valueOf(500), AdditionMoment.MASH);
    addFermentableAddition(recipe, FermentableType.GRAIN, BigDecimal.valueOf(400), AdditionMoment.MASH);
    addFermentableAddition(recipe, FermentableType.SUGAR, BigDecimal.valueOf(500), AdditionMoment.BOIL);
    addHopAddition(recipe, BigDecimal.valueOf(100));
    final WaterCalculator instance = new WaterCalculator();
    final BrewingSystem brewingSystem = new BrewingSystem();
    brewingSystem.setWaterLossMalt(BigDecimal.valueOf(1.2));
    brewingSystem.setLeafHopLoss(BigDecimal.valueOf(0.01));
    brewingSystem.setEvaporationRate(BigDecimal.valueOf(2.5));
    brewingSystem.setKettleLoss(BigDecimal.valueOf(0.3));
    brewingSystem.setRestLoss(BigDecimal.valueOf(2));
    brewingSystem.setYeastLoss(BigDecimal.valueOf(0.2));
    recipe.setSystem(brewingSystem);

    final BigDecimal volume = instance.calculate(recipe);

    assertEquals(BigDecimal.valueOf(25.98), volume.setScale(2, RoundingMode.HALF_UP));
  }

  private void addHopAddition(Recipe recipe, BigDecimal amount) {
    final HopAddition hopAddition = new HopAddition();
    hopAddition.setHop(new Hop());
    hopAddition.setAdditionMoment(AdditionMoment.BOIL);
    hopAddition.setContactTime(60);
    hopAddition.setAmount(amount);
    recipe.addHopAddition(hopAddition);
  }

  private void addFermentableAddition(Recipe recipe, FermentableType type, BigDecimal amount,
                                      AdditionMoment additionMoment) {
    final Fermentable fermentable = new Fermentable();
    fermentable.setName(UUID.randomUUID().toString());
    fermentable.setFermentableType(type);
    recipe.addFermentableAddition(new FermentableAddition(fermentable, additionMoment, amount));
  }

}