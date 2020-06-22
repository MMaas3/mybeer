package org.mybeer.model.mash;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class MashSchemeTest {

  private MashScheme instance;

  @BeforeClass
  public static void setUpClass()
      throws Exception {
  }

  @AfterClass
  public static void tearDownClass()
      throws Exception {
  }

  @Before
  public void setUp() {
    instance = new MashScheme(BigDecimal.valueOf(20), BigDecimal.valueOf(5));
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of setMashWater method, of class MashScheme.
   */
  @Test
  public void testSetMashWater() {
    System.out.println("setMashWater");
    BigDecimal mashWater = BigDecimal.valueOf(15);
    instance.setMashWater(mashWater);

    assertCalculations(instance, BigDecimal.valueOf(20), mashWater, BigDecimal.valueOf(5), BigDecimal.valueOf(3));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetMashWaterSmallerThanZero() {
    System.out.println("setMashWaterSmallerThanZero");
    BigDecimal mashWater = BigDecimal.valueOf(-2);
    instance.setMashWater(mashWater);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetMashWaterLargerThanTotalWater() {
    System.out.println("setMashWaterLargerThanTotalWater");
    BigDecimal mashWater = BigDecimal.valueOf(22);
    instance.setMashWater(mashWater);
  }

  /**
   * Test of setSpargeWater method, of class MashScheme.
   */
  @Test
  public void testSetSpargeWater() {
    System.out.println("setSpargeWatter");
    BigDecimal spargeWater = BigDecimal.valueOf(7.0);
    instance.setSpargeWater(spargeWater);

    assertCalculations(instance, BigDecimal.valueOf(20), BigDecimal.valueOf(13), BigDecimal.valueOf(7),
        BigDecimal.valueOf(2.6)
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetSpargeWaterSmallerThanZero() {
    System.out.println("setSpargeWaterSmallerThanZero");
    BigDecimal spargeWater = BigDecimal.valueOf(-2);
    instance.setSpargeWater(spargeWater);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetSpargeWaterLargerThanTotalWater() {
    System.out.println("setSpargeWaterLargerThanTotalWater");
    BigDecimal mashWater = BigDecimal.valueOf(22);
    instance.setSpargeWater(mashWater);
  }

  /**
   * Test of setThickness method, of class MashScheme.
   */
  @Test
  public void testSetThickness() {
    System.out.println("setThickness");
    BigDecimal thickness = BigDecimal.valueOf(2.5);
    instance.setThickness(thickness);

    assertCalculations(instance, BigDecimal.valueOf(20), BigDecimal.valueOf(12.5), BigDecimal.valueOf(7.5), thickness);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetThicknessSmallerZero() {
    System.out.println("setThicknessSmallerThanZero");
    BigDecimal thickness = BigDecimal.valueOf(-2);
    instance.setThickness(thickness);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetThicknessTooThin() {
    System.out.println("setThicknessTooThin");
    BigDecimal thickness = BigDecimal.valueOf(5);
    instance.setThickness(thickness);
  }

  private void assertCalculations(MashScheme instance, BigDecimal totalWater, BigDecimal mashWater,
                                  BigDecimal spargeWater,
                                  BigDecimal thickness) {
    assertEquals(totalWater, instance.getTotalWater());
    assertEquals(mashWater.setScale(2, RoundingMode.HALF_UP), instance.getMashWater().setScale(2, RoundingMode.HALF_UP));
    assertEquals(spargeWater.setScale(2, RoundingMode.HALF_UP), instance.getSpargeWater().setScale(2, RoundingMode.HALF_UP));
    assertEquals(thickness.setScale(2, RoundingMode.HALF_UP), instance.getThickness().setScale(2, RoundingMode.HALF_UP));
  }
}
