package org.mybeer.model.ingredient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class YeastTest
{
    
    public YeastTest()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }
    
    @Test
    public void testEqualsAreEqual()
    {
        Yeast yeastImpl1 = new Yeast();
        yeastImpl1.setName("Name");

        Yeast yeastImpl2 = new Yeast();
        yeastImpl2.setName("Name");

        boolean result1 = yeastImpl1.equals(yeastImpl2);
        boolean result2 = yeastImpl2.equals(yeastImpl1);

        assertTrue(result1);
        assertTrue(result2);
    }

    @Test
    public void testEqualsAreNotEqual()
    {
        Yeast yeastImpl1 = new Yeast();
        yeastImpl1.setName("Name");

        Yeast yeastImpl2 = new Yeast();
        yeastImpl2.setName("Name1");

        boolean result1 = yeastImpl1.equals(yeastImpl2);
        boolean result2 = yeastImpl2.equals(yeastImpl1);

        assertFalse(result1);
        assertFalse(result2);
    }
}
