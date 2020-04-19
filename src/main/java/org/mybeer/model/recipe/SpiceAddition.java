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

  public SpiceAddition(Spice spice, AdditionMoment additionMoment, int contactTime, BigDecimal spiceMass) {

    this.spice = spice;
    this.additionMoment = additionMoment;
    this.contactTime = contactTime;
    this.spiceMass = spiceMass;
  }

  private Spice getSpice() {
    return spice;
  }

  private void setSpice(Spice spice) {
    this.spice = spice;
  }

  private AdditionMoment getAdditionMoment() {
    return additionMoment;
  }

  private void setAdditionMoment(AdditionMoment additionMoment) {
    this.additionMoment = additionMoment;
  }

  private int getContactTime() {
    return contactTime;
  }

  private void setContactTime(int contactTime) {
    this.contactTime = contactTime;
  }

  private BigDecimal getSpiceMass() {
    return spiceMass;
  }

  private void setSpiceMass(BigDecimal spiceMass) {
    this.spiceMass = spiceMass;
  }
}
