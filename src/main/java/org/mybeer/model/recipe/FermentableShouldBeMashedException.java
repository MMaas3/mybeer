/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mybeer.model.recipe;

public class FermentableShouldBeMashedException
    extends IllegalArgumentException {

  private final static String message = "Fermentable should be added with add the mash moment.";

  public FermentableShouldBeMashedException() {
    super(message);
  }
}
