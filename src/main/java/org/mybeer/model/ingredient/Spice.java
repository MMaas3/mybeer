package org.mybeer.model.ingredient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Spice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int oldId;
  private String name;
  private String type;
  private BigDecimal gravityAddition;
  private String remark;

  public void setOldId(int oldId) {
    this.oldId = oldId;
  }

  public int getOldId() {
    return oldId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setGravityAddition(BigDecimal gravityAddition) {
    this.gravityAddition = gravityAddition;
  }

  public BigDecimal getGravityAddition() {
    return gravityAddition;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getRemark() {
    return remark;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
