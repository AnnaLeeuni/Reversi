import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.IBoard;
import model.SquareReversiModel;
import view.textualview.TextualViewBoard;
import model.BasicBoard;
import model.DiscType;

/**
 * A class that tests for other shorter method such as setter and getter.
 * And exception throws.
 */
public class TestBasicBoardModelPublic {

  @Test
  public void TestGetTurn() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    DiscType turn = board.getTurns();
    Assert.assertEquals(DiscType.BLACK, turn);
    board.skipRound();
    DiscType turn2 = board.getTurns();
    Assert.assertEquals(DiscType.WHITE, turn2);
  }

  // test if skipRound swap the player turn
  @Test
  public void testSkipRound() {
    // current turn is black
    BasicBoard board = new BasicBoard();
    board.startGame();
    // should be white
    board.skipRound();
    Assert.assertEquals(DiscType.WHITE, board.getTurns());
  }

  @Test
  public void testSkipRoundDontMutatePile() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    List<List<DiscType>> copy = new ArrayList<>();
    List<DiscType> insideCopy;
    // make a deep copy of the pile
    for (List<DiscType> lc : board.getPile()) {
      insideCopy = new ArrayList<>(lc);
      copy.add(insideCopy);
    }

    board.skipRound();
    // the pile at board shouldn't get mutate after the skip
    Assert.assertEquals(copy, board.getPile());
  }

  @Test(expected = IllegalStateException.class)
  public void testSkipRoundWhenGameIsOver() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    board.skipRound();
    board.skipRound();
    board.skipRound();

  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceDiscWhenGameOver() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    board.skipRound();
    board.skipRound();
    board.placeDisc(6, 3);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetTurnWhenGameOver() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    board.skipRound();
    board.skipRound();
    board.getTurns();
  }

  @Test
  public void testGetTurnAtStart() {
    BasicBoard board = new BasicBoard();
    Assert.assertEquals(DiscType.BLACK, board.getTurns());
  }

  /**
   * the disc in the middle of the board should be empty.
   */
  @Test
  public void testGetDiscAtMiddleLine() {
    BasicBoard board = new BasicBoard();
    Assert.assertEquals(board.getDisc(5, 5), DiscType.EMPTY);
  }

  @Test
  public void testGetRightDiscWhite() {
    BasicBoard board = new BasicBoard();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    System.out.println(viewBoard);
    Assert.assertEquals(board.getDisc(4, 4), DiscType.WHITE);
  }

  @Test
  public void testInvalidInputForPlaceDisc1() {
    BasicBoard board = new BasicBoard();
    Assert.assertThrows(IllegalArgumentException.class, () ->
            board.placeDisc(-1, 1));
  }

  @Test
  public void testInvalidInputForPlaceDisc2() {
    BasicBoard board = new BasicBoard();
    Assert.assertThrows(IllegalArgumentException.class, () ->
            board.placeDisc(0, -2));
  }

  @Test
  public void testInvalidInputForPlaceDiscTooBig1() {
    BasicBoard board = new BasicBoard();
    Assert.assertThrows("row or index exceed the limits.",
            IllegalArgumentException.class, () -> board.placeDisc(11, 1));
  }

  @Test
  public void testInvalidInputForPlaceDiscTooBig2() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    Assert.assertThrows(IllegalStateException.class, () -> board.placeDisc(10, 6));
  }

  /**
   * test place disc at invalid location.
   * The placing of the location do not flip any disc, therefore is invalid move.
   */
  @Test
  public void testPlaceDiscInvalidMove() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    Assert.assertThrows(IllegalStateException.class, () -> {
      board.placeDisc(0, 0);
    });
  }

  @Test
  public void testGameOverWhenNotOver() {
    BasicBoard board = new BasicBoard();
    Assert.assertFalse(board.gameOver());
  }

  @Test
  public void testGameOverWhenNotOverAfterASkip() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    board.skipRound();
    Assert.assertFalse(board.gameOver());
  }

  @Test
  public void testGetWinnerWhenNotOver() {
    BasicBoard board = new BasicBoard();
    Assert.assertThrows(IllegalStateException.class, () -> {
      DiscType dt = board.getWinner();
    });

  }


  @Test
  public void testGetWinnerIsNull() {
    BasicBoard board = new BasicBoard(5);
    board.startGame();
    List<List<DiscType>> piles = board.getPile();
    List<DiscType> firstLine = piles.get(0);
    firstLine.set(3, DiscType.BLACK);
    List<DiscType> secondLine = piles.get(1);
    secondLine.set(1, DiscType.BLACK);
    secondLine.set(4, DiscType.WHITE);
    List<DiscType> fourthLine = piles.get(3);
    fourthLine.set(0, DiscType.WHITE);
    fourthLine.set(3, DiscType.BLACK);
    List<DiscType> fifthLine = piles.get(4);
    fifthLine.set(1, DiscType.WHITE);
    BasicBoard boardNew = new BasicBoard(piles);
    TextualViewBoard viewBoardNew = new TextualViewBoard(boardNew);
    System.out.println(viewBoardNew);
    /*
     * rigged board to get stalemate condition.
     * getWinner will be null when the score of two player are the same.
     *   _ x _
     *  x o x o
     * _ x _ o _
     *  o o x x
     *   _ o _
     */
    Assert.assertEquals(DiscType.BLACK, board.getTurns());
    board.skipRound();
    board.skipRound();
    Assert.assertNull(board.getWinner());
  }

  @Test
  public void testGameOverWhenSkippedTwoTimes() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    board.skipRound();
    board.skipRound();
    Assert.assertTrue(board.gameOver());
  }

  @Test
  public void testGetPile() {
    BasicBoard board1 = new BasicBoard(5);
    List<List<DiscType>> piles1 = board1.getPile();
    BasicBoard board2 = new BasicBoard(piles1);
    TextualViewBoard viewBoard1 = new TextualViewBoard(board1);
    TextualViewBoard viewBoard2 = new TextualViewBoard(board2);
    Assert.assertEquals(
            "  _ _ _\n"
                    + " _ o x _\n"
                    + "_ x _ o _\n"
                    + " _ o x _\n"
                    + "  _ _ _\n", viewBoard1.toString());
    Assert.assertEquals(
            "  _ _ _\n"
                    + " _ o x _\n"
                    + "_ x _ o _\n"
                    + " _ o x _\n"
                    + "  _ _ _\n", viewBoard2.toString());
  }

  private List<List<DiscType>> customPile;


  @Before
  public void setup() {
    List<DiscType> firstLine = new ArrayList<>();
    firstLine.add(null);
    firstLine.add(null);
    List<DiscType> firstLineRest = Collections.nCopies(3, DiscType.EMPTY);
    firstLine.addAll(firstLineRest);

    List<DiscType> secondLine = new ArrayList<>();
    secondLine.add(null);
    List<DiscType> secondLineRest = new ArrayList<>(List.of(
            DiscType.EMPTY, DiscType.WHITE, DiscType.BLACK, DiscType.EMPTY));
    secondLine.addAll(secondLineRest);

    List<DiscType> thirdLineRest = new ArrayList<>(
            List.of(DiscType.EMPTY, DiscType.BLACK, DiscType.EMPTY,
                    DiscType.WHITE, DiscType.EMPTY));
    List<DiscType> thirdLine = new ArrayList<>(thirdLineRest);

    List<DiscType> forthLine = new ArrayList<>(List.of(
            DiscType.EMPTY, DiscType.WHITE, DiscType.BLACK, DiscType.EMPTY));
    forthLine.add(null);


    List<DiscType> fifthLine = new ArrayList<>(Collections.nCopies(3, DiscType.EMPTY));
    fifthLine.add(null);
    fifthLine.add(null);

    customPile = List.of(firstLine, secondLine, thirdLine, forthLine,
            fifthLine);
  }

  // test constructor generate piles that we want
  @Test
  public void testGetPileReturnRightPile() {

    IBoard board = new BasicBoard(5);
    Assert.assertEquals(board.getPile(), customPile);
  }

  // the pile we received should be the same as the pile we passed in the constructor
  @Test
  public void testPassPileInConstructor() {

    IBoard board = new BasicBoard(customPile);
    Assert.assertEquals(board.getPile(), customPile);
  }

  @Test
  public void testGetWidthHeight() {
    BasicBoard board1 = new BasicBoard(5);
    int width1 = board1.getWidth();
    int height1 = board1.getHeight();
    Assert.assertEquals(5, width1);
    Assert.assertEquals(5, height1);

    BasicBoard board2 = new BasicBoard();
    int width2 = board2.getWidth();
    int height2 = board2.getHeight();
    Assert.assertEquals(11, width2);
    Assert.assertEquals(11, height2);

    List<List<DiscType>> piles1 = board1.getPile();
    BasicBoard board3 = new BasicBoard(piles1);
    int width3 = board3.getWidth();
    int height3 = board3.getHeight();
    Assert.assertEquals(5, width3);
    Assert.assertEquals(5, height3);
  }


  @Test
  public void testGetDiscWhenGameOver() {
    BasicBoard board = new BasicBoard(5);
    board.startGame();
    board.skipRound();
    board.skipRound();
    Assert.assertEquals(DiscType.EMPTY, board.getDisc(0, 0));
  }


  /**
   * if the user tries to get pile after game is over,
   * shouldn't throw any exception.
   */
  @Test
  public void testGetPileWhenGameOver() {
    BasicBoard board = new BasicBoard(5);
    board.startGame();
    board.skipRound();
    board.skipRound();
    //shouldn't throw any exception
    Assert.assertEquals(5, board.getPile().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetDiscInvalidArgument1() {
    BasicBoard board = new BasicBoard(5);
    board.getDisc(-1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetDiscInvalidArgument2() {
    BasicBoard board = new BasicBoard(5);
    board.getDisc(5, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetDiscInvalidArgument3() {
    BasicBoard board = new BasicBoard(5);
    board.getDisc(2, 5);
  }

  @Test
  public void testGetDiscValidArgument1() {
    BasicBoard board = new BasicBoard(5);
    Assert.assertEquals(DiscType.EMPTY, board.getDisc(2, 4));

  }

  @Test
  public void testGetDiscArgumentAll2() {
    BasicBoard board = new BasicBoard(5);

    Assert.assertEquals(DiscType.EMPTY, board.getDisc(0, 0));
    Assert.assertEquals(DiscType.EMPTY, board.getDisc(0, 2));

    // out of index limit in a single row
    Assert.assertThrows(IllegalArgumentException.class, () -> board.getDisc(0, 3));

    Assert.assertEquals(DiscType.EMPTY, board.getDisc(1, 0));
    Assert.assertThrows(IllegalArgumentException.class, () -> board.getDisc(1, 4));

    Assert.assertEquals(DiscType.EMPTY, board.getDisc(3, 0));
    // null value in the list cannot be retrieved.
    Assert.assertThrows(IllegalArgumentException.class, () -> board.getDisc(3, 4));
  }

  @Test
  public void testGetScoreReturnsRightScore() {
    BasicBoard board = new BasicBoard();

    Assert.assertEquals(3, board.getScore(DiscType.BLACK));
  }

  @Test
  public void testGetScoreReturnsRightScoreAfterPlace() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);

    System.out.println(viewBoard);
    board.placeDisc(6, 3);
    System.out.println(viewBoard);
    board.placeDisc(4, 6);
    System.out.println(viewBoard);
    Assert.assertEquals(4, board.getScore(DiscType.BLACK));
  }

  @Test
  public void testAnyLegalMoveWhenTrue() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);

    System.out.println(viewBoard);
    board.placeDisc(6, 3);
    Assert.assertTrue(board.legalMove(4, 6));
  }

  @Test
  public void testAnyLegalMoveWhenFalse() {
    BasicBoard board = new BasicBoard();
    board.startGame();

    board.placeDisc(6, 3);
    Assert.assertFalse(board.legalMove(4, 5));
  }

  @Test
  public void testAnyLegalMoveAfterAPlace() {
    BasicBoard board = new BasicBoard();
    board.startGame();

    board.placeDisc(6, 3);
    Assert.assertTrue(board.anyLegalMove());
  }

  @Test
  public void testAnyLegalMoves() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    System.out.println(viewBoard);
    Assert.assertEquals(6, board.legalMoves().size());
    board.placeDisc(4, 3);
    board.placeDisc(3, 2);
    board.skipRound();
    board.placeDisc(3, 4);
    board.skipRound();
    //board.placeDisc(4,2);
    System.out.println(viewBoard);
    Assert.assertEquals(2,board.legalMoves().size());

  }

  @Test
  public void testNewnew() {
    BasicBoard board = new BasicBoard(15);
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    board.placeDisc(5,6);
    board.placeDisc(4,5);
    board.placeDisc(6,8);
    board.placeDisc(8,8);
    board.placeDisc(9,6);
    board.placeDisc(8,5);
    board.placeDisc(6,5);
    board.placeDisc(5,8);
    board.placeDisc(4,8);

    System.out.println(viewBoard);
    Assert.assertEquals(false,board.gameOver());

  }

  @Test
  public void testGetCorners1() {
    BasicBoard board = new BasicBoard(5);
    board.startGame();
    Assert.assertEquals(6, board.getCorners().size());
    Assert.assertEquals("[x = 0, y = 0, x = 0, y = 2, x = 2, y = 0, x = 2, y = 4, x = 4, "
            + "y = 0, x = 4, y = 2]", board.getCorners().toString());
    Assert.assertEquals(17, board.getCornersNearBy().size());
  }

  @Test
  public void testGetCorners2() {
    SquareReversiModel board = new SquareReversiModel(8);
    board.startGame();
    Assert.assertEquals(4, board.getCorners().size());
    Assert.assertEquals("[x = 0, y = 0, x = 0, y = 7, x = 7, y = 7, x = 7, y = 0]",
            board.getCorners().toString());
    Assert.assertEquals(12, board.getCornersNearBy().size());
  }

}

