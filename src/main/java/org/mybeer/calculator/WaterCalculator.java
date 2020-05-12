package org.mybeer.calculator;

import org.mybeer.model.BrewingSystem;
import org.mybeer.model.ingredient.FermentableType;
import org.mybeer.model.recipe.FermentableAddition;
import org.mybeer.model.recipe.HopAddition;
import org.mybeer.model.recipe.Recipe;

import java.math.BigDecimal;
import java.math.MathContext;

import static java.math.BigDecimal.valueOf;

public class WaterCalculator {
  public BigDecimal calculate(Recipe recipe) {
    final BrewingSystem system = recipe.getSystem();

    return system.getYeastLoss().add(system.getKettleLoss()).add(system.getRestLoss())
                 .add(getMaltLoss(recipe, system))
                 .add(getHopLoss(recipe, system))
                 .add(getEvaporation(recipe, system))
                 .add(recipe.getVolume());
  }

  private BigDecimal getEvaporation(Recipe recipe, BrewingSystem system) {
    return valueOf(recipe.getBoilTime()).multiply(system.getEvaporationRate())
                                      .divide(valueOf(60), MathContext.DECIMAL32);
  }

  private BigDecimal getHopLoss(Recipe recipe, BrewingSystem system) {
    return getHopWeightInKg(recipe).multiply(system.getLeafHopLoss()).multiply(valueOf(100));
  }

  private BigDecimal getMaltLoss(Recipe recipe, BrewingSystem system) {
    return getFermentableWeightInKg(recipe).multiply(system.getWaterLossMalt());
  }

  private BigDecimal getHopWeightInKg(Recipe recipe) {
    return recipe.getHopAdditions().stream()
                 .map(HopAddition::getAmount)
                 .reduce(BigDecimal::add)
                 .map(weight -> weight.divide(valueOf(1000), MathContext.DECIMAL32))
                 .orElse(BigDecimal.ZERO);
  }

  private BigDecimal getFermentableWeightInKg(Recipe recipe) {
    return recipe.getFermentableAdditions().stream()
                 .filter(fa -> fa.getFermentable().getFermentableType() == FermentableType.GRAIN)
                 .map(FermentableAddition::getAmount)
                 .reduce(BigDecimal::add)
                 .map(weight -> weight.divide(valueOf(1000), MathContext.DECIMAL32))
                 .orElse(BigDecimal.ZERO);
  }
}
