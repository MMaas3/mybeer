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
import java.util.Objects;

@Entity
public class Hop {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private BigDecimal alphaAcidPercentageMin;
  private BigDecimal alphaAcidPercentageMax;
  private BigDecimal betaAcidPercentageMin;
  private BigDecimal betaAcidPercentageMax;
  private BigDecimal cohumuloneMin;
  private BigDecimal cohumuloneMax;
  private BigDecimal oilMin;
  private BigDecimal oilMax;
  private BigDecimal humuloneMin;
  private BigDecimal humuloneMax;
  private BigDecimal caryophylleneMin;
  private BigDecimal caryophylleneMax;
  private BigDecimal myrceneMin;
  private BigDecimal myrceneMax;
  private BigDecimal farneseneMin;
  private BigDecimal farneseneMax;
  @Enumerated(EnumType.STRING)
  private HopForm form;
  private String name;
  private int oldId;
  private BigDecimal alphaBetaBalance;
  @Type(type = "text")
  private String taste;
  @Type(type = "text")
  private String substitute;


  public HopForm getForm() {
    return form;
  }

  public void setForm(HopForm form) {
    this.form = form;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Hop other = (Hop) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 53 * hash + Objects.hashCode(this.name);
    return hash;
  }

  public BigDecimal getAlphaAcidPercentageMin() {
    return alphaAcidPercentageMin;
  }

  public void setAlphaAcidPercentageMin(BigDecimal alphaAcidPercentageMin) {
    this.alphaAcidPercentageMin = alphaAcidPercentageMin;
  }

  public BigDecimal getAlphaAcidPercentageMax() {
    return alphaAcidPercentageMax;
  }

  public void setAlphaAcidPercentageMax(BigDecimal alphaAcidPercentageMax) {
    this.alphaAcidPercentageMax = alphaAcidPercentageMax;
  }

  public BigDecimal getBetaAcidPercentageMin() {
    return betaAcidPercentageMin;
  }

  public void setBetaAcidPercentageMin(BigDecimal betaAcidPercentageMin) {
    this.betaAcidPercentageMin = betaAcidPercentageMin;
  }

  public BigDecimal getBetaAcidPercentageMax() {
    return betaAcidPercentageMax;
  }

  public void setBetaAcidPercentageMax(BigDecimal betaAcidPercentageMax) {
    this.betaAcidPercentageMax = betaAcidPercentageMax;
  }

  public BigDecimal getCohumuloneMin() {
    return cohumuloneMin;
  }

  public void setCohumuloneMin(BigDecimal cohumuloneMin) {
    this.cohumuloneMin = cohumuloneMin;
  }

  public BigDecimal getCohumuloneMax() {
    return cohumuloneMax;
  }

  public void setCohumuloneMax(BigDecimal cohumuloneMax) {
    this.cohumuloneMax = cohumuloneMax;
  }

  public BigDecimal getOilMin() {
    return oilMin;
  }

  public void setOilMin(BigDecimal oilMin) {
    this.oilMin = oilMin;
  }

  public BigDecimal getOilMax() {
    return oilMax;
  }

  public void setOilMax(BigDecimal oilMax) {
    this.oilMax = oilMax;
  }

  public BigDecimal getHumuloneMin() {
    return humuloneMin;
  }

  public void setHumuloneMin(BigDecimal humuloneMin) {
    this.humuloneMin = humuloneMin;
  }

  public BigDecimal getHumuloneMax() {
    return humuloneMax;
  }

  public void setHumuloneMax(BigDecimal humuloneMax) {
    this.humuloneMax = humuloneMax;
  }

  public BigDecimal getCaryophylleneMin() {
    return caryophylleneMin;
  }

  public void setCaryophylleneMin(BigDecimal caryophylleneMin) {
    this.caryophylleneMin = caryophylleneMin;
  }

  public BigDecimal getCaryophylleneMax() {
    return caryophylleneMax;
  }

  public void setCaryophylleneMax(BigDecimal caryophylleneMax) {
    this.caryophylleneMax = caryophylleneMax;
  }

  public BigDecimal getMyrceneMin() {
    return myrceneMin;
  }

  public void setMyrceneMin(BigDecimal myrceneMin) {
    this.myrceneMin = myrceneMin;
  }

  public BigDecimal getMyrceneMax() {
    return myrceneMax;
  }

  public void setMyrceneMax(BigDecimal myrceneMax) {
    this.myrceneMax = myrceneMax;
  }

  public BigDecimal getFarneseneMin() {
    return farneseneMin;
  }

  public void setFarneseneMin(BigDecimal farneseneMin) {
    this.farneseneMin = farneseneMin;
  }

  public BigDecimal getFarneseneMax() {
    return farneseneMax;
  }

  public void setFarneseneMax(BigDecimal farneseneMax) {
    this.farneseneMax = farneseneMax;
  }

  public void setOldId(int oldId) {

    this.oldId = oldId;
  }

  public int getOldId() {
    return oldId;
  }

  public void setAlphaBetaBalance(BigDecimal alphaBetaBalance) {
    this.alphaBetaBalance = alphaBetaBalance;
  }

  public BigDecimal getAlphaBetaBalance() {
    return alphaBetaBalance;
  }

  public void setTaste(String taste) {
    this.taste = taste;
  }

  public String getTaste() {
    return taste;
  }

  public void setSubstitute(String substitute) {
    this.substitute = substitute;
  }

  public String getSubstitute() {
    return substitute;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
