package org.mybeer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class BrewingSystem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private BigDecimal mashTunVolume;
  private BigDecimal mashTunHeight;
  private BigDecimal copperVolume;
  private BigDecimal copperHeight;
  private BigDecimal hotLiquorTankVolume;
  private BigDecimal hotLiquorTankHeight;
  private BigDecimal waterLossMalt;
  private BigDecimal evaporationRate;
  private BigDecimal kettleLoss;
  private BigDecimal yeastLoss;
  private BigDecimal restLoss;
  private BigDecimal leafHopLoss;
  private BigDecimal pelletHopLoss;

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setMashTunVolume(BigDecimal mashTunVolume) {
    this.mashTunVolume = mashTunVolume;
  }

  public BigDecimal getMashTunVolume() {
    return mashTunVolume;
  }

  public void setMashTunHeight(BigDecimal mashTunHeight) {
    this.mashTunHeight = mashTunHeight;
  }

  public BigDecimal getMashTunHeight() {
    return mashTunHeight;
  }

  public void setCopperVolume(BigDecimal copperVolume) {
    this.copperVolume = copperVolume;
  }

  public BigDecimal getCopperVolume() {
    return copperVolume;
  }

  public void setCopperHeight(BigDecimal copperHeight) {
    this.copperHeight = copperHeight;
  }

  public BigDecimal getCopperHeight() {
    return copperHeight;
  }

  public void setHotLiquorTankVolume(BigDecimal hotLiquorTankVolume) {
    this.hotLiquorTankVolume = hotLiquorTankVolume;
  }

  public BigDecimal getHotLiquorTankVolume() {
    return hotLiquorTankVolume;
  }

  public void setHotLiquorTankHeight(BigDecimal hotLiquorTankHeight) {
    this.hotLiquorTankHeight = hotLiquorTankHeight;
  }

  public BigDecimal getHotLiquorTankHeight() {
    return hotLiquorTankHeight;
  }

  /**
   * @param waterLossMalt in L/Kg
   */
  public void setWaterLossMalt(BigDecimal waterLossMalt) {
    this.waterLossMalt = waterLossMalt;
  }

  /**
   * @return waterLossMalt in L/Kg
   */
  public BigDecimal getWaterLossMalt() {
    return waterLossMalt;
  }

  /**
   * @param evaporationRate in L/h
   */
  public void setEvaporationRate(BigDecimal evaporationRate) {
    this.evaporationRate = evaporationRate;
  }

  /**
   * @return evaporationRate in L/h
   */
  private BigDecimal getEvaporationRate() {
    return evaporationRate;
  }

  /**
   * @param kettleLoss in L
   */
  public void setKettleLoss(BigDecimal kettleLoss) {
    this.kettleLoss = kettleLoss;
  }

  public BigDecimal getKettleLoss() {
    return kettleLoss;
  }

  /**
   * @param yeastLoss in L
   */
  public void setYeastLoss(BigDecimal yeastLoss) {
    this.yeastLoss = yeastLoss;
  }

  public BigDecimal getYeastLoss() {
    return yeastLoss;
  }

  /**
   * @param restLoss in L
   */
  public void setRestLoss(BigDecimal restLoss) {
    this.restLoss = restLoss;
  }

  public BigDecimal getRestLoss() {
    return restLoss;
  }

  /**
   * @param leafHopLoss L/g
   */
  public void setLeafHopLoss(BigDecimal leafHopLoss) {
    this.leafHopLoss = leafHopLoss;
  }

  /**
   * @return leafHopLoss L/g
   */
  public BigDecimal getLeafHopLoss() {
    return leafHopLoss;
  }

  /**
   * @param pelletHopLoss L/g
   */
  public void setPelletHopLoss(BigDecimal pelletHopLoss) {
    this.pelletHopLoss = pelletHopLoss;
  }

  /**
   * @return pelletHopLoss L/g
   */
  public BigDecimal getPelletHopLoss() {
    return pelletHopLoss;
  }

  private Long getId() {
    return id;
  }

  private void setId(Long id) {
    this.id = id;
  }
}
