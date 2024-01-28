import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import controller.strategy.AIAvoidNextToCorner;
import controller.strategy.AICaptureManyPieces;
import controller.strategy.AIEasyStrategy;
import controller.strategy.AIGetCorners;
import controller.strategy.AIHardStrategy;
import controller.strategy.AIMedianStrategy;
import controller.strategy.AIMinimax;
import controller.strategy.CompositeStrategy;
import controller.strategy.IStrategy;
import model.BasicBoard;
import model.BoardLocation;
import model.DiscType;
import model.ReadonlyReversiModel;
import view.textualview.TextualViewBoard;

/**
 * class test to see if the strategy are working correctly.
 */
public class TestAIStrategyHex {

  // test case when all the possible move only flip one disc
  @Test
  public void testAICaptureManyPieces() {
    ReadonlyReversiModel board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    System.out.println(viewBoard);

    IStrategy strategy = new AICaptureManyPieces();
    Assert.assertEquals(3, strategy.getPosition(board).getRow());
    Assert.assertEquals(4, strategy.getPosition(board).getIndex());

  }

  // should return the same for AIEasy
  @Test
  public void testAIEasy() {
    ReadonlyReversiModel board = new BasicBoard();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    System.out.println(viewBoard);

    IStrategy strategy = new AIEasyStrategy();
    Assert.assertEquals(3, strategy.getPosition(board).getRow());
    Assert.assertEquals(4, strategy.getPosition(board).getIndex());

  }

  // test case when the one location can flip two discs
  @Test
  public void testAICaptureManyPieces2() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    board.placeDisc(3, 4);
    System.out.println(viewBoard);

    IStrategy strategy = new AICaptureManyPieces();
    System.out.println(board.getTurns());

    Assert.assertEquals(4, board.legalMoves().size());
    Assert.assertEquals(2, strategy.getPosition(board).getRow());
    Assert.assertEquals(3, strategy.getPosition(board).getIndex());
  }

  // test when there are many position that can flip same amount of discs
  // should choose the one on the upper left.
  @Test
  public void testAICaptureManyPieces3() {
    BasicBoard board = new BasicBoard();
    TextualViewBoard viewBoard = new TextualViewBoard(board);

    System.out.println(viewBoard);

    IStrategy strategy = new AICaptureManyPieces();
    Assert.assertEquals(6, board.legalMoves().size());
    Assert.assertEquals(3, strategy.getPosition(board).getRow());
    Assert.assertEquals(4, strategy.getPosition(board).getIndex());
  }

  // should return the first available move
  // when no moves are next to the corner
  @Test
  public void testAIAvoidNextToCorner() {
    BasicBoard board = new BasicBoard();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    System.out.println(viewBoard);

    IStrategy strategy = new AIAvoidNextToCorner();
    Assert.assertEquals(3, strategy.getPosition(board).getRow());
    Assert.assertEquals(4, strategy.getPosition(board).getIndex());
  }

  // the first possible move is next to corner
  // should return the second move
  @Test
  public void testAIAvoidNextToCorner2() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    board.placeDisc(4, 6);
    board.placeDisc(4, 7);
    board.placeDisc(3, 4);
    board.placeDisc(4, 3);
    board.placeDisc(3, 6);
    board.placeDisc(2, 4);
    board.placeDisc(2, 3);
    board.skipRound();
    board.placeDisc(1, 4);
    System.out.println(viewBoard);
    Assert.assertEquals("x = 0, y = 4", board.legalMoves().get(0).toString());

    IStrategy strategy = new AIAvoidNextToCorner();
    Assert.assertEquals(2, strategy.getPosition(board).getRow());
    Assert.assertEquals(5, strategy.getPosition(board).getIndex());
  }

  // test when all moves are next to corner.
  // throw exception
  @Test(expected = IllegalStateException.class)
  public void testAIAvoidNextToCorner3() {
    BasicBoard board = new BasicBoard(5);
    board.startGame();
    List<List<DiscType>> piles = board.getPile();
    List<DiscType> firstLine = piles.get(0);
    firstLine.set(2, DiscType.BLACK);
    List<DiscType> secondLine = piles.get(1);
    secondLine.set(1, DiscType.BLACK);
    secondLine.set(3, DiscType.EMPTY);
    List<DiscType> thirdLine = piles.get(2);
    thirdLine.set(0, DiscType.BLACK);
    thirdLine.set(2, DiscType.BLACK);
    thirdLine.set(3, DiscType.EMPTY);
    List<DiscType> fourthLine = piles.get(3);
    fourthLine.set(2, DiscType.EMPTY);
    List<DiscType> fifthLine = piles.get(4);
    fifthLine.set(1, DiscType.BLACK);
    BasicBoard boardNew = new BasicBoard(piles);
    boardNew.startGame();
    TextualViewBoard viewBoardNew = new TextualViewBoard(boardNew);
    System.out.println(viewBoardNew);
    boardNew.skipRound();

    IStrategy strategy = new AIAvoidNextToCorner();
    //  x _ _
    // x o _ _
    //x x x _ _
    // _ o _ _
    //  _ x _
    // when it's white's turn, avoid moving into (3, 0), which is next to a corner.
    strategy.getPosition(boardNew);
  }

  // should throw exception if there's no corner to capture.
  @Test(expected = IllegalStateException.class)
  public void testAIGetCorners() {
    BasicBoard board = new BasicBoard();
    IStrategy strategy = new AIGetCorners();
    strategy.getPosition(board);
  }

  // if there is a possible move in the corner.
  @Test
  public void testAIGetCorners2() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    board.placeDisc(4, 6);
    board.placeDisc(4, 7);
    board.placeDisc(3, 4);
    board.placeDisc(4, 3);
    board.placeDisc(3, 6);
    board.placeDisc(2, 4);
    board.placeDisc(2, 3);
    board.skipRound();
    board.placeDisc(1, 4);
    board.placeDisc(0, 4);
    board.placeDisc(2, 5);
    board.placeDisc(6, 6);
    board.placeDisc(7, 6);
    board.placeDisc(6, 7);
    board.placeDisc(0, 3);
    board.placeDisc(0, 2);
    board.placeDisc(7, 4);
    board.placeDisc(8, 4);
    board.placeDisc(5, 8);
    board.placeDisc(4, 8);
    board.placeDisc(6, 3);
    board.placeDisc(7, 2);
    board.placeDisc(3, 8);
    board.placeDisc(4, 9);
    board.placeDisc(5, 10);
    board.placeDisc(6, 8);
    board.placeDisc(7, 8);
    board.placeDisc(8, 5);
    board.placeDisc(8, 6);
    board.placeDisc(8, 7);
    board.placeDisc(9, 6);
    board.placeDisc(2, 2);
    board.placeDisc(2, 1);
    board.placeDisc(6, 9);
    board.placeDisc(6, 2);
    board.placeDisc(5, 2);
    board.placeDisc(6, 1);
    board.placeDisc(6, 0);
    board.placeDisc(8, 1);
    board.placeDisc(9, 0);
    board.placeDisc(9, 4);
    board.placeDisc(10, 4);
    board.placeDisc(8, 3);
    board.placeDisc(9, 2);
    board.placeDisc(10, 1);
    board.placeDisc(2, 0);
    board.placeDisc(1, 2);
    board.placeDisc(0, 1);
    board.placeDisc(4, 2);
    board.placeDisc(4, 1);
    board.placeDisc(3, 2);
    board.placeDisc(10, 5);
    board.placeDisc(10, 3);
    board.placeDisc(8, 2);
    board.placeDisc(3, 0);
    board.placeDisc(4, 0);
    board.placeDisc(7, 0);
    board.placeDisc(8, 0);
    board.placeDisc(1, 0);
    // board.placeDisc(0, 0);
    System.out.println(viewBoard);
    IStrategy strategy = new AIGetCorners();
    Assert.assertEquals(0, strategy.getPosition(board).getRow());
    Assert.assertEquals(0, strategy.getPosition(board).getIndex());

  }

  @Test
  public void testMiniMax() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    board.placeDisc(4, 6);
    board.placeDisc(4, 7);
    board.placeDisc(3, 4);
    board.placeDisc(4, 3);
    board.placeDisc(3, 6);
    board.placeDisc(2, 4);
    board.placeDisc(2, 3);
    board.skipRound();
    board.placeDisc(1, 4);
    board.placeDisc(0, 4);
    board.placeDisc(2, 5);
    board.placeDisc(6, 6);
    board.placeDisc(7, 6);
    board.placeDisc(6, 7);
    board.placeDisc(0, 3);
    board.placeDisc(0, 2);
    board.placeDisc(7, 4);
    board.placeDisc(8, 4);
    board.placeDisc(5, 8);
    board.placeDisc(4, 8);
    board.placeDisc(6, 3);
    board.placeDisc(7, 2);
    board.placeDisc(3, 8);
    board.placeDisc(4, 9);
    board.placeDisc(5, 10);
    board.placeDisc(6, 8);
    board.placeDisc(7, 8);
    board.placeDisc(8, 5);
    board.placeDisc(8, 6);
    board.placeDisc(8, 7);
    board.placeDisc(9, 6);
    board.placeDisc(2, 2);
    board.placeDisc(2, 1);
    board.placeDisc(6, 9);
    board.placeDisc(6, 2);
    board.placeDisc(5, 2);
    board.placeDisc(6, 1);
    board.placeDisc(6, 0);
    board.placeDisc(8, 1);
    board.placeDisc(9, 0);
    board.placeDisc(9, 4);
    board.placeDisc(10, 4);
    board.placeDisc(8, 3);
    board.placeDisc(9, 2);
    board.placeDisc(10, 1);
    board.placeDisc(2, 0);
    board.placeDisc(1, 2);
    board.placeDisc(0, 1);
    board.placeDisc(4, 2);
    board.placeDisc(4, 1);
    board.placeDisc(3, 2);
    board.placeDisc(10, 5);
    board.placeDisc(10, 3);
    board.placeDisc(8, 2);
    board.placeDisc(3, 0);
    board.placeDisc(4, 0);
    board.placeDisc(7, 0);
    board.placeDisc(8, 0);
    //board.placeDisc(1, 0);
    IStrategy op = new AIGetCorners();
    IStrategy mini = new AIMinimax(op);
    System.out.println(viewBoard);

    BoardLocation location = mini.getPosition(board);
    Assert.assertEquals(1, location.getRow());
    Assert.assertEquals(0, location.getIndex());

    // README: use basic board to extend readonly, even it's basicboard,
    // you cannot use mutable method on readonly mold, will throw exception.
  }

  // combine getCorners and avoidNextToCorner
  @Test
  public void testCompositeStrategy() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    board.placeDisc(4, 6);
    board.placeDisc(4, 7);
    board.placeDisc(3, 4);
    board.placeDisc(4, 3);
    board.placeDisc(3, 6);
    board.placeDisc(2, 4);
    board.placeDisc(2, 3);
    board.skipRound();
    board.placeDisc(1, 4);
    System.out.println(viewBoard);
    Assert.assertEquals("x = 0, y = 4", board.legalMoves().get(0).toString());

    IStrategy strategy = new CompositeStrategy(new AIGetCorners(), new AIAvoidNextToCorner());
    Assert.assertEquals(2, strategy.getPosition(board).getRow());
    Assert.assertEquals(5, strategy.getPosition(board).getIndex());
  }

  @Test
  public void testAIGetCornerWorkingWithRiggedPileCrossCompare() {
    BasicBoard board = new BasicBoard(5);
    board.startGame();
    List<List<DiscType>> piles = board.getPile();
    List<DiscType> firstLine = piles.get(0);
    firstLine.set(3, DiscType.WHITE);
    List<DiscType> secondLine = piles.get(1);
    secondLine.set(1, DiscType.BLACK);
    secondLine.set(2, DiscType.WHITE);
    secondLine.set(3, DiscType.WHITE);
    List<DiscType> thirdLine = piles.get(2);
    thirdLine.set(1, DiscType.WHITE);
    thirdLine.set(2, DiscType.BLACK);
    thirdLine.set(3, DiscType.BLACK);
    List<DiscType> fourthLine = piles.get(3);
    fourthLine.set(1, DiscType.EMPTY);
    fourthLine.set(2, DiscType.BLACK);
    BasicBoard boardNew = new BasicBoard(piles, DiscType.WHITE);
    boardNew.startGame();
    TextualViewBoard viewBoardNew = new TextualViewBoard(boardNew);
    System.out.println(viewBoardNew);
    // customized a board like this
    //  _ o _
    // x o o _
    //_ o x x _
    // _ _ x _
    //  _ _ _
    IStrategy getCorner = new AIGetCorners();
    boardNew.placeDisc(getCorner.getPosition(boardNew).getRow(),
            getCorner.getPosition(boardNew).getIndex());
    Assert.assertEquals("x = 0, y = 0", getCorner.getPosition(boardNew).toString());
    System.out.println(viewBoardNew);
    //  _ o _
    // x o o _
    //_ o o o o
    // _ _ x _
    //  _ _ _
    //and through test, our getCorner did find us the correct best move.
  }

  @Test
  public void testAIMinimaxWorkingWithRiggedPileCrossCompare() {
    BasicBoard board = new BasicBoard(5);
    board.startGame();
    List<List<DiscType>> piles = board.getPile();
    List<DiscType> firstLine = piles.get(0);
    firstLine.set(3, DiscType.WHITE);
    List<DiscType> secondLine = piles.get(1);
    secondLine.set(1, DiscType.BLACK);
    secondLine.set(2, DiscType.WHITE);
    secondLine.set(3, DiscType.WHITE);
    List<DiscType> thirdLine = piles.get(2);
    thirdLine.set(1, DiscType.WHITE);
    thirdLine.set(2, DiscType.BLACK);
    thirdLine.set(3, DiscType.BLACK);
    List<DiscType> fourthLine = piles.get(3);
    fourthLine.set(1, DiscType.EMPTY);
    fourthLine.set(2, DiscType.BLACK);
    BasicBoard boardNew = new BasicBoard(piles, DiscType.WHITE);
    boardNew.startGame();
    TextualViewBoard viewBoardNew = new TextualViewBoard(boardNew);
    System.out.println(viewBoardNew);
    // customized a board like this
    //  _ o _
    // x o o _
    //_ o x x _
    // _ _ x _
    //  _ _ _
    IStrategy opp = new AICaptureManyPieces();
    IStrategy strategy = new AIMinimax(opp);
    boardNew.placeDisc(strategy.getPosition(boardNew).getRow(),
            strategy.getPosition(boardNew).getIndex());
    System.out.println(viewBoardNew);
    Assert.assertEquals("x = 0, y = 0", strategy.getPosition(boardNew).toString());
    //  _ o _
    // x o o _
    //_ o o o o
    // _ _ x _
    //  _ _ _
    //and through test, our getCorner did find us the correct best move.
  }

  @Test(expected = IllegalStateException.class)
  public void testAIMinimaxException() {
    BasicBoard board = new BasicBoard(5);
    List<List<DiscType>> piles = board.getPile();
    List<DiscType> firstLine = piles.get(0);
    firstLine.set(3, DiscType.WHITE);
    List<DiscType> secondLine = piles.get(1);
    secondLine.set(1, DiscType.BLACK);
    secondLine.set(2, DiscType.WHITE);
    secondLine.set(3, DiscType.WHITE);
    secondLine.set(4, DiscType.WHITE);
    List<DiscType> thirdLine = piles.get(2);
    thirdLine.set(1, DiscType.WHITE);
    thirdLine.set(2, DiscType.BLACK);
    thirdLine.set(3, DiscType.BLACK);
    thirdLine.set(4, DiscType.WHITE);
    List<DiscType> fourthLine = piles.get(3);
    fourthLine.set(1, DiscType.WHITE);
    fourthLine.set(2, DiscType.BLACK);
    fourthLine.set(3, DiscType.WHITE);
    List<DiscType> fifthLine = piles.get(4);
    fifthLine.set(1, DiscType.WHITE);
    fifthLine.set(2, DiscType.WHITE);
    BasicBoard boardNew = new BasicBoard(piles, DiscType.WHITE);
    TextualViewBoard viewBoardNew = new TextualViewBoard(boardNew);
    System.out.println(viewBoardNew);

    IStrategy opp = new AICaptureManyPieces();
    IStrategy strategy = new AIMinimax(opp);
    strategy.getPosition(boardNew);

  }

  // getCorner and capture many
  @Test
  public void testCompositeStrategy2() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    board.placeDisc(4, 6);
    board.placeDisc(4, 7);
    board.placeDisc(3, 4);
    board.placeDisc(4, 3);
    board.placeDisc(3, 6);
    board.placeDisc(2, 4);
    board.placeDisc(2, 3);
    board.skipRound();
    board.placeDisc(1, 4);
    System.out.println(viewBoard);
    IStrategy strategy = new CompositeStrategy(new AIGetCorners(), new AICaptureManyPieces());
    Assert.assertEquals(0, strategy.getPosition(board).getRow());
    Assert.assertEquals(4, strategy.getPosition(board).getIndex());
  }

  @Test
  public void testCompositeStrategy3() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    board.placeDisc(4, 6);
    board.placeDisc(4, 7);
    board.placeDisc(3, 4);
    board.placeDisc(4, 3);
    board.placeDisc(3, 6);
    board.placeDisc(2, 4);
    board.placeDisc(2, 3);
    board.skipRound();
    board.placeDisc(1, 4);
    System.out.println(viewBoard);
    IStrategy strategy = new CompositeStrategy(new AIGetCorners(), new AIMinimax());
    Assert.assertEquals(0, strategy.getPosition(board).getRow());
    Assert.assertEquals(4, strategy.getPosition(board).getIndex());
  }

  @Test
  public void testAIMedian() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    board.placeDisc(4, 6);
    board.placeDisc(4, 7);
    board.placeDisc(3, 4);
    board.placeDisc(4, 3);
    board.placeDisc(3, 6);
    board.placeDisc(2, 4);
    board.placeDisc(2, 3);
    board.skipRound();
    board.placeDisc(1, 4);
    System.out.println(viewBoard);
    IStrategy strategy = new AIMedianStrategy();
    Assert.assertEquals(2, strategy.getPosition(board).getRow());
    Assert.assertEquals(5, strategy.getPosition(board).getIndex());

  }

  @Test
  public void testAIHard() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    board.placeDisc(4, 6);
    board.placeDisc(4, 7);
    board.placeDisc(3, 4);
    board.placeDisc(4, 3);
    board.placeDisc(3, 6);
    board.placeDisc(2, 4);
    board.placeDisc(2, 3);
    board.skipRound();
    board.placeDisc(1, 4);
    System.out.println(viewBoard);
    IStrategy strategy = new AIHardStrategy();
    Assert.assertEquals(0, strategy.getPosition(board).getRow());
    Assert.assertEquals(4, strategy.getPosition(board).getIndex());

  }


  // test Mock
}
