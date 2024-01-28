package model;

import java.util.List;

/**
 * IMockModel can keep track of which location got inspected by the strategy.
 */
public interface IMockModel extends IBoard {

  /**
   * return the locations that currently got inspected.
   */
  public List<BoardLocation> getInspectedLocations();
}
