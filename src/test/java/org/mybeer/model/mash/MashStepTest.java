/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mybeer.model.mash;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public abstract class MashStepTest
{

    private MashStep instance;

    @Before
    public void setUp()
    {
        instance = createMashStep();
    }

    private MashStep createMashStep() {
        return new MashStep();
    }

    @Test
    public void testSetTimePositiveInt()
    {
        instance.setTime(15);

        assertEquals(15, instance.getTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetTimeNegativeInt()
    {
        instance.setTime(-9);
    }

    @Test
    public void testSetTemperaturePositiveInt()
    {
        instance.setTemperature(BigDecimal.valueOf(54.6));
        assertEquals(BigDecimal.valueOf(54.6), instance.getTemperature());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetTemperatureNegativeInt()
    {
        instance.setTemperature(BigDecimal.valueOf(-60));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetTemperatureZero()
    {
        instance.setTemperature(BigDecimal.valueOf(0));
    }

    @Test
    public void testEqualsEqual()
    {
        MashStep mashStep1 = createMashStep();
        mashStep1.setTemperature(BigDecimal.valueOf(15));
        mashStep1.setTime(16);

        MashStep mashStep2 = createMashStep();
        mashStep2.setTemperature(BigDecimal.valueOf(15));
        mashStep2.setTime(16);

        assertEquals(mashStep1, mashStep2);
    }

    @Test
    public void testEqualsUnequalTime()
    {
        MashStep mashStep1 = createMashStep();
        mashStep1.setTemperature(BigDecimal.valueOf(15));
        mashStep1.setTime(16);

        MashStep mashStep2 = createMashStep();
        mashStep2.setTemperature(BigDecimal.valueOf(15));
        mashStep2.setTime(29);

        assertEquals(mashStep1, mashStep2);
    }

    @Test
    public void testEqualsUnequalTemperature()
    {
        MashStep mashStep1 = createMashStep();
        mashStep1.setTemperature(BigDecimal.valueOf(15));
        mashStep1.setTime(16);

        MashStep mashStep2 = createMashStep();
        mashStep2.setTemperature(BigDecimal.valueOf(16));
        mashStep2.setTime(16);

        assertNotSame(mashStep1, mashStep2);
    }
}
