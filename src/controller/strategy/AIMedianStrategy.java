package controller.strategy;

import model.BoardLocation;
import model.ReadonlyReversiModel;

/**
 * A Median AI strategy which have some of the smarter strategy.
 */
public class AIMedianStrategy implements IStrategy {
  @Override
  public BoardLocation getPosition(ReadonlyReversiModel model) {
    if (!model.anyLegalMove()) {
      throw new IllegalStateException("no legal moves for the current player");
    }

    return new CompositeStrategy(new AIGetCorners(),
            new CompositeStrategy(new AIAvoidNextToCorner(),
                    new AIEasyStrategy())).getPosition(model);
    // return new CompositeStrategy(new AIPassStrategy(), new AIEasyStrategy()) .getPosition(model);
  }
}
