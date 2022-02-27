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
public class Yeast {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  @Enumerated(EnumType.STRING)
  private YeastForm form;
  @Enumerated(EnumType.STRING)
  private YeastType type;
  private int oldId;
  private BigDecimal attenuationMin;
  private BigDecimal attenuationMax;
  private BigDecimal tempMin;
  private BigDecimal tempMax;
  private BigDecimal alcoholToleranceMin;
  private BigDecimal alcoholToleranceMax;
  private BigDecimal doseMin;
  private BigDecimal doseMax;
  @Enumerated(EnumType.STRING)
  private Flocculation flocculation;
  @Type(type = "text")
  private String styles;
  @Type(type = "text")
  private String aromaTaste;
  @Type(type = "text")
  private String remarks;

  public YeastForm getForm() {
    return form;
  }

  public void setForm(YeastForm form) {
    this.form = form;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public YeastType getType() {
    return type;
  }

  public void setType(YeastType type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Yeast other = (Yeast) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 13 * hash + Objects.hashCode(this.name);
    return hash;
  }

  public void setOldId(int oldId) {
    this.oldId = oldId;
  }

  public int getOldId() {
    return oldId;
  }

  public void setAttenuationMin(BigDecimal attenuationMin) {
    this.attenuationMin = attenuationMin;
  }

  public BigDecimal getAttenuationMin() {
    return attenuationMin;
  }

  public void setAttenuationMax(BigDecimal attenuationMax) {
    this.attenuationMax = attenuationMax;
  }

  public BigDecimal getAttenuationMax() {
    return attenuationMax;
  }

  public void setTempMin(BigDecimal tempMin) {
    this.tempMin = tempMin;
  }

  public BigDecimal getTempMin() {
    return tempMin;
  }

  public void setTempMax(BigDecimal tempMax) {
    this.tempMax = tempMax;
  }

  public BigDecimal getTempMax() {
    return tempMax;
  }

  public void setAlcoholToleranceMin(BigDecimal alcoholToleranceMin) {
    this.alcoholToleranceMin = alcoholToleranceMin;
  }

  public BigDecimal getAlcoholToleranceMin() {
    return alcoholToleranceMin;
  }

  public void setAlcoholToleranceMax(BigDecimal alcoholToleranceMax) {
    this.alcoholToleranceMax = alcoholToleranceMax;
  }

  public BigDecimal getAlcoholToleranceMax() {
    return alcoholToleranceMax;
  }

  public void setDoseMin(BigDecimal doseMin) {
    this.doseMin = doseMin;
  }

  public BigDecimal getDoseMin() {
    return doseMin;
  }

  public void setDoseMax(BigDecimal doseMax) {
    this.doseMax = doseMax;
  }

  public BigDecimal getDoseMax() {
    return doseMax;
  }

  public void setFlocculation(Flocculation flocculation) {
    this.flocculation = flocculation;
  }

  public Flocculation getFlocculation() {
    return flocculation;
  }

  public void setStyles(String styles) {
    this.styles = styles;
  }

  public String getStyles() {
    return styles;
  }

  public void setAromaTaste(String aromaTaste) {
    this.aromaTaste = aromaTaste;
  }

  public String getAromaTaste() {
    return aromaTaste;
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
