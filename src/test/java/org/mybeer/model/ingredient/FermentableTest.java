package org.mybeer.model.ingredient;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FermentableTest
{

    @Test
    public void testEqualsAreEqual()
    {
        Fermentable fermentable1 = new Fermentable();
        fermentable1.setName("Name");

        Fermentable fermentable2 = new Fermentable();
        fermentable2.setName("Name");

        boolean result1 = fermentable1.equals(fermentable2);
        boolean result2 = fermentable2.equals(fermentable1);

        assertTrue(result1);
        assertTrue(result2);
    }

    @Test
    public void testEqualsAreNotEqual()
    {
        Fermentable fermentable1 = new Fermentable();
        fermentable1.setName("Name1");

        Fermentable fermentable2 = new Fermentable();
        fermentable2.setName("Name2");

        boolean result = fermentable1.equals(fermentable2);
        boolean result2 = fermentable2.equals(fermentable1);

        assertFalse(result);
        assertFalse(result2);
    }

    @Test
    public void testSetPotententialExtract()
    {
        BigDecimal potentialExtract = BigDecimal.valueOf(1.036);
        BigDecimal yield = BigDecimal.valueOf(0.78);

        Fermentable fermentable = new Fermentable();
        fermentable.setPotentialExtract(potentialExtract);

        assertEquals(potentialExtract, fermentable.getPotentialExtract());
        assertEquals(yield, fermentable.getYield().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void testSetYield()
    {
        BigDecimal potentialExtract = BigDecimal.valueOf(1.036);
        BigDecimal yield = BigDecimal.valueOf(0.78);

        Fermentable fermentable = new Fermentable();
        fermentable.setYield(yield);

        assertEquals(yield, fermentable.getYield());
        assertEquals(potentialExtract, fermentable.getPotentialExtract().setScale(3, RoundingMode.HALF_UP));

    }
}
