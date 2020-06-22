package org.mybeer.model.ingredient;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HopTest
{

    public HopTest()
    {
    }

    @Test
    public void testEqualsAreEqual()
    {
        Hop hopImpl1 = new Hop();
        hopImpl1.setName("Name");

        Hop hopImpl2 = new Hop();
        hopImpl2.setName("Name");

        boolean result1 = hopImpl1.equals(hopImpl2);
        boolean result2 = hopImpl2.equals(hopImpl1);

        assertTrue(result1);
        assertTrue(result2);
    }

    @Test
    public void testEqualsAreNotEqual()
    {
        Hop hopImpl1 = new Hop();
        hopImpl1.setName("Name1");

        Hop hopImpl2 = new Hop();
        hopImpl2.setName("Name2");

        boolean result1 = hopImpl1.equals(hopImpl2);
        boolean result2 = hopImpl2.equals(hopImpl1);

        assertFalse(result1);
        assertFalse(result2);
    }
}
