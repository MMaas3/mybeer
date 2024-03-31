package org.mybeer.calculator;

import org.mybeer.model.recipe.FermentableAddition;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collection;
import java.util.Optional;

public class GravityCalculator {

  public static final BigDecimal PLATO_SG_CONSTANT = BigDecimal.valueOf(259);

  public BigDecimal calculate(Collection<FermentableAddition> fermentableAdditions, BigDecimal volume,
                              BigDecimal efficiency) {
    final Optional<BigDecimal> plato = fermentableAdditions.stream()
                                                           .map(add -> calculatePlatoForAddition(add, volume))
                                                           .reduce(BigDecimal::add);

    return plato.map(val -> val.multiply(efficiency)).map(this::platoToSg).orElse(BigDecimal.ZERO);
  }

  private BigDecimal platoToSg(BigDecimal plato) {

    return PLATO_SG_CONSTANT.divide(PLATO_SG_CONSTANT.subtract(plato), MathContext.DECIMAL32);
  }

  private BigDecimal calculatePlatoForAddition(FermentableAddition addition, BigDecimal volume) {
    return addition.getFermentable().getYield().multiply(toKg(addition))
                   .divide(volume.divide(BigDecimal.valueOf(100), MathContext.DECIMAL32), MathContext.DECIMAL32);
  }

  private BigDecimal toKg(FermentableAddition addition) {
    return addition.getAmount() == null ? new BigDecimal(0) : addition.getAmount().divide(BigDecimal.valueOf(1000), MathContext.DECIMAL32);
  }

}
