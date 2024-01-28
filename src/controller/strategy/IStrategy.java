package controller.strategy;

import model.BoardLocation;
import model.ReadonlyReversiModel;

/**
 * Represents a strategy for making a move in a Reversi game.
 * This interface defines a method for determining a move based on a given game state.
 */
public interface IStrategy {

  /**
   * Determines a position to make a move based on the strategy implemented.
   * The method analyzes the current game state provided by the model and returns the most
   * appropriate move as a BoardLocation. If the strategy cannot determine a valid move,
   * it may throw exception.
   *
   * @param model A readonly model representing the current state of the game.
   * @return A BoardLocation indicating the position to place a disc, or throw exception,
   *         if the strategy unable to find a valid move under that strategy.
   *
   */
  public BoardLocation getPosition(ReadonlyReversiModel model);


}
