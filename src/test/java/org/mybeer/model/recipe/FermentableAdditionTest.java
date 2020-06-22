/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mybeer.model.recipe;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mybeer.model.ingredient.Fermentable;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Martijn
 */
public class FermentableAdditionTest
{

    Fermentable fermentable;

    public FermentableAdditionTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
            throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass()
            throws Exception
    {
    }

    @Before
    public void setUp()
    {
        fermentable = new Fermentable();
        fermentable.setName("Test");

    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of equals method, of class FermentableAddition.
     */
    @Test
    public void testEqualsAreEqual()
    {
        Object obj = new FermentableAddition(fermentable, AdditionMoment.MASH, BigDecimal.valueOf(5.12));
        FermentableAddition instance = new FermentableAddition(fermentable, AdditionMoment.MASH, BigDecimal.valueOf(3.14));
        boolean result = instance.equals(obj);

        assertEquals(true, result);
    }

    /**
     * Test of equals method, of class FermentableAddition.
     */
    @Test
    public void testEqualsFermentablesAreNotEqual()
    {
        Fermentable fermentable2 = new Fermentable();
        fermentable2.setName("Test1");

        Object obj = new FermentableAddition(fermentable2, AdditionMoment.MASH, BigDecimal.valueOf(3.14));
        FermentableAddition instance = new FermentableAddition(fermentable, AdditionMoment.MASH, BigDecimal.valueOf(3.14));
        boolean result = instance.equals(obj);

        assertEquals(false, result);
    }

    @Test
    public void testEqualsAdditionMomentsAreNotEqual()
    {
        Object obj = new FermentableAddition(fermentable, AdditionMoment.BOIL, BigDecimal.valueOf(3.14));
        FermentableAddition instance = new FermentableAddition(fermentable, AdditionMoment.MASH, BigDecimal.valueOf(3.14));

        boolean result = instance.equals(obj);
        assertEquals(false, result);
    }
}
