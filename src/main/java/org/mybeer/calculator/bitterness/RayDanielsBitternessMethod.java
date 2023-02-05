package org.mybeer.calculator.bitterness;

import org.mybeer.model.recipe.AdditionMoment;
import org.mybeer.model.recipe.HopAddition;

import java.math.BigDecimal;
import java.math.MathContext;

public class RayDanielsBitternessMethod implements BitternessCalculationMethod {

  public static final BigDecimal GRAVITY_BORDER = BigDecimal.valueOf(1.05);

  @Override
  public BigDecimal calculate(HopAddition hopAddition, BigDecimal sg, BigDecimal volume) {
    BigDecimal gravityCorrection = (sg.subtract(GRAVITY_BORDER)).divide(BigDecimal.valueOf(2)).add(BigDecimal.ONE);

    return hopAddition.getAmount()
               .multiply(getUtilizationFactor(hopAddition.getContactTime(), hopAddition.getAdditionMoment()))
               .multiply(hopAddition.getHopsAlphaAcid().divide(BigDecimal.valueOf(100))).multiply(BigDecimal.valueOf(1000))
               .divide(volume.multiply(gravityCorrection), MathContext.DECIMAL32);

  }

  private BigDecimal getUtilizationFactor(int contactTime, AdditionMoment additionMoment) {

    if(additionMoment == AdditionMoment.BOIL || additionMoment == AdditionMoment.FIRST_WORT) {
      if (contactTime >= 60) {
        return BigDecimal.valueOf(0.211);
      }
      if (contactTime >= 15) {
        return BigDecimal.valueOf(0.115);
      }
      if (contactTime > 0) {
        return BigDecimal.valueOf(0.042);
      }
    }
    return BigDecimal.ZERO;
  }
}
