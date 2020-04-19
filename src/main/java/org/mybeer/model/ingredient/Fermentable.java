/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mybeer.model.ingredient;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

import static java.math.BigDecimal.ONE;

@Entity
public class Fermentable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private static final BigDecimal MAXIMUM_EXTRACT = BigDecimal.valueOf(1.046);
  private BigDecimal colour;
  @Enumerated(EnumType.STRING)
  private FermentableType fermentableType;
  private BigDecimal moisture;
  private String name;
  private BigDecimal potentialExtract;
  private BigDecimal yield;
  private int oldId;
  private BigDecimal maximumPercentage;
  private BigDecimal protein;
  @Type(type = "text")
  private String usage;
  @Type(type = "text")
  private String remarks;

  /**
   * Get the colour.
   *
   * @return the color in EBC.
   */
  public BigDecimal getColour() {
    return colour;
  }

  /**
   * Set the colour.
   *
   * @param colour the color in EBC.
   */
  public void setColour(BigDecimal colour) {
    this.colour = colour;
  }

  public FermentableType getFermentableType() {
    return fermentableType;
  }

  public void setFermentableType(FermentableType fermentableType) {
    this.fermentableType = fermentableType;
  }

  public BigDecimal getMoisture() {
    return moisture;
  }

  public void setMoisture(BigDecimal moisture) {
    this.moisture = moisture;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getPotentialExtract() {
    return potentialExtract;
  }

  public void setPotentialExtract(BigDecimal potentialExtract) {
    this.potentialExtract = potentialExtract;
    this.yield = potentialExtract.subtract(ONE).divide(MAXIMUM_EXTRACT.subtract(ONE), MathContext.DECIMAL32);
  }

  public BigDecimal getYield() {
    return yield;
  }

  public void setYield(BigDecimal yield) {
    this.yield = yield;
    potentialExtract = yield.multiply(MAXIMUM_EXTRACT.subtract(ONE)).add(ONE);
  }

  public boolean shouldBeMashed() {
    return fermentableType == FermentableType.GRAIN || fermentableType == FermentableType.ADJUNCT;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Fermentable other = (Fermentable) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 23 * hash + Objects.hashCode(this.name);
    return hash;
  }

  public void setOldId(int oldId) {
    this.oldId = oldId;
  }

  public int getOldId() {
    return oldId;
  }

  public void setMaximumPercentage(BigDecimal maximumPercentage) {
    this.maximumPercentage = maximumPercentage;
  }

  public BigDecimal getMaximumPercentage() {
    return maximumPercentage;
  }

  public void setProtein(BigDecimal protein) {
    this.protein = protein;
  }

  public BigDecimal getProtein() {
    return protein;
  }

  public void setUsage(String usage) {
    this.usage = usage;
  }

  public String getUsage() {
    return usage;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public String getRemarks() {
    return remarks;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
