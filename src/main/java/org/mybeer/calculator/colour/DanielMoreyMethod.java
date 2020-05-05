/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mybeer.calculator.colour;

import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 *
 * @author Martijn
 */
public class DanielMoreyMethod implements ColourCorrectionMethod
{

    @Override
    public BigDecimal correctColour(BigDecimal colourUnits)
    {
        return BigDecimal.valueOf(1.49)
                         .multiply(BigDecimalMath.pow(colourUnits, BigDecimal.valueOf(0.69), MathContext.DECIMAL32));
    }
}
