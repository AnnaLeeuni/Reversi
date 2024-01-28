package controller.strategy;

import java.util.ArrayList;
import java.util.List;

import model.BoardLocation;
import model.ReadonlyReversiModel;

/**
 * avoid the cells next to corners.
 */
public class AIAvoidNextToCorner extends AStrategy implements IStrategy {


  /**
   * use the default type of mutable board.
   */
  public AIAvoidNextToCorner() {
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
    List<BoardLocation> possibleMoves = new ArrayList<>();
    List<BoardLocation> avoidMoves = new ArrayList<>();
    List<BoardLocation> availableLocation = model.legalMoves();
    // only three lines in the board can have corner: first/mid/last
    // all the corners
    List<BoardLocation> cornerPosition = model.getCorners();

    // get the positions next to those corners
    List<BoardLocation> nearCorner = model.getCornersNearBy();

    // initiate the copyBoard
    resetBoard(model);

    for (BoardLocation nearCornerBL : nearCorner) {
      try {
        copyBoard.placeDisc(nearCornerBL.getRow(), nearCornerBL.getIndex());
        resetBoard(model);
        //System.out.println(nearCorner.size());
        avoidMoves.add(nearCornerBL);
      } catch (IllegalStateException ignored) {
      }
    }

    // filter the avoidMoves in availableLocation
    int index = 0;
    int bound = availableLocation.size();
    while (index < bound) {
      boolean removeElement = false;
      for (BoardLocation bl : avoidMoves) {
        if (index < availableLocation.size()
                && availableLocation.get(index).toString().equals(bl.toString())) {
          availableLocation.remove(index);
          bound--;
          removeElement = true;
        }
      }
      if (!removeElement) {
        index++;
      }
    }

    // if all the available moves are next to a corner
    if (availableLocation.isEmpty()) {
      throw new IllegalStateException("all the moves are next to the corner");
    } else {
      System.out.println(availableLocation);
      return availableLocation;
    }
  }
}