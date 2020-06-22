/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mybeer.calculator.colour;

import java.math.BigDecimal;

/**
 * @author Martijn
 */
public class RandyMosherMethod implements ColourCorrectionMethod {

  @Override
  public BigDecimal correctColour(BigDecimal colourUnits) {
    return BigDecimal.valueOf(0.3).multiply(colourUnits).add(BigDecimal.valueOf(4.7));
  }

}
