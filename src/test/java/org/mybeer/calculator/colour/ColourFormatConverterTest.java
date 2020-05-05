package org.mybeer.calculator.colour;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.*;

public class ColourFormatConverterTest {
  @Test
  public void testLovibondToEbc() {
    assertEquals(BigDecimal.valueOf(25.268), ColourFormatConverter.lovibondToEbc(BigDecimal.TEN).setScale(3, RoundingMode.HALF_UP));
  }

  @Test
  public void testEbcToLovibond() {
    assertEquals(BigDecimal.valueOf(4.318), ColourFormatConverter.ebcToLovibond(BigDecimal.TEN).setScale(3, RoundingMode.HALF_UP));
  }
}