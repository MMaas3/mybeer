package org.mybeer.calculator;

import org.junit.Assert;
import org.junit.Test;
import org.mybeer.model.ingredient.Fermentable;
import org.mybeer.model.recipe.FermentableAddition;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;
import static org.junit.Assert.assertEquals;

public class GravityCalculatorTest {
  @Test
  public void testCalculate() {
    final GravityCalculator instance = new GravityCalculator();
    final List<FermentableAddition> fermentableAdditions = new ArrayList<>();
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(0.74), BigDecimal.valueOf(5000)));
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(0.74), BigDecimal.valueOf(500)));
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(1), BigDecimal.valueOf(500)));
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(0.59), BigDecimal.valueOf(28)));

    final BigDecimal sg = instance.calculate(fermentableAdditions, BigDecimal.valueOf(15), BigDecimal.valueOf(0.62));

    assertEquals(BigDecimal.valueOf(1.079).setScale(3, HALF_UP), sg.setScale(3, HALF_UP));
  }

  private FermentableAddition createFermentableAddition(BigDecimal yield, BigDecimal amount) {
    final Fermentable fermentable = new Fermentable();
    fermentable.setYield(yield);
    final FermentableAddition fermentableAddition = new FermentableAddition();
    fermentableAddition.setFermentable(fermentable);
    fermentableAddition.setAmount(amount);
    return fermentableAddition;
  }

  @Test
  public void testCalculateHigherVolume() {
    final GravityCalculator instance = new GravityCalculator();
    final List<FermentableAddition> fermentableAdditions = new ArrayList<>();
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(0.74), BigDecimal.valueOf(5000)));
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(0.74), BigDecimal.valueOf(500)));
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(1), BigDecimal.valueOf(500)));
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(0.59), BigDecimal.valueOf(28)));

    final BigDecimal sg = instance.calculate(fermentableAdditions, BigDecimal.valueOf(20), BigDecimal.valueOf(0.62));

    assertEquals(BigDecimal.valueOf(1.058).setScale(3, HALF_UP), sg.setScale(3, HALF_UP));
  }

  @Test
  public void testCalculateLowerVolume() {
    final GravityCalculator instance = new GravityCalculator();
    final List<FermentableAddition> fermentableAdditions = new ArrayList<>();
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(0.74), BigDecimal.valueOf(5000)));
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(0.74), BigDecimal.valueOf(500)));
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(1), BigDecimal.valueOf(500)));
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(0.59), BigDecimal.valueOf(28)));

    final BigDecimal sg = instance.calculate(fermentableAdditions, BigDecimal.valueOf(10), BigDecimal.valueOf(0.62));

    assertEquals(BigDecimal.valueOf(1.123).setScale(3, HALF_UP), sg.setScale(3, HALF_UP));
  }

  @Test
  public void testCalculateHigherEfficiency() {
    final GravityCalculator instance = new GravityCalculator();
    final List<FermentableAddition> fermentableAdditions = new ArrayList<>();
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(0.74), BigDecimal.valueOf(5000)));
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(0.74), BigDecimal.valueOf(500)));
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(1), BigDecimal.valueOf(500)));
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(0.59), BigDecimal.valueOf(28)));

    final BigDecimal sg = instance.calculate(fermentableAdditions, BigDecimal.valueOf(15), BigDecimal.valueOf(0.75));

    assertEquals(BigDecimal.valueOf(1.097).setScale(3, HALF_UP), sg.setScale(3, HALF_UP));
  }

  @Test
  public void testCalculateLowerEfficiency() {
    final GravityCalculator instance = new GravityCalculator();
    final List<FermentableAddition> fermentableAdditions = new ArrayList<>();
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(0.74), BigDecimal.valueOf(5000)));
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(0.74), BigDecimal.valueOf(500)));
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(1), BigDecimal.valueOf(500)));
    fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(0.59), BigDecimal.valueOf(28)));

    final BigDecimal sg = instance.calculate(fermentableAdditions, BigDecimal.valueOf(15), BigDecimal.valueOf(0.55));

    assertEquals(BigDecimal.valueOf(1.069).setScale(3, HALF_UP), sg.setScale(3, HALF_UP));
  }
}