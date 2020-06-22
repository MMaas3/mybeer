/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mybeer.model.recipe;

import org.junit.Assert;
import org.junit.Test;
import org.mybeer.model.ingredient.Hop;

import java.math.BigDecimal;

/**
 *
 * @author Martijn
 */
public class HopAdditionTest
{

    /**
     * Test of equals method, of class HopAddition.
     */
    @Test
    public void testEqualsIsEqual()
    {
        Hop hop = new Hop();
        HopAddition hopAddition1 = new HopAddition(AdditionMoment.BOIL, 60, hop, BigDecimal.valueOf(5));
        HopAddition hopAddition2 = new HopAddition(AdditionMoment.BOIL, 60, hop, BigDecimal.valueOf(3.14));

        Assert.assertEquals(true, hopAddition1.equals(hopAddition2));
    }

    @Test
    public void testEqualsDifferentHop()
    {
        Hop hop1 = new Hop();
        hop1.setName("Test");
        Hop hop2 = new Hop();
        hop2.setName("Test1");
        HopAddition hopAddition1 = new HopAddition(AdditionMoment.BOIL, 60, hop1, BigDecimal.valueOf(5));
        HopAddition hopAddition2 = new HopAddition(AdditionMoment.BOIL, 60, hop2, BigDecimal.valueOf(5));

        Assert.assertEquals(false, hopAddition1.equals(hopAddition2));
    }

    @Test
    public void testEqualsDifferentAdditionMoment()
    {
        Hop hop = new Hop();
        HopAddition hopAddition1 = new HopAddition(AdditionMoment.BOIL, 60, hop, BigDecimal.valueOf(5));
        HopAddition hopAddition2 = new HopAddition(AdditionMoment.PRIMARY_FERMENTATION, 60, hop, BigDecimal.valueOf(5));

        Assert.assertEquals(false, hopAddition1.equals(hopAddition2));
    }

    @Test
    public void testEqualsDifferentContactTime()
    {
        Hop hop = new Hop();
        HopAddition hopAddition1 = new HopAddition(AdditionMoment.BOIL, 60, hop, BigDecimal.valueOf(5));
        HopAddition hopAddition2 = new HopAddition(AdditionMoment.BOIL, 15, hop, BigDecimal.valueOf(5));

        Assert.assertEquals(false, hopAddition1.equals(hopAddition2));
    }
}
