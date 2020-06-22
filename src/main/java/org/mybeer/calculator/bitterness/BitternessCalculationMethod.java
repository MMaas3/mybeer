
package org.mybeer.calculator.bitterness;


import org.mybeer.model.recipe.HopAddition;

import java.math.BigDecimal;

/**
 * The way the bitterness is calculated.
 */
public interface BitternessCalculationMethod {
    /**
     * Calculates the bitterness of a hop addition
     * @param hopAddition the addition to calculate the bitterness of.
     * @param sg the sg of the wort.
     * @param volume
     * @return the bitterness of the hop addition.
     */
    BigDecimal calculate(HopAddition hopAddition, BigDecimal sg, BigDecimal volume);
}
