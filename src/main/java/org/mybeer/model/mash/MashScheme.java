/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mybeer.model.mash;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MashScheme {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private BigDecimal grainTemperature;
  private BigDecimal maltWeight;
  @OneToMany(cascade = CascadeType.ALL, targetEntity = MashStep.class, mappedBy = "mashScheme")
  private List<MashStep> mashSteps;
  private BigDecimal mashWater;
  private String name;
  private BigDecimal spargeWater;
  private BigDecimal thickness;
  private BigDecimal totalWater;

  public MashScheme() {
    mashSteps = new ArrayList<>();
  }

  public MashScheme(BigDecimal totalWater, BigDecimal maltWeight) {
    this();
    this.maltWeight = maltWeight;
    this.totalWater = totalWater;
  }

  public BigDecimal getGrainTemperature() {
    return grainTemperature;
  }

  public void setGrainTemperature(BigDecimal grainTemperature) {
    this.grainTemperature = grainTemperature;
  }

  public BigDecimal getMaltWeight() {
    return maltWeight;
  }

  public void addMashStep(MashStep mashStep) {
    mashStep.setMashScheme(this);
    mashSteps.add(mashStep);
  }

  public List<MashStep> getMashSteps() {
    return mashSteps;
  }

  public void setMashSteps(List<MashStep> mashSteps) {
    this.mashSteps = mashSteps;
  }

  public BigDecimal getMashWater() {
    return mashWater;
  }

  public void setMashWater(BigDecimal mashWater)
      throws IllegalArgumentException {
    if (mashWater.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Mash water should be a positive BigDecimal value");
    } else if (mashWater.compareTo(totalWater) > 0) {
      throw new IllegalArgumentException("Mash water should be smaller than the total water amount.");
    }

    this.mashWater = mashWater;
    this.spargeWater = totalWater.subtract(mashWater);
    this.thickness = mashWater.divide(this.maltWeight, MathContext.DECIMAL32);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getSpargeWater() {
    return spargeWater;
  }

  public void setSpargeWater(BigDecimal spargeWater) {
    if (spargeWater.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Sparge water should be a positive BigDecimal value");
    } else if (spargeWater.compareTo(totalWater) > 0) {
      throw new IllegalArgumentException("Sparge water should be smaller than the total water amount.");
    }

    this.spargeWater = spargeWater;
    this.mashWater = totalWater.subtract(spargeWater);
    this.thickness = mashWater.divide(this.maltWeight, MathContext.DECIMAL32);
  }

  public BigDecimal getThickness() {
    return thickness;
  }

  public void setThickness(BigDecimal thickness) {
    if (thickness.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Thickness should be a positive BigDecimal value");
    } else if ((thickness.multiply(maltWeight)).compareTo(totalWater) > 0) {
      throw new IllegalArgumentException("There is not enough water for this thickness");
    }

    this.thickness = thickness;
    this.mashWater = thickness.multiply(this.maltWeight);
    this.spargeWater = totalWater.subtract(mashWater);
  }

  public BigDecimal getTotalWater() {
    return totalWater;
  }
}
