
package org.mybeer.calculator.bitterness;


import org.mybeer.model.recipe.HopAddition;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * A class that calculater the bitterness of multiple hop additions
 */
public class BitternessCalculator {
  private BitternessCalculationMethod bitternessCalculationMethod;

  public BitternessCalculator(BitternessCalculationMethod bitternessCalculationMethod) {
    this.bitternessCalculationMethod = bitternessCalculationMethod;
  }


  public BigDecimal calculate(Collection<HopAddition> hopAdditions, BigDecimal sg, BigDecimal volume) {
    BigDecimal totalBitterness = BigDecimal.valueOf(0);

    for (HopAddition hopAddition : hopAdditions) {
      totalBitterness = totalBitterness.add(bitternessCalculationMethod.calculate(hopAddition, sg, volume));
    }

    return totalBitterness;
  }

}
