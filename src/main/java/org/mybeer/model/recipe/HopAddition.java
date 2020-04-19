/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mybeer.model.recipe;

import org.mybeer.model.ingredient.Hop;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class HopAddition {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private AdditionMoment additionMoment;
  private int contactTime;

  @ManyToOne
  private Hop hop;
  private BigDecimal weight;

  public HopAddition() {
  }

  public HopAddition(AdditionMoment additionMoment, int contactTime, Hop hop, BigDecimal weight) {
    this.additionMoment = additionMoment;
    this.contactTime = contactTime;
    this.hop = hop;
    this.weight = weight;
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

  public Hop getHop() {
    return hop;
  }

  public void setHop(Hop hop) {
    this.hop = hop;
  }

  public BigDecimal getAmount() {
    return weight;
  }

  public void setWeight(BigDecimal weight) {
    this.weight = weight;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final HopAddition other = (HopAddition) obj;
    if (this.additionMoment != other.additionMoment) {
      return false;
    }
    if (this.contactTime != other.contactTime) {
      return false;
    }
    if (!Objects.equals(this.hop, other.hop)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 73 * hash + (this.additionMoment != null ? this.additionMoment.hashCode() : 0);
    hash = 73 * hash + this.contactTime;
    hash = 73 * hash + Objects.hashCode(this.hop);
    return hash;
  }

  private Long getId() {
    return id;
  }

  private void setId(Long id) {
    this.id = id;
  }
}
