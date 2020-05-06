
package org.mybeer.calculator.bitterness;


import org.mybeer.model.recipe.HopAddition;

import java.math.BigDecimal;
import java.util.List;

/**
 * A class that calculater the bitterness of multiple hop additions
 */
public class BitternessCalculator {
    private BitternessCalculationMethod bitternessCalculationMethod;

    BitternessCalculator(BitternessCalculationMethod bitternessCalculationMethod)
    {
        this.bitternessCalculationMethod = bitternessCalculationMethod;
    }


    BigDecimal calculate(List<HopAddition> hopAdditions, BigDecimal sg, BigDecimal volume)
    {
        BigDecimal totalBitterness = BigDecimal.valueOf(0);
        
        for(HopAddition hopAddition : hopAdditions){
            totalBitterness.add(bitternessCalculationMethod.calculate(hopAddition, sg, volume));
        }
        
        return totalBitterness;
    }

}
