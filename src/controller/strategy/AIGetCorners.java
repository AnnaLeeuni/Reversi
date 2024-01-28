package controller.strategy;

import java.util.ArrayList;
import java.util.List;

import model.BoardLocation;
import model.ReadonlyReversiModel;

/**
 * This class implements a strategy for a Reversi game AI that prioritizes capture corner positions.
 * Capturing corners in Reversi is typically advantageous as corner pieces cannot be flipped.
 */
public class AIGetCorners extends AIAvoidNextToCorner implements IStrategy {

  /**
   * use the default type.
   */
  public AIGetCorners() {
    super();
  }

  @Override
  public BoardLocation getPosition(ReadonlyReversiModel model) {
    if (!model.anyLegalMove()) {
      throw new IllegalStateException("no legal moves for the current player");
    }
    return listOfPossibleMove(model).get(0);
  }

  @Override
  protected List<BoardLocation> listOfPossibleMove(ReadonlyReversiModel model) {
    List<BoardLocation> availableLocation = model.legalMoves();
    // all possible corners could capture.
    List<BoardLocation> possibleMoves = new ArrayList<>();

    List<BoardLocation> cornerPosition = model.getCorners();

    resetBoard(model);

    for (BoardLocation blCorner : cornerPosition) {
      try {
        copyBoard.placeDisc(blCorner.getRow(), blCorner.getIndex());
        resetBoard(model);
        possibleMoves.add(blCorner);
      } catch (IllegalStateException ignored) {
      }
    }

    // if there is no possible move in the corner.
    if (possibleMoves.isEmpty()) {
      throw new IllegalStateException("no corners to place");
    } else {
      return possibleMoves;
    }
  }
}
