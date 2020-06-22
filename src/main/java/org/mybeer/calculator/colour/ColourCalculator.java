package org.mybeer.calculator.colour;


import org.mybeer.model.ingredient.Fermentable;
import org.mybeer.model.recipe.FermentableAddition;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Set;

import static org.mybeer.calculator.colour.ColourFormatConverter.srmToEbc;

/**
 * A class that can calculate the colour of a recipe.
 */
public class ColourCalculator {

  private static final BigDecimal GALLONS_TO_LITERS_CONSTANT = BigDecimal.valueOf(3.78541178);
  private static final BigDecimal KILOGRAMS_TO_LBS_CONSTANT = BigDecimal.valueOf(2.20462262);
  private BigDecimal volume;

  /**
   * @param volume the volume of the recipe in liters.
   */
  public ColourCalculator(BigDecimal volume) {
    this.volume = volume;
  }

  /**
   * Calculates the EBC colour of the recipe.
   *
   * @param colourCorrectionMethod the correction method to use.
   * @param fermentableAdditions   the fermentable additions to calculate the
   *                               colour of.
   * @return
   */
  public BigDecimal calculateEBC(ColourCorrectionMethod colourCorrectionMethod,
                             Set<FermentableAddition> fermentableAdditions) {
    return srmToEbc(calculateSRM(colourCorrectionMethod, fermentableAdditions));
  }

  /**
   * Calculates the SRM colour of the recipe.
   *
   * @param colourCorrectionMethod the correction method to use.
   * @param fermentableAdditions   the fermentable additions to calculate the
   *                               colour of.
   * @return
   */
  public BigDecimal calculateSRM(ColourCorrectionMethod colourCorrectionMethod,
                             Set<FermentableAddition> fermentableAdditions) {
    final BigDecimal volumeInGallons = getVolumeInGallons(volume);

    BigDecimal colourUnits = BigDecimal.valueOf(0.0);

    for (FermentableAddition fermentableAddition : fermentableAdditions) {
      BigDecimal fermentableColour = getLovibondColourOfMalt(fermentableAddition.getFermentable());
      BigDecimal weight = getWeightInLbs(fermentableAddition.getAmount());

      colourUnits = colourUnits.add(calculateColour(fermentableColour, weight, volumeInGallons));
    }

    return colourCorrectionMethod.correctColour(colourUnits);
  }

  public BigDecimal getWeightInLbs(BigDecimal weight) {
    return weight.divide(BigDecimal.valueOf(1000l)).multiply(KILOGRAMS_TO_LBS_CONSTANT);
  }

  private BigDecimal getVolumeInGallons(BigDecimal volume) {
    return volume.divide(GALLONS_TO_LITERS_CONSTANT, MathContext.DECIMAL32);
  }

  private BigDecimal getLovibondColourOfMalt(Fermentable fermentable) {
    return ColourFormatConverter.ebcToLovibond(fermentable.getColour());
  }


  /**
   * Calculate the colouring units the fermentable adds.
   *
   * @param colourInLovibond the colour of the fermentable in Lovibond.
   * @param weight           the amount in lbs of the fermentable is used.
   * @param recipeVolume     the volume in gallons.
   * @return the colour addition in SRM.
   */
  private BigDecimal calculateColour(BigDecimal colourInLovibond, BigDecimal weight, BigDecimal recipeVolume) {
    return colourInLovibond.multiply(weight).divide(recipeVolume, MathContext.DECIMAL32);
  }
}
