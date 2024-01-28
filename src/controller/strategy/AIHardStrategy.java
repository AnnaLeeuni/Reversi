package controller.strategy;

import model.BoardLocation;
import model.ReadonlyReversiModel;

/**
 * A hard AI strategy,  which composite all the strategy we have so far.
 * The smarter AI we have for now.
 */
public class AIHardStrategy implements IStrategy {
  @Override
  public BoardLocation getPosition(ReadonlyReversiModel model) {
    if (!model.anyLegalMove()) {
      throw new IllegalStateException("no legal moves for the current player");
    }
    return new CompositeStrategy(new AIMinimax(), new AIMedianStrategy()).getPosition(model);
  }
}
