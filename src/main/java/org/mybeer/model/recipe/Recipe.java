/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mybeer.model.recipe;


import org.hibernate.annotations.Type;
import org.mybeer.model.BeerStyle;
import org.mybeer.model.BrewingSystem;
import org.mybeer.model.ingredient.Fermentable;
import org.mybeer.model.mash.MashScheme;
import org.mybeer.model.mash.MashStep;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Recipe {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int boilTime;
  private String name;

  @OneToOne(cascade = CascadeType.ALL)
  private MashScheme mashScheme;
  private BigDecimal volume;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "recipe_id")
  private Set<FermentableAddition> fermentableAdditions;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "recipe_id")
  private Set<HopAddition> hopAdditions;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "recipe_id")
  private Set<YeastAddition> yeastAdditions;
  private int oldId;
  private int efficiency;
  @ManyToOne
  private BeerStyle beerStyle;
  private BigDecimal brewingVolume;

  @Type(type = "text")
  private String description;

  @ManyToOne
  private BrewingSystem system;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
  @JoinColumn(name = "recipe_id")
  private Set<SpiceAddition> spiceAdditions;

  public Recipe() {
    fermentableAdditions = new HashSet<>();
    hopAdditions = new HashSet<>();
    yeastAdditions = new HashSet<>();
    spiceAdditions = new HashSet<>();
    mashScheme = new MashScheme();
  }

  public void addFermentableAddition(FermentableAddition fermentableAddition)
      throws IllegalArgumentException,
      FermentableShouldBeMashedException {
    Fermentable fermentable = fermentableAddition.getFermentable();
    AdditionMoment additionMoment = fermentableAddition.getAdditionMoment();

    if (fermentable == null) {
      throw new IllegalArgumentException("FermentableImpl cannot be null.");
    }

    if (additionMoment == null) {
      throw new IllegalArgumentException("AdditionMoment cannot be null.");
    }

    if (fermentableAddition.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Weight should be larger than zero.");
    }

    if (fermentable.shouldBeMashed()
        && additionMoment != AdditionMoment.MASH) {
      throw new FermentableShouldBeMashedException();
    }

    fermentableAdditions.add(fermentableAddition);
  }

  public void addHopAddition(HopAddition hopAddition)
      throws IllegalArgumentException {
    if (hopAddition.getHop() == null) {
      throw new IllegalArgumentException("HopImpl cannot be null");
    }

    if (hopAddition.getAdditionMoment() == null) {
      throw new IllegalArgumentException("AddtionMoment cannot be null.");
    }

    if (hopAddition.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Amount should be larger than 0.");
    }

    if (hopAddition.getAdditionMoment() == AdditionMoment.BOIL && hopAddition.getContactTime() > boilTime) {
      hopAddition.setContactTime(boilTime);
      throw new IllegalArgumentException("ContactTime should be smaller than or equal to the boil time: " + boilTime);
    }

    this.hopAdditions.add(hopAddition);
  }

  public void addYeastAddition(YeastAddition yeastAddition)
      throws IllegalArgumentException {
    if (yeastAddition.getYeast() == null) {
      throw new IllegalArgumentException("YeastImpl cannot be null");
    }

    if (yeastAddition.getAdditionMoment() == null) {
      throw new IllegalArgumentException("AddtionMoment cannot be null.");
    }

    if (yeastAddition.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Amount should be larger than 0.");
    }

    this.yeastAdditions.add(yeastAddition);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Recipe other = (Recipe) obj;
    if (!Objects.equals(this.id, other.id)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 89 * hash + Objects.hashCode(this.id);
    return hash;
  }

  public int getBoilTime() {
    return boilTime;
  }

  public void setBoilTime(int boilTime) {
    this.boilTime = boilTime;
  }

  public Set<FermentableAddition> getFermentableAdditions() {
    return fermentableAdditions;
  }

  public void setFermentableAdditions(Set<FermentableAddition> fermentableAdditions) {
    this.fermentableAdditions = fermentableAdditions;
  }

  public Set<HopAddition> getHopAdditions() {
    return hopAdditions;
  }

  public void setHopAdditions(Set<HopAddition> hopAdditions) {
    this.hopAdditions = hopAdditions;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public MashScheme getMashScheme() {
    return mashScheme;
  }

  public void setMashScheme(MashScheme mashScheme) {
    this.mashScheme = mashScheme;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get the volume.
   *
   * @return the volume in liters.
   */
  public BigDecimal getVolume() {
    return volume;
  }

  /**
   * Set the volume in liters.
   *
   * @param volume volume in liters.
   */
  public void setVolume(BigDecimal volume) {
    this.volume = volume;
  }

  public Set<YeastAddition> getYeastAdditions() {
    return yeastAdditions;
  }

  public void setYeastAdditions(Set<YeastAddition> yeastAdditions) {
    this.yeastAdditions = yeastAdditions;
  }

  public void setOldId(int oldId) {
    this.oldId = oldId;
  }

  public int getOldId() {
    return oldId;
  }

  public void setEfficiency(int efficiency) {
    this.efficiency = efficiency;
  }

  public int getEfficiency() {
    return efficiency;
  }

  public void setBeerStyle(BeerStyle beerStyle) {
    this.beerStyle = beerStyle;
  }

  public BeerStyle getBeerStyle() {
    return beerStyle;
  }

  public void setBrewingVolume(BigDecimal brewingVolume) {
    this.brewingVolume = brewingVolume;
  }

  public BigDecimal getBrewingVolume() {
    return brewingVolume;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void addMashStep(MashStep mashStep) {
    mashScheme.addMashStep(mashStep);
  }

  public void setSystem(BrewingSystem system) {
    this.system = system;
  }

  public BrewingSystem getSystem() {
    return system;
  }

  public void addSpiceAddition(SpiceAddition spiceAddition) {
    this.spiceAdditions.add(spiceAddition);
  }
}
