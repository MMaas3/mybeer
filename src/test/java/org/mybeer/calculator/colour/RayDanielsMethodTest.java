package org.mybeer.calculator.colour;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class RayDanielsMethodTest extends ColourCorrectionMethodTest {
  @Before
  public void setUp() {
    instance = new RayDanielsMethod();
  }

  @After
  public void tearDown() {
    instance = null;
  }

  @Test
  public void testCorrectColourZero() {
    testCorrectColour(BigDecimal.valueOf(0), BigDecimal.valueOf(8.4));
  }

  @Test
  public void testCorrectColourOne() {
    testCorrectColour(BigDecimal.valueOf(1), BigDecimal.valueOf(8.6));
  }

  @Test
  public void testCorrectColourFourty() {
    testCorrectColour(BigDecimal.valueOf(40), BigDecimal.valueOf(16.4));
  }

  @Test
  public void testCorrectColourSixtyFive() {
    testCorrectColour(BigDecimal.valueOf(65), BigDecimal.valueOf(21.4));
  }
}
