/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mybeer.model.recipe;

import org.junit.Test;
import org.mybeer.model.ingredient.Yeast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 *
 * @author Martijn
 */
public class YeastAdditionTest
{
    @Test
    public void testEqualsAreEqual()
    {
        Yeast yeast = new Yeast();
        AdditionMoment additionMoment1 = AdditionMoment.PRIMARY_FERMENTATION;
        YeastAddition yeastAdditionImpl1 = new YeastAddition();
        yeastAdditionImpl1.setAdditionMoment(additionMoment1);
        yeastAdditionImpl1.setYeast(yeast);

        AdditionMoment additionMoment2 = AdditionMoment.PRIMARY_FERMENTATION;
        YeastAddition yeastAdditionImpl2 = new YeastAddition();
        yeastAdditionImpl2.setAdditionMoment(additionMoment2);
        yeastAdditionImpl2.setYeast(yeast);

        assertEquals(yeastAdditionImpl1, yeastAdditionImpl2);
    }

    @Test
    public void testEqualsDifferentYeast()
    {
        Yeast yeast1 = new Yeast();
        yeast1.setName("Test");
        AdditionMoment additionMoment1 = AdditionMoment.PRIMARY_FERMENTATION;
        YeastAddition yeastAdditionImpl1 = new YeastAddition();
        yeastAdditionImpl1.setAdditionMoment(additionMoment1);
        yeastAdditionImpl1.setYeast(yeast1);

        Yeast yeast2 = new Yeast();
        yeast2.setName("Test2");
        AdditionMoment additionMoment2 = AdditionMoment.PRIMARY_FERMENTATION;
        YeastAddition yeastAdditionImpl2 = new YeastAddition();
        yeastAdditionImpl2.setAdditionMoment(additionMoment2);
        yeastAdditionImpl2.setYeast(yeast2);

        assertNotSame(yeastAdditionImpl1, yeastAdditionImpl2);
    }

    @Test
    public void testEqualsDifferentAdditionMoment()
    {
        Yeast yeast = new Yeast();
        AdditionMoment additionMoment1 = AdditionMoment.SECONDARY_FERMENTATION;
        YeastAddition yeastAdditionImpl1 = new YeastAddition();
        yeastAdditionImpl1.setAdditionMoment(additionMoment1);
        yeastAdditionImpl1.setYeast(yeast);

        AdditionMoment additionMoment2 = AdditionMoment.PRIMARY_FERMENTATION;
        YeastAddition yeastAdditionImpl2 = new YeastAddition();
        yeastAdditionImpl2.setAdditionMoment(additionMoment2);
        yeastAdditionImpl2.setYeast(yeast);

        assertNotSame(yeastAdditionImpl1, yeastAdditionImpl2);
    }
}
