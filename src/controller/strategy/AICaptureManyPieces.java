package controller.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.BoardLocation;
import model.ReadonlyReversiModel;

/**
 * This class implements an AI strategy for capturing as many pieces as possible in a single turn.
 * It evaluates all possible moves and chooses the one that maximizes the number of pieces captured.
 */
public class AICaptureManyPieces extends AStrategy implements IStrategy {

  // store the boardLocation as  key, the score as the value
  private final Map<BoardLocation, Integer> allMoves = new HashMap<>();

  // store the highest of a single disc can capture
  private int highestScore = -1;


  /**
   * use the default mutable board.
   */
  public AICaptureManyPieces() {
    super();
  }

  @Override
  protected List<BoardLocation> listOfPossibleMove(ReadonlyReversiModel model) {
    resetBoard(model);
    List<BoardLocation> availableLocation = model.legalMoves();

    int index = 0;
    for (BoardLocation bl : availableLocation) {
      int score = calculateScoreForMove(bl, model);
      allMoves.put(new BoardLocation(bl.getRow(), bl.getIndex()), score);
      highestScore = Math.max(highestScore, score);
    }

    // convert the map into a list
    List<BoardLocation> possibleMoves = new ArrayList<>();
    for (Map.Entry<BoardLocation, Integer> entry : allMoves.entrySet()) {
      possibleMoves.add(new BoardLocation(entry.getKey().getRow(), entry.getKey().getIndex()));
    }

    return possibleMoves;
  }

  @Override
  public BoardLocation getPosition(ReadonlyReversiModel model) {
    //System.out.println(model.getTurns() + " " + model.legalMoves());
    //System.out.println("this: " + findUpperLeftMost(allMoves, highestScore));
    if (!model.anyLegalMove()) {
      throw new IllegalStateException("no legal moves for the current player");
    }
    listOfPossibleMove(model);
    return findUpperLeftMost(allMoves, highestScore);
  }


  /**
   * Calculates the score for a specific move.
   * The score is based on the number of pieces that would be captured.
   *
   * @param model the read-only model of the Reversi game
   * @return the score of the move
   */
  private int calculateScoreForMove(BoardLocation move, ReadonlyReversiModel model) {
    copyBoard = model.getMutableVersion();
    copyBoard.placeDisc(move.getRow(), move.getIndex());
    int score = copyBoard.getScore(model.getTurns());
    resetBoard(model);
    return score;
  }

  /**
   * Finds the upper-leftmost board location from a list of locations that have the highest score.
   *
   * @param allMoves     a map of board locations to their scores
   * @param highestScore the highest score among all moves
   * @return the upper-leftmost location with the highest score
   */
  private BoardLocation findUpperLeftMost(Map<BoardLocation, Integer> allMoves, int highestScore) {
    BoardLocation upperLeft = null;
    for (Map.Entry<BoardLocation, Integer> entry : allMoves.entrySet()) {
      if (entry.getValue() == highestScore) {
        if (upperLeft == null || entry.getKey().getRow() < upperLeft.getRow()
                || (entry.getKey().getRow() == upperLeft.getRow()
                && entry.getKey().getIndex() < upperLeft.getIndex())) {
          upperLeft = entry.getKey();
        }
      }
    }
    return upperLeft;
  }
}
