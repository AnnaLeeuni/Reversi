import org.junit.Assert;
import org.junit.Test;

import controller.strategy.AIAvoidNextToCorner;
import controller.strategy.AICaptureManyPieces;
import controller.strategy.AIGetCorners;
import controller.strategy.IStrategy;
import model.SquareReversiModel;
import view.SquareTextualView;
import model.BoardLocation;

import controller.strategy.AIMinimax;

/**
 * Tests on the AI strategy for the square model.
 */

public class TestAIStrategySquare {

  // Capture many returns the move of the top left most when all move capture the same amount
  @Test
  public void testAICaptureManyPieces() {
    SquareReversiModel board = new SquareReversiModel();
    board.startGame();
    SquareTextualView viewBoard = new SquareTextualView(board);
    System.out.println(viewBoard);

    IStrategy strategy = new AICaptureManyPieces();
    Assert.assertEquals(2, strategy.getPosition(board).getRow());
    Assert.assertEquals(4, strategy.getPosition(board).getIndex());

  }

  // AI easy should return the same, because AIEasy is basically AICaptureMany
  @Test
  public void testAIEasy() {
    SquareReversiModel board = new SquareReversiModel();
    board.startGame();
    SquareTextualView viewBoard = new SquareTextualView(board);
    System.out.println(viewBoard);

    IStrategy strategy = new AICaptureManyPieces();
    Assert.assertEquals(2, strategy.getPosition(board).getRow());
    Assert.assertEquals(4, strategy.getPosition(board).getIndex());
  }

  // when there are legal moves that can capture different amounts.
  // the strategy will locate the move that capture the most.
  @Test
  public void testAICaptureMany2() {
    SquareReversiModel board = new SquareReversiModel();
    board.startGame();
    SquareTextualView viewBoard = new SquareTextualView(board);
    board.placeDisc(2,4);
    System.out.println(viewBoard);
    board.placeDisc(2,5);
    board.skipRound();
    board.placeDisc(2,3);
    System.out.println(viewBoard);
    IStrategy strategy = new AICaptureManyPieces();

    Assert.assertEquals(3, board.legalMoves().size());
    Assert.assertEquals(1, strategy.getPosition(board).getRow());
    Assert.assertEquals(4, strategy.getPosition(board).getIndex());
  }

  // should return the first available move
  // when no moves are next to the corner
  @Test
  public void testAIAvoidNextToCorner() {
    SquareReversiModel board = new SquareReversiModel(6);
    board.startGame();
    SquareTextualView viewBoard = new SquareTextualView(board);
    System.out.println(viewBoard);

    IStrategy strategy = new AIAvoidNextToCorner();
    Assert.assertEquals(1, strategy.getPosition(board).getRow());
    Assert.assertEquals(3, strategy.getPosition(board).getIndex());
  }

  // the first possible move is next to corner
  // should return the second move
  @Test
  public void testAIAvoidNextToCorner2() {
    SquareReversiModel board = new SquareReversiModel(6);
    board.startGame();
    SquareTextualView viewBoard = new SquareTextualView(board);
    System.out.println(viewBoard);

    IStrategy strategy = new AIAvoidNextToCorner();
    Assert.assertEquals(1, strategy.getPosition(board).getRow());
    Assert.assertEquals(3, strategy.getPosition(board).getIndex());


  }



  // when all moves are next to corners
  @Test
  public void testAIAvoidNextToCorner3() {
    SquareReversiModel board = new SquareReversiModel(4);
    board.startGame();
    SquareTextualView viewBoard = new SquareTextualView(board);
    System.out.println(viewBoard);

    IStrategy strategy = new AIAvoidNextToCorner();
    Assert.assertThrows(IllegalStateException.class, () -> strategy.getPosition(board));
  }

  // should throw exception if there's no corner to capture.
  @Test(expected = IllegalStateException.class)
  public void testAIGetCorners() {
    SquareReversiModel board = new SquareReversiModel(4);
    board.startGame();
    SquareTextualView viewBoard = new SquareTextualView(board);
    System.out.println(viewBoard);
    IStrategy strategy = new AIGetCorners();
    strategy.getPosition(board);
  }

  // if there is a possible move in the corner.
  @Test
  public void testAIGetCorners2() {
    SquareReversiModel board = new SquareReversiModel(4);
    board.startGame();
    SquareTextualView viewBoard = new SquareTextualView(board);
    board.placeDisc(3, 1);
    board.placeDisc(3, 2);
    System.out.println(viewBoard);
    IStrategy strategy = new AIGetCorners();
    Assert.assertEquals(0, strategy.getPosition(board).getRow());
    Assert.assertEquals(3, strategy.getPosition(board).getIndex());
    System.out.println(viewBoard);

  }

  @Test
  public void testAIMiniMax() {
    SquareReversiModel model = new SquareReversiModel(8);
    model.startGame();
    SquareTextualView viewBoard = new SquareTextualView(model);

    IStrategy op = new AIGetCorners();
    IStrategy mini = new AIMinimax(op);
    model.placeDisc(3, 5);
    model.placeDisc(2, 5);
    model.placeDisc(2, 4);
    model.placeDisc(2, 3);
    model.skipRound();
    model.placeDisc(3, 6);
    model.placeDisc(1, 4);
    System.out.println(viewBoard);

    BoardLocation location = mini.getPosition(model);
    Assert.assertEquals(0, location.getRow());
    Assert.assertEquals(3, location.getIndex());
  }



}
