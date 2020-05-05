/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mybeer.calculator.colour;

import java.math.BigDecimal;

/**
 *
 * @author Martijn
 */
public class RayDanielsMethod implements ColourCorrectionMethod{

    @Override
    public BigDecimal correctColour(BigDecimal colourUnits)
    {
        return BigDecimal.valueOf(0.2).multiply(colourUnits).add(BigDecimal.valueOf(8.4));
    }
}
