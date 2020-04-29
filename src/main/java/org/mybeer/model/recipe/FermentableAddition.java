/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mybeer.model.recipe;


import org.mybeer.model.ingredient.Fermentable;

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
public class FermentableAddition {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Fermentable fermentable;

  @Enumerated(EnumType.STRING)
  private AdditionMoment additionMoment;
  private BigDecimal amount;

  public FermentableAddition() {

  }

  public FermentableAddition(Fermentable fermentable,
                             AdditionMoment additionMoment, BigDecimal weight) {
    this.fermentable = fermentable;
    this.additionMoment = additionMoment;
    this.amount = weight;
  }

  public Fermentable getFermentable() {
    return fermentable;
  }

  public void setFermentable(Fermentable fermentable) {
    this.fermentable = fermentable;
  }

  public AdditionMoment getAdditionMoment() {
    return additionMoment;
  }

  public void setAdditionMoment(AdditionMoment additionMoment) {
    this.additionMoment = additionMoment;
  }

  /**
   * Get the amount.
   *
   * @return the amount in grams.
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * Set the amount in grams.
   *
   * @param amount amount in grams.
   */
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final FermentableAddition other = (FermentableAddition) obj;
    if (!Objects.equals(this.fermentable, other.fermentable)) {
      return false;
    }
    return this.additionMoment == other.additionMoment;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 53 * hash + Objects.hashCode(this.fermentable);
    hash = 53 * hash + (this.additionMoment != null ? this.additionMoment.hashCode() : 0);
    return hash;
  }

  private Long getId() {
    return id;
  }

  private void setId(Long id) {
    this.id = id;
  }
}
