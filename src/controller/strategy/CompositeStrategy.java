package controller.strategy;

import model.BoardLocation;
import model.ReadonlyReversiModel;

/**
 * Implements a composite strategy for the Reversi game AI. This strategy allows combining
 * multiple strategies, using a primary strategy first and resorting to a secondary strategy
 * if the primary one is not applicable or throws an exception.
 */
public class CompositeStrategy implements IStrategy {
  // the first strategy to getPosition
  IStrategy first;

  // if the first strategy throws exception, use the second strategy
  IStrategy rest;

  /**
   * Constructs a CompositeStrategy with a primary (first) strategy and a secondary (rest) strategy.
   * The primary strategy is used first, and if it fails, the secondary strategy is used.
   *
   * @param first The primary strategy to be used initially.
   * @param rest  The secondary strategy to be used if the primary strategy fails.
   */
  public CompositeStrategy(IStrategy first, IStrategy rest) {
    this.first = first;
    this.rest = rest;
  }

  /**
   * Calculates the best move by trying the primary strategy first. If the primary strategy
   * throws an IllegalStateException, it resorts to the secondary strategy.
   *
   * @param model The read-only model of the Reversi game.
   * @return The best move as a BoardLocation, determined by the strategies.
   */
  @Override
  public BoardLocation getPosition(ReadonlyReversiModel model) {
    BoardLocation pos;

    try {
      pos = this.first.getPosition(model);

    } catch (IllegalStateException e) {
      pos = this.rest.getPosition(model);
    }

    if (pos == null) {
      throw new IllegalStateException();
    }
    return pos;
  }
}
