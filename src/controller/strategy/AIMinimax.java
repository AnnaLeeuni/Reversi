package controller.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.BoardLocation;
import model.DiscType;
import model.IBoard;
import model.ReadonlyReversiModel;

/**
 * Implements the Minimax strategy for a Reversi game AI. This strategy aims to minimize the
 * maximum advantage the opponent can gain in their next move. It selects a move by evaluating
 * the opponent's best possible response and chooses the one that leads to the least advantageous
 * position for the opponent.
 * It assumes an opponent's strategy, when opponent's strategy throws an exception, that means:
 * our pick for the location ts very good that whatever strategy opponent is using,
 * we made them powerless, therefore, when find an exception thrown by opponent,
 * play at that location.
 */
public class AIMinimax extends AStrategy implements IStrategy {

  // the strategy opponent is using.
  IStrategy opponentStrategy;


  /**
   * Constructs an AIMinimax with the default opponent strategy set to AICaptureManyPieces.
   */
  public AIMinimax() {
    this.opponentStrategy = new AICaptureManyPieces();
  }

  /**
   * Constructs an AIMinimax with a specified opponent strategy.
   *
   * @param opponentStrategy The strategy that the opponent is using.
   */
  public AIMinimax(IStrategy opponentStrategy) {

    this.opponentStrategy = opponentStrategy;
  }

  @Override
  protected List<BoardLocation> listOfPossibleMove(ReadonlyReversiModel model) {
    // the potential minimaxs
    List<BoardLocation> allPossibleMoves = new ArrayList<>();
    // initiate the copyBoard which is the same as the model but mutable
    resetBoard(model);
    // get the possible moves for current Disc
    List<BoardLocation> possibleMoves = copyBoard.legalMoves();
    Map<BoardLocation, Integer> locationToScore = new HashMap<>();
    int lowestScore = -1; // set an unrealistic value
    for (BoardLocation bl : possibleMoves) {

      //System.out.println(bl);

      copyBoard.placeDisc(bl.getRow(), bl.getIndex());

      try {
        // get the mutable version of the read only model
        IBoard localBoard = model.getMutableVersion();

        DiscType oppoType = localBoard.getTurns();
        BoardLocation oppBest = opponentStrategy.getPosition(localBoard);
        copyBoard.placeDisc(oppBest.getRow(), oppBest.getIndex());

        int localScore = copyBoard.getScore(oppoType);
        if (lowestScore == -1 || lowestScore >= localScore) {
          lowestScore = localScore;
        }
        // get the score of the opponent's best move for each position we place
        locationToScore.put(bl, localScore);
        resetBoard(model);

      } catch (IllegalStateException | NullPointerException e) {
        // the best case is when we placed the location and the opponent got nowhere to place.
        allPossibleMoves.add(bl);
        resetBoard(model);
      }
    }
    for (Map.Entry<BoardLocation, Integer> ignored : locationToScore.entrySet()) {
      if (ignored.getValue() == (lowestScore)) {
        allPossibleMoves.add(ignored.getKey());
      }
    }

    if (allPossibleMoves.isEmpty()) {
      throw new IllegalStateException("no miniMax to place");
    } else {
      //System.out.println(allPossibleMoves);
      return allPossibleMoves;
    }
  }


  /**
   * Calculates the best move to make based on the Minimax strategy. It simulates each possible
   * move, evaluates the opponent's best response, and selects the move that minimizes the
   * opponent's advantage.
   *
   * @param model The read-only model of the Reversi game.
   * @return The best move as a BoardLocation based on the Minimax strategy.
   * @throws IllegalStateException If no optimal move can be determined.
   */
  @Override
  public BoardLocation getPosition(ReadonlyReversiModel model) {
    if (!model.anyLegalMove()) {
      throw new IllegalStateException("no legal moves for the current player");
    }

    System.out.println("USE THIS: " + listOfPossibleMove(model).get(0));
    return listOfPossibleMove(model).get(0);
  }
}
