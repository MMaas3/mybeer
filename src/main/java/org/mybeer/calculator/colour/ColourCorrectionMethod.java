package org.mybeer.calculator.colour;

import java.math.BigDecimal;

/**
 * A method needed to make a better colour estimation.
 */
public interface ColourCorrectionMethod
{

    /**
     * Corrects an estimated the beer colour.
     *
     * @param colourUnits colour in SRM.
     * @return the colour in SRM
     */
    BigDecimal correctColour(BigDecimal colourUnits);
}
