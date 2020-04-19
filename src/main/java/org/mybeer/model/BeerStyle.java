/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mybeer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class BeerStyle {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String beerType;
  private String category;
  private int categoryNumber;
  private String name;
  private String guideName;
  private char styleLetter;
  private BigDecimal minimumOG;
  private BigDecimal maximumOG;
  private BigDecimal minimumFG;
  private BigDecimal maximumFG;
  private BigDecimal minimumAlcoholByVolume;
  private BigDecimal maximumAlcoholByVolume;
  private int minimumBitterness;
  private int maximumBitterness;
  private BigDecimal minimumCarbonation;
  private BigDecimal maximumCarbonation;
  private BigDecimal minimumColour;
  private BigDecimal maximumColour;
  private BigDecimal phMin;
  private BigDecimal phMax;

  public BeerStyle() {

  }

  public String getBeerType() {
    return beerType;
  }

  public void setBeerType(String beerType) {
    this.beerType = beerType;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public int getCategoryNumber() {
    return categoryNumber;
  }

  public void setCategoryNumber(int categoryNumber) {
    this.categoryNumber = categoryNumber;
  }

  public String getGuideName() {
    return guideName;
  }

  public void setGuideName(String guideName) {
    this.guideName = guideName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public char getStyleLetter() {
    return styleLetter;
  }

  public void setStyleLetter(char styleLetter) {
    this.styleLetter = styleLetter;
  }

  public BigDecimal getMinimunOG() {
    return minimumOG;
  }

  public void setMinimumOG(BigDecimal minimumOG) {
    this.minimumOG = minimumOG;
  }

  public BigDecimal getMaximumOG() {
    return maximumOG;
  }

  public void setMaximumOG(BigDecimal maximumOG) {
    this.maximumOG = maximumOG;
  }

  public BigDecimal getMinimumFG() {
    return minimumFG;
  }

  public void setMinimumFG(BigDecimal minimumFG) {
    this.minimumFG = minimumFG;
  }

  public BigDecimal getMaximumFG() {
    return maximumFG;
  }

  public void setMaximumFG(BigDecimal maximumFG) {
    this.maximumFG = maximumFG;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final BeerStyle other = (BeerStyle) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 31 * hash + Objects.hashCode(this.name);
    return hash;
  }

  public void setMinimumAlcoholByVolume(BigDecimal minimumAlcoholByVolume) {
    this.minimumAlcoholByVolume = minimumAlcoholByVolume;
  }

  public BigDecimal getMinimumAlcoholByVolume() {
    return minimumAlcoholByVolume;
  }

  public void setMaximumAlcoholByVolume(BigDecimal maximumAlcoholByVolume) {
    this.maximumAlcoholByVolume = maximumAlcoholByVolume;
  }

  public BigDecimal getMaximumAlcoholByVolume() {
    return maximumAlcoholByVolume;
  }

  public void setMinimumBitterness(int minimumBitterness) {
    this.minimumBitterness = minimumBitterness;
  }

  public int getMinimumBitterness() {
    return minimumBitterness;
  }

  public void setMaximumBitterness(int maximumBitterness) {
    this.maximumBitterness = maximumBitterness;
  }

  public int getMaximumBitterness() {
    return maximumBitterness;
  }

  public void setMinimumCarbonation(BigDecimal minimumCarbonation) {
    this.minimumCarbonation = minimumCarbonation;
  }

  public BigDecimal getMinimumCarbonation() {
    return minimumCarbonation;
  }

  public void setMaximumCarbonation(BigDecimal maximumCarbonation) {
    this.maximumCarbonation = maximumCarbonation;
  }

  public BigDecimal getMaximumCarbonation() {
    return maximumCarbonation;
  }

  public void setMinimumColour(BigDecimal minimumColour) {
    this.minimumColour = minimumColour;
  }

  public BigDecimal getMinimumColour() {
    return minimumColour;
  }

  public void setMaximumColour(BigDecimal maximumColour) {
    this.maximumColour = maximumColour;
  }

  public BigDecimal getMaximumColour() {
    return maximumColour;
  }

  public void setPhMin(BigDecimal phMin) {
    this.phMin = phMin;
  }

  public BigDecimal getPhMin() {
    return phMin;
  }

  public void setPhMax(BigDecimal phMax) {
    this.phMax = phMax;
  }

  public BigDecimal getPhMax() {
    return phMax;
  }
}