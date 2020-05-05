package org.mybeer.calculator.colour;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RandyMosherMethodTest extends ColourCorrectionMethodTest
{

    @Before
    public void setUp()
    {
        instance = new RandyMosherMethod();
    }

    @After
    public void tearDown()
    {
        instance = null;
    }

    @Test
    public void testCorrectColourZero()
    {
        testCorrectColour(BigDecimal.valueOf(0), BigDecimal.valueOf(4.7));
    }

    @Test
    public void testCorrectColourOne()
    {
        testCorrectColour(BigDecimal.valueOf(1), BigDecimal.valueOf(5).setScale(1, RoundingMode.UNNECESSARY));
    }

    @Test
    public void testCorrectColourFourty()
    {
        testCorrectColour(BigDecimal.valueOf(40), BigDecimal.valueOf(16.7));
    }

    @Test
    public void testCorrectColourSixtyFive()
    {
        testCorrectColour(BigDecimal.valueOf(65), BigDecimal.valueOf(24.2));
    }
}
