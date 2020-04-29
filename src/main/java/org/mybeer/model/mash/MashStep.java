package org.mybeer.model.mash;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class MashStep {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private int number;
  private BigDecimal temperature;
  private int time;
  private BigDecimal thickness;

  @ManyToOne(optional = false)
  private MashScheme mashScheme;

  public MashStep() {

  }

  public MashStep(int number) {
    this.number = number;
  }

  public BigDecimal getTemperature() {
    return temperature;
  }

  /**
   * Set the temperature in degrees Centigrade.
   *
   * @param temperature
   */
  public void setTemperature(BigDecimal temperature)
      throws IllegalArgumentException {
    if (temperature.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Temperature should bigger then zero.");
    }

    this.temperature = temperature;
  }

  public int getTime() {
    return time;
  }

  /**
   * Set the mash step time in minutes.
   *
   * @param time
   */
  public void setTime(int time) {
    // if (time < 0) {
    //   throw new IllegalArgumentException("Time should bigger then zero.");
    // }

    this.time = time;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MashStep mashStep = (MashStep) o;
    return number == mashStep.number &&
        time == mashStep.time &&
        Objects.equals(temperature, mashStep.temperature) &&
        Objects.equals(thickness, mashStep.thickness);
  }

  @Override
  public int hashCode() {
    return Objects.hash(number, temperature, time, thickness);
  }

  public void setThickness(BigDecimal thickness) {
    this.thickness = thickness;
  }

  public BigDecimal getThickness() {
    return thickness;
  }

  int getNumber() {
    return number;
  }

  private long getId() {
    return id;
  }

  private void setId(long id) {
    this.id = id;
  }

  private MashScheme getMashScheme() {
    return mashScheme;
  }

  void setMashScheme(MashScheme mashScheme) {
    this.mashScheme = mashScheme;
  }
}
