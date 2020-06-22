package org.mybeer.calculator.colour;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class DanielMoreyMethodTest extends ColourCorrectionMethodTest
{
    @Before
    public void setUp(){
        instance = new DanielMoreyMethod();
    }
    
    @After
    public void tearDown(){
        instance = null;
    }
    
    @Test
    public void testCorrectColourZero(){
        testCorrectColour(BigDecimal.valueOf(0), BigDecimal.valueOf(0.00).setScale(2, RoundingMode.UNNECESSARY));
    }
    
    @Test
    public void testCorrectColourOne(){
        testCorrectColour(BigDecimal.valueOf(1), BigDecimal.valueOf(1.49));
    }
    
    @Test
    public void testCorrectColourFourty(){
        testCorrectColour(BigDecimal.valueOf(40), BigDecimal.valueOf(18.9936111));
    }
    
    @Test
    public void testCorrectColourSixtyFive(){
        testCorrectColour(BigDecimal.valueOf(65), BigDecimal.valueOf(26.5519490).setScale(7, RoundingMode.UNNECESSARY));
    }
}
