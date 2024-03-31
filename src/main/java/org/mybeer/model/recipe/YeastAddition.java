/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mybeer.model.recipe;

import org.mybeer.model.ingredient.Yeast;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class YeastAddition {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private AdditionMoment additionMoment;
  private BigDecimal amount;

  @OneToOne(cascade = CascadeType.ALL)
  private Yeast yeast;

  @Enumerated(EnumType.STRING)
  private YeastAdditionType yeastAdditionType;
  private boolean amountWeight;

  public YeastAddition() {

  }

  public YeastAddition(AdditionMoment additionMoment, BigDecimal amount, Yeast yeast, YeastAdditionType yeastAdditionType, boolean amountWeight) {
    this.additionMoment = additionMoment;
    this.amount = amount;
    this.yeast = yeast;
    this.yeastAdditionType = yeastAdditionType;
    this.amountWeight = amountWeight;
  }

  public AdditionMoment getAdditionMoment() {
    return additionMoment;
  }

  public void setAdditionMoment(AdditionMoment additionMoment) {
    this.additionMoment = additionMoment;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public boolean isAmountWeight() {
    return amountWeight;
  }

  public void setAmountWeight(boolean amountWeight) {
    this.amountWeight = amountWeight;
  }

  public Yeast getYeast() {
    return yeast;
  }

  public void setYeast(Yeast yeast) {
    this.yeast = yeast;
  }

  public YeastAdditionType getYeastAdditionType() {
    return yeastAdditionType;
  }

  public void setYeastAdditionType(YeastAdditionType yeastAdditionType) {
    this.yeastAdditionType = yeastAdditionType;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final YeastAddition other = (YeastAddition) obj;
    if (this.additionMoment != other.additionMoment) {
      return false;
    }
    if (!Objects.equals(this.yeast, other.yeast)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + (this.additionMoment != null ? this.additionMoment.hashCode() : 0);
    hash = 97 * hash + Objects.hashCode(this.yeast);
    return hash;
  }
}
