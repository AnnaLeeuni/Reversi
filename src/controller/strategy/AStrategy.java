package controller.strategy;

import java.util.List;

import model.BoardLocation;
import model.IBoard;
import model.ReadonlyReversiModel;

/**
 * provide util methods for strategy classes.
 */
public abstract class AStrategy implements IStrategy {
  // the board that copies all the information from the given model
  // used for simulation purposes
  protected IBoard copyBoard;

  // copy the given readOnlyBoard as a mutable board

  /**
   * generate a list of possible moves, ranked from upper left to the lower right.
   */
  protected abstract List<BoardLocation> listOfPossibleMove(ReadonlyReversiModel model);

  /**
   * Resets the board to the state of the given model.
   * This is used for simulating moves without affecting the actual game state.
   *
   * @param model the read-only model of the Reversi game
   */
  protected void resetBoard(ReadonlyReversiModel model) {
    copyBoard = model.getMutableVersion();
  }
}
