package org.mybeer.calculator.colour;

import java.math.BigDecimal;

public class ColourFormatConverter {
  private static final BigDecimal SRM_EBC_RATIO = BigDecimal.valueOf(1.97);

  private ColourFormatConverter() {

  }

  public static BigDecimal srmToEbc(BigDecimal colourSrm) {
    return colourSrm.multiply(SRM_EBC_RATIO);
  }

  public static BigDecimal ebcToSrm(BigDecimal colourToEbc) {
    return colourToEbc.divide(SRM_EBC_RATIO);
  }

  public static BigDecimal ebcToLovibond(BigDecimal colourEbc) {
    return BigDecimal.valueOf(-0.00000000000132303).multiply(colourEbc.pow(4))
                     .subtract(BigDecimal.valueOf(0.00000000291515).multiply(colourEbc.pow(3)))
                     .add(BigDecimal.valueOf(0.00000818515).multiply(colourEbc.pow(2)))
                     .add(BigDecimal.valueOf(0.372038).multiply(colourEbc))
                     .add(BigDecimal.valueOf(0.596351));
  }

  public static BigDecimal lovibondToEbc(BigDecimal colourLovibond) {
    return BigDecimal.valueOf(0.000000000176506).multiply(colourLovibond.pow(4))
                     .add(BigDecimal.valueOf(0.000000154529).multiply(colourLovibond.pow(3)))
                     .subtract(BigDecimal.valueOf(0.000159428).multiply(colourLovibond.pow(2)))
                     .add(BigDecimal.valueOf(2.68837).multiply(colourLovibond))
                     .subtract(BigDecimal.valueOf(1.6004));

  }
}
