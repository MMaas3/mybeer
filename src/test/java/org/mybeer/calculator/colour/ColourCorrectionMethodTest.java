/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mybeer.calculator.colour;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public abstract class ColourCorrectionMethodTest
{
    protected ColourCorrectionMethod instance;
    
    
    public void testCorrectColour(BigDecimal input, BigDecimal expected){
       BigDecimal actual = instance.correctColour(input);
       
       assertEquals(expected, actual);
    }
}
