package org.mybeer.model;

import org.mybeer.model.recipe.Recipe;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class BrewDay {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Date date;
  @ManyToOne
  private Recipe recipe;

  public void setDate(Date date) {
    this.date = date;
  }

  public Date getDate() {
    return date;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setRecipe(Recipe recipe) {
    this.recipe = recipe;
  }

  public Recipe getRecipe() {
    return recipe;
  }
}
