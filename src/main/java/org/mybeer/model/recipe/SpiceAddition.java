package org.mybeer.model.recipe;


import org.mybeer.model.ingredient.Spice;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class SpiceAddition {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Spice spice;

  @Enumerated(EnumType.STRING)
  private AdditionMoment additionMoment;
  private int contactTime;
  private BigDecimal spiceMass;

  public SpiceAddition() {

  }

  public SpiceAddition(Spice spice, AdditionMoment additionMoment, int contactTime, BigDecimal spiceMass) {

    this.spice = spice;
    this.additionMoment = additionMoment;
    this.contactTime = contactTime;
    this.spiceMass = spiceMass;
  }

  public Spice getSpice() {
    return spice;
  }

  public void setSpice(Spice spice) {
    this.spice = spice;
  }

  public AdditionMoment getAdditionMoment() {
    return additionMoment;
  }

  public void setAdditionMoment(AdditionMoment additionMoment) {
    this.additionMoment = additionMoment;
  }

  public int getContactTime() {
    return contactTime;
  }

  public void setContactTime(int contactTime) {
    this.contactTime = contactTime;
  }

  public BigDecimal getSpiceMass() {
    return spiceMass;
  }

  public void setSpiceMass(BigDecimal spiceMass) {
    this.spiceMass = spiceMass;
  }
}
