package controller.strategy;

import model.BoardLocation;
import model.ReadonlyReversiModel;


/**
 * A class to represent an AI easy strategy.
 * This class is simple and has just one basic strategy.
 */
public class AIEasyStrategy implements IStrategy {
  @Override
  public BoardLocation getPosition(ReadonlyReversiModel model) {
    if (!model.anyLegalMove()) {
      throw new IllegalStateException("no legal moves for the current player");
    }
    return new AICaptureManyPieces().getPosition(model);
  }
}
