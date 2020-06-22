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
import org.mybeer.model.ingredient.FermentableType;
import org.mybeer.model.ingredient.Hop;
import org.mybeer.model.ingredient.HopForm;
import org.mybeer.model.ingredient.Yeast;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 *
 * @author Martijn
 */
public class RecipeTest
{

    public RecipeTest()
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
    }

    @After
    public void tearDown()
    {
    }

    private Fermentable createFermentable(final boolean shouldBeMashed)
    {
        Fermentable fermentable = new Fermentable();
        if(shouldBeMashed) {
            fermentable.setFermentableType(FermentableType.GRAIN);
        } else {
            fermentable.setFermentableType(FermentableType.SUGAR);
        }

        return fermentable;
    }

    private HopAddition createHopAddition(final Hop hop, final AdditionMoment additionMoment, final BigDecimal weight, final int contactTime)
    {
        HopAddition hopAddition = new HopAddition();
        hopAddition.setAdditionMoment(additionMoment);
        hopAddition.setHop(hop);
        hopAddition.setAmount(weight);
        hopAddition.setContactTime(contactTime);

        return hopAddition;
    }

    private Recipe createRecipeWithBoilTime(int boilTime)
    {
        Recipe recipe = new Recipe();
        recipe.setBoilTime(boilTime);

        return recipe;
    }

    private Recipe createRecipeWithId(Long id)
    {
        Recipe recipe = new Recipe();
        recipe.setId(id);

        return recipe;
    }

    /**
     * Test of addHopAdditionImpl method, of class Recipe.
     */
    @Test
    public void testAddFermentableAdditionMashAddition()
    {
        Recipe recipe = createRecipeWithBoilTime(60);
        AdditionMoment additionMoment = AdditionMoment.MASH;
        Fermentable shouldBeMashed = createFermentable(true);

        assertEquals(0, recipe.getFermentableAdditions().size());

        recipe.addFermentableAddition(new FermentableAddition(shouldBeMashed, additionMoment, BigDecimal.valueOf(3.14)));

        assertEquals(1, recipe.getFermentableAdditions().size());
    }

    @Test
    public void testAddFermentableAdditionMashAdditionNoMashNeeded()
    {
        Recipe recipe = createRecipeWithBoilTime(60);
        AdditionMoment additionMoment = AdditionMoment.BOIL;
        Fermentable noMashNeeded = createFermentable(false);

        assertEquals(0, recipe.getFermentableAdditions().size());

        recipe.addFermentableAddition(new FermentableAddition(noMashNeeded, additionMoment, BigDecimal.valueOf(3.14)));

        assertEquals(1, recipe.getFermentableAdditions().size());
    }

    @Test(expected = FermentableShouldBeMashedException.class)
    public void testAddFermentableAdditionWrongAdditionMoment()
    {
        Recipe recipe = createRecipeWithBoilTime(60);
        AdditionMoment additionMoment = AdditionMoment.POST_BOIL;
        Fermentable shouldBeMashed = createFermentable(true);

        assertEquals(0, recipe.getFermentableAdditions().size());

        recipe.addFermentableAddition(new FermentableAddition(shouldBeMashed, additionMoment, BigDecimal.valueOf(3.14)));
    }

    @Test
    public void testAddFermentableAdditionDuplicateAddition()
    {
        Recipe recipe = createRecipeWithBoilTime(60);

        Fermentable fermentable = createFermentable(true);

        AdditionMoment additionMoment1 = AdditionMoment.MASH;
        AdditionMoment additionMoment2 = AdditionMoment.MASH;

        recipe.addFermentableAddition(new FermentableAddition(fermentable, additionMoment1, BigDecimal.valueOf(5)));

        recipe.addFermentableAddition(new FermentableAddition(fermentable, additionMoment2, BigDecimal.valueOf(2.12)));

        assertEquals(1, recipe.getFermentableAdditions().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFermentableAdditionFermentableNull()
    {
        Recipe recipe = createRecipeWithBoilTime(60);
        Fermentable fermentable = null;
        AdditionMoment additionMoment = null;

        assertEquals(0, recipe.getFermentableAdditions().size());

        recipe.addFermentableAddition(new FermentableAddition(fermentable, additionMoment, BigDecimal.valueOf(3.14)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFermentableAdditionAdditionMomentNull()
    {
        Recipe recipe = createRecipeWithBoilTime(60);
        Fermentable fermentable = createFermentable(true);

        AdditionMoment additionMoment = null;

        recipe.addFermentableAddition(new FermentableAddition(fermentable, additionMoment, BigDecimal.valueOf(3.14)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFermentableAdditionWeightZero()
    {
        Recipe recipe = createRecipeWithBoilTime(60);
        Fermentable fermentable = createFermentable(true);
        AdditionMoment additionMoment = AdditionMoment.MASH;

        recipe.addFermentableAddition(new FermentableAddition(fermentable, additionMoment, BigDecimal.valueOf(0)));
    }

    /**
     * Test of addHopAdditionImpl method, of class Recipe.
     */
    @Test
    public void testAddHopAddition()
    {
        Recipe instanceWith60MinutesBoil = createRecipeWithBoilTime(60);
        Hop hop = new Hop();
        AdditionMoment additionMoment = AdditionMoment.BOIL;

        assertEquals(0, instanceWith60MinutesBoil.getHopAdditions().size());

        instanceWith60MinutesBoil.addHopAddition(createHopAddition(hop, additionMoment, BigDecimal.valueOf(3.14), 15));

        assertEquals(1, instanceWith60MinutesBoil.getHopAdditions().size());
    }

    @Test
    public void testAddHopAdditionDuplicateAddition()
    {
        Recipe instanceWith60MinutesBoil = createRecipeWithBoilTime(60);
        Hop hop = new Hop();
        hop.setName("test");
        hop.setAlphaAcidPercentageMin(BigDecimal.valueOf(8.5));
        hop.setForm(HopForm.LEAF);

        AdditionMoment additionMoment1 = AdditionMoment.BOIL;
        AdditionMoment additionMoment2 = AdditionMoment.BOIL;

        assertEquals(0, instanceWith60MinutesBoil.getHopAdditions().size());

        instanceWith60MinutesBoil.addHopAddition(createHopAddition(hop, additionMoment1, BigDecimal.valueOf(3.14), 60));
        instanceWith60MinutesBoil.addHopAddition(createHopAddition(hop, additionMoment2, BigDecimal.valueOf(3.14), 60));

        assertEquals(1, instanceWith60MinutesBoil.getHopAdditions().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddHopAdditionAdditionMomentNull()
    {
        Recipe instanceWith60MinutesBoil = createRecipeWithBoilTime(60);
        Hop hop = new Hop();
        AdditionMoment additionMoment = null;

        assertEquals(0, instanceWith60MinutesBoil.getHopAdditions().size());

        instanceWith60MinutesBoil.addHopAddition(createHopAddition(hop, additionMoment, BigDecimal.valueOf(3.14), 60));

        assertEquals(1, instanceWith60MinutesBoil.getHopAdditions().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddHopAdditionHopNull()
    {
        Recipe instanceWith60MinutesBoil = createRecipeWithBoilTime(60);
        Hop hop = null;
        AdditionMoment additionMoment = AdditionMoment.BOIL;

        assertEquals(0, instanceWith60MinutesBoil.getHopAdditions().size());

        instanceWith60MinutesBoil.addHopAddition(createHopAddition(hop, additionMoment, BigDecimal.valueOf(3.14), 60));

        assertEquals(1, instanceWith60MinutesBoil.getHopAdditions().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddHopAdditionContactTimeBiggerThanBoilTime()
    {
        Recipe instanceWith60MinutesBoil = createRecipeWithBoilTime(60);
        Hop hop = new Hop();
        AdditionMoment additionMoment = AdditionMoment.BOIL;

        instanceWith60MinutesBoil.addHopAddition(createHopAddition(hop, additionMoment, BigDecimal.valueOf(3.14), 70));
    }

    @Test
    public void testAddHopAdditionPrimaryFermentation()
    {
        Recipe instanceWith60MinutesBoil = createRecipeWithBoilTime(60);
        AdditionMoment additionMoment = AdditionMoment.PRIMARY_FERMENTATION;
        Hop hop = new Hop();
        int oneWeekContactTime = 7 * 24 * 60;
        HopAddition hopAddition = createHopAddition(hop, additionMoment, BigDecimal.valueOf(0.20), oneWeekContactTime);

        assertEquals(0, instanceWith60MinutesBoil.getHopAdditions().size());
        instanceWith60MinutesBoil.addHopAddition(hopAddition);
        assertEquals(1, instanceWith60MinutesBoil.getHopAdditions().size());
    }

    @Test
    public void testAddHopAdditionSecondaryFermentation()
    {
        Recipe instanceWith60MinutesBoil = createRecipeWithBoilTime(60);
        AdditionMoment additionMoment = AdditionMoment.SECONDARY_FERMENTATION;
        Hop hop = new Hop();
        int oneWeekContactTime = 7 * 24 * 60;
        HopAddition hopAddition = createHopAddition(hop, additionMoment, BigDecimal.valueOf(0.20), oneWeekContactTime);

        assertEquals(0, instanceWith60MinutesBoil.getHopAdditions().size());
        instanceWith60MinutesBoil.addHopAddition(hopAddition);
        assertEquals(1, instanceWith60MinutesBoil.getHopAdditions().size());
    }

    @Test
    public void testAddHopAdditionPostBoil()
    {
        Recipe instanceWith60MinutesBoil = createRecipeWithBoilTime(60);
        AdditionMoment additionMoment = AdditionMoment.POST_BOIL;
        Hop hop = new Hop();
        int twoHourContact = 120;
        HopAddition hopAddition = createHopAddition(hop, additionMoment, BigDecimal.valueOf(0.20), twoHourContact);

        assertEquals(0, instanceWith60MinutesBoil.getHopAdditions().size());
        instanceWith60MinutesBoil.addHopAddition(hopAddition);
        assertEquals(1, instanceWith60MinutesBoil.getHopAdditions().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddHopAdditionWeightZero()
    {
        Recipe instanceWith60MinutesBoil = createRecipeWithBoilTime(60);
        Hop hop = new Hop();
        AdditionMoment additionMoment = AdditionMoment.BOIL;

        assertEquals(0, instanceWith60MinutesBoil.getHopAdditions().size());

        instanceWith60MinutesBoil.addHopAddition(createHopAddition(hop, additionMoment, BigDecimal.valueOf(0), 60));

        assertEquals(1, instanceWith60MinutesBoil.getHopAdditions().size());
    }

    @Test
    public void testAddYeastSuccess()
    {
        YeastAddition addition = createYeastAddition("Test", AdditionMoment.PRIMARY_FERMENTATION);
        YeastAddition addition1 = createYeastAddition("Test1", AdditionMoment.PRIMARY_FERMENTATION);

        Recipe recipeImpl = new Recipe();
        recipeImpl.addYeastAddition(addition);
        recipeImpl.addYeastAddition(addition1);
        assertEquals(2, recipeImpl.getYeastAdditions().size());
    }

    @Test
    public void testAddYeastAdditionDuplicateAddition()
    {
        YeastAddition addition = createYeastAddition("Test", AdditionMoment.PRIMARY_FERMENTATION);

        Recipe recipeImpl = new Recipe();
        recipeImpl.addYeastAddition(addition);
        recipeImpl.addYeastAddition(addition);

        assertEquals(1, recipeImpl.getYeastAdditions().size());
    }

    private YeastAddition createYeastAddition(final String yeastName, final AdditionMoment additionMoment)
    {
        Yeast yeast = new Yeast();
        yeast.setName(yeastName);

        YeastAddition addition = new YeastAddition();
        addition.setYeast(yeast);
        addition.setAdditionMoment(additionMoment);
        addition.setAmount(BigDecimal.valueOf(20));

        return addition;
    }

    @Test
    public void testEqualsAreEqual()
    {
        Recipe recipeImpl1 = createRecipeWithId(1l);
        Recipe recipeImpl2 = createRecipeWithId(1l);

        assertEquals(recipeImpl1, recipeImpl2);
    }

    @Test
    public void testEqualsAreNotEqual()
    {
        Recipe recipeImpl1 = createRecipeWithId(1l);
        Recipe recipeImpl2 = createRecipeWithId(2l);

        assertNotSame(recipeImpl1, recipeImpl2);
    }
}
