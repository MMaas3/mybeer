package org.mybeer.calculator.bitterness;

import org.junit.Test;
import org.mybeer.model.recipe.AdditionMoment;
import org.mybeer.model.recipe.HopAddition;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class RayDanielsBitternessMethodTest {
  @Test
  public void testBitternessCalculation() {
    final HopAddition hopAddition = new HopAddition();
    hopAddition.setHopsAlphaAcid(BigDecimal.valueOf(8.5));
    hopAddition.setAmount(BigDecimal.valueOf(7));
    hopAddition.setContactTime(15);
    hopAddition.setAdditionMoment(AdditionMoment.BOIL);
    final RayDanielsBitternessMethod instance = new RayDanielsBitternessMethod();

    final BigDecimal actual = instance.calculate(hopAddition, BigDecimal.valueOf(1.058), BigDecimal.valueOf(22));

    assertEquals(BigDecimal.valueOf(3), actual.setScale(0, RoundingMode.HALF_UP));
  }
}