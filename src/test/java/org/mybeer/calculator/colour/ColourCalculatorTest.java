package org.mybeer.calculator.colour;


import org.junit.Before;
import org.junit.Test;
import org.mybeer.model.ingredient.Fermentable;
import org.mybeer.model.recipe.FermentableAddition;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Martijn
 */
public class ColourCalculatorTest
{

    private ColourCalculator instance;
    private Set<FermentableAddition> fermentableAdditions;

    @Before
    public void setUp()
    {
        instance = new ColourCalculator(BigDecimal.valueOf(20));
        setUpFermentableAdditions();
    }

    private void setUpFermentableAdditions()
    {
        fermentableAdditions = new HashSet<>();
        fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(5500), BigDecimal.valueOf(4)));
        fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(400), BigDecimal.valueOf(69)));
        fermentableAdditions.add(createFermentableAddition(BigDecimal.valueOf(250), BigDecimal.valueOf(112)));

    }

    private FermentableAddition createFermentableAddition(BigDecimal amount, BigDecimal colour)
    {
        final Fermentable fermentable = new Fermentable();
        fermentable.setColour(colour);
        fermentable.setName(UUID.randomUUID().toString());

        final FermentableAddition fermentableAddition = new FermentableAddition();
        fermentableAddition.setAmount(amount);
        fermentableAddition.setFermentable(fermentable);

        return fermentableAddition;
    }

    @Test
    public void testCalculateEBCDanielMorey()
    {
        BigDecimal actual = instance.calculateEBC(new DanielMoreyMethod(), fermentableAdditions);
        BigDecimal expected = BigDecimal.valueOf(17.769);

        assertEquals(expected, actual.setScale(3, RoundingMode.HALF_UP));
    }

    @Test
    public void testCalculateEBCRandyMosher()
    {
        BigDecimal actual = instance.calculateEBC(new RandyMosherMethod(), fermentableAdditions);
        BigDecimal expected = BigDecimal.valueOf(17.293);

        assertEquals(expected, actual.setScale(3, RoundingMode.HALF_UP));
    }

    @Test
    public void testCalculateEBCRayDaniels()
    {
        BigDecimal actual = instance.calculateEBC(new RayDanielsMethod(), fermentableAdditions);
        BigDecimal expected = BigDecimal.valueOf(21.904);

        assertEquals(expected, actual.setScale(3, RoundingMode.HALF_UP));
    }

    @Test
    public void testCalculateSRMDanielMorey()
    {
        BigDecimal actual = instance.calculateSRM(new DanielMoreyMethod(), fermentableAdditions);
        BigDecimal expected = BigDecimal.valueOf(9.020).setScale(3, RoundingMode.HALF_UP);

        assertEquals(expected, actual.setScale(3, RoundingMode.HALF_UP));
    }

    @Test
    public void testCalculateSRMRandyMosher()
    {
        BigDecimal actual = instance.calculateSRM(new RandyMosherMethod(), fermentableAdditions);
        BigDecimal expected = BigDecimal.valueOf(8.778);

        assertEquals(expected, actual.setScale(3, RoundingMode.HALF_UP));
    }

    @Test
    public void testCalculateSRMRayDaniels()
    {
        BigDecimal actual = instance.calculateSRM(new RayDanielsMethod(), fermentableAdditions);
        BigDecimal expected = BigDecimal.valueOf(11.119);

        assertEquals(expected, actual.setScale(3, RoundingMode.HALF_UP));
    }
}
