import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import model.IBoard;
import view.textualview.TextualViewBoard;
import model.BasicBoard;
import model.BoardLocation;
import model.DiscType;

/**
 * Testing the model public method in the basic board.
 */
public class TestBasicBoardGameExampleGamePlay {


  /**
   * test place discs for multiple rounds.
   */
  @Test
  public void testMultiplePlaceDisc() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);

    System.out.println(viewBoard);
    board.placeDisc(6, 3);
    System.out.println(viewBoard);
    board.placeDisc(4, 6);
    System.out.println(viewBoard);
    board.placeDisc(3, 4);
    System.out.println(viewBoard);
    board.placeDisc(2, 3);
    System.out.println(viewBoard);
    board.placeDisc(4, 7);
    System.out.println(viewBoard);

    BoardLocation lc = new BoardLocation(4, 4);

    Assert.assertEquals("     _ _ _ _ _ _\n"
            + "    _ _ _ _ _ _ _\n"
            + "   _ _ _ o _ _ _ _\n"
            + "  _ _ _ _ o _ _ _ _\n"
            + " _ _ _ _ x x x x _ _\n"
            + "_ _ _ _ x _ o _ _ _ _\n"
            + " _ _ _ x x x _ _ _ _\n"
            + "  _ _ _ _ _ _ _ _ _\n"
            + "   _ _ _ _ _ _ _ _\n"
            + "    _ _ _ _ _ _ _\n"
            + "     _ _ _ _ _ _\n", viewBoard.toString());
  }


  /**
   * the board should only contain black discs.
   */
  @Test
  public void testSkipTurnsWithPlaceDisc() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);

    System.out.println(viewBoard);
    board.placeDisc(6, 3);
    board.skipRound();
    System.out.println(viewBoard);
    board.placeDisc(4, 6);
    board.skipRound();
    System.out.println(viewBoard);
    board.placeDisc(3, 4);
    // board.skipRound();
    System.out.println(viewBoard);

    BoardLocation lc = new BoardLocation(4, 4);

    Assert.assertEquals("     _ _ _ _ _ _\n"
            + "    _ _ _ _ _ _ _\n"
            + "   _ _ _ _ _ _ _ _\n"
            + "  _ _ _ _ x _ _ _ _\n"
            + " _ _ _ _ x x x _ _ _\n"
            + "_ _ _ _ x _ x _ _ _ _\n"
            + " _ _ _ x x x _ _ _ _\n"
            + "  _ _ _ _ _ _ _ _ _\n"
            + "   _ _ _ _ _ _ _ _\n"
            + "    _ _ _ _ _ _ _\n"
            + "     _ _ _ _ _ _\n", viewBoard.toString());
  }


  @Test
  public void testGameOverAfterEntireGamePlay() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);

    board.placeDisc(6, 3);
    board.placeDisc(4, 6);
    board.placeDisc(3, 4);
    board.placeDisc(4, 3);
    board.placeDisc(6, 6);
    board.placeDisc(7, 4);
    board.skipRound();
    board.placeDisc(6, 2);
    board.placeDisc(6, 1);
    board.placeDisc(7, 2);
    board.placeDisc(8, 1);

    board.skipRound();

    board.placeDisc(3, 2);
    board.skipRound();

    board.placeDisc(4, 7);
    board.placeDisc(7, 6);

    board.placeDisc(8, 6);
    System.out.println(viewBoard);

    board.skipRound();

    board.placeDisc(8, 3);
    System.out.println(viewBoard);

    Assert.assertTrue(board.gameOver());
  }

  @Test
  public void testGameOverGetCorrectWinner() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    board.placeDisc(6, 3);
    board.placeDisc(4, 6);
    board.placeDisc(3, 4);
    board.placeDisc(4, 3);
    board.placeDisc(6, 6);
    board.placeDisc(7, 4);
    board.skipRound();
    board.placeDisc(6, 2);
    board.placeDisc(6, 1);
    board.placeDisc(7, 2);
    board.placeDisc(8, 1);
    board.skipRound();
    board.placeDisc(3, 2);
    board.skipRound();
    board.placeDisc(4, 7);
    board.placeDisc(7, 6);
    board.placeDisc(8, 6);
    board.skipRound();
    board.placeDisc(8, 3);
    System.out.println(viewBoard);

    Assert.assertTrue(board.gameOver());
    Assert.assertEquals(board.getWinner(), DiscType.BLACK);
  }

  // test when one side has no valid move to make therefore has to skip.
  // the current game implementation do not notice the player to skip
  // automatically when there is no valid move, player has to make that observation themselves,
  // and call skipRound manually.
  @Test
  public void testHasToSkipRound() {
    BasicBoard board = new BasicBoard(5);
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    board.placeDisc(0, 1);
    board.placeDisc(4, 1);
    board.placeDisc(3, 3);
    board.placeDisc(1, 0);
    board.placeDisc(3, 0);
    board.placeDisc(1, 3);
    System.out.println(viewBoard);
    /*
     *   _ x _
     *  o o o o
     * _ x _ o _
     *  x x o x
     *   _ o _
     * There is no place for x or o to play.
     */
    Assert.assertTrue(board.gameOver());
  }


  /**
   * test case when place a disc can cause discs from different direction to flip.
   */
  @Test
  public void testPlaceCanFormTwoLine() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    System.out.println(viewBoard);
    board.placeDisc(4, 6);
    System.out.println(viewBoard);

    board.placeDisc(4, 7);
    System.out.println(viewBoard);

    board.placeDisc(3, 4);
    System.out.println(viewBoard);

    board.placeDisc(4, 3);
    System.out.println(viewBoard);

    Assert.assertEquals("     _ _ _ _ _ _\n"
            + "    _ _ _ _ _ _ _\n"
            + "   _ _ _ _ _ _ _ _\n"
            + "  _ _ _ _ x _ _ _ _\n"
            + " _ _ _ o o o o o _ _\n"
            + "_ _ _ _ o _ x _ _ _ _\n"
            + " _ _ _ _ o x _ _ _ _\n"
            + "  _ _ _ _ _ _ _ _ _\n"
            + "   _ _ _ _ _ _ _ _\n"
            + "    _ _ _ _ _ _ _\n"
            + "     _ _ _ _ _ _\n", viewBoard.toString());

  }

  @Test
  public void testPlaceDiscWhileADiscIsOnBoarder() {
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
    System.out.println(viewBoard);

    Assert.assertEquals("     _ _ _ _ o _\n"
            + "    _ _ _ _ o _ _\n"
            + "   _ _ _ x x x _ _\n"
            + "  _ _ _ _ o _ x _ _\n"
            + " _ _ _ o o x x o _ _\n"
            + "_ _ _ _ o _ x _ _ _ _\n"
            + " _ _ _ _ o x _ _ _ _\n"
            + "  _ _ _ _ _ _ _ _ _\n"
            + "   _ _ _ _ _ _ _ _\n"
            + "    _ _ _ _ _ _ _\n"
            + "     _ _ _ _ _ _\n", viewBoard.toString());
  }


  @Test
  public void testPlaceTopLeftCorner() {
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
    board.placeDisc(0, 0);

    Assert.assertEquals(DiscType.WHITE, board.getDisc(0, 0));
  }

  /**
   * test complete game play.
   */
  @Test
  public void testCompleteGamePlay() {
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
    board.placeDisc(0, 0);
    board.skipRound();
    board.placeDisc(2, 6);
    board.placeDisc(2, 7);
    board.placeDisc(1, 6);
    System.out.println(viewBoard);
    board.placeDisc(0, 5);
    System.out.println(viewBoard);
    board.placeDisc(10, 2);
    System.out.println(viewBoard);
    board.skipRound();
    board.placeDisc(10, 0);
    System.out.println(viewBoard);

    Assert.assertTrue(board.gameOver());
    Assert.assertEquals(DiscType.WHITE, board.getWinner());
  }


  @Test
  public void testPlaceInTheMiddleWithoutCustomsPiles() {
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
    System.out.println(viewBoard);

    board.placeDisc(3, 2);
    System.out.println(viewBoard);

    Assert.assertEquals(DiscType.BLACK, board.getDisc(3, 2));
  }


  /**
   * test if the place of the disc filled the entire line.
   */
  @Test
  public void testPlaceDiscFilledAline() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    System.out.println(viewBoard);
    board.placeDisc(4, 6);
    System.out.println(viewBoard);

    board.placeDisc(4, 7);
    System.out.println(viewBoard);

    board.placeDisc(3, 4);
    System.out.println(viewBoard);

    board.placeDisc(4, 3);
    System.out.println(viewBoard);

    board.placeDisc(3, 6);
    System.out.println(viewBoard);

    board.placeDisc(2, 4);
    System.out.println(viewBoard);

    board.placeDisc(2, 3);
    System.out.println(viewBoard);

    board.skipRound();
    board.placeDisc(1, 4);
    System.out.println(viewBoard);

    board.placeDisc(0, 4);
    System.out.println(viewBoard);

    board.placeDisc(2, 5);
    System.out.println(viewBoard);

    board.placeDisc(6, 6);
    System.out.println(viewBoard);

    board.placeDisc(7, 6);
    System.out.println(viewBoard);

    board.placeDisc(6, 7);
    System.out.println(viewBoard);

    board.placeDisc(0, 3);
    System.out.println(viewBoard);

    board.placeDisc(0, 2);
    System.out.println(viewBoard);

    board.placeDisc(7, 4);
    System.out.println(viewBoard);

    board.placeDisc(8, 4);
    System.out.println(viewBoard);

    board.placeDisc(5, 8);
    System.out.println(viewBoard);

    board.placeDisc(4, 8);
    System.out.println(viewBoard);

    board.placeDisc(6, 3);
    System.out.println(viewBoard);

    board.placeDisc(7, 2);
    System.out.println(viewBoard);

    board.placeDisc(3, 8);
    System.out.println(viewBoard);

    board.placeDisc(4, 9);
    System.out.println(viewBoard);

    board.placeDisc(5, 10);
    System.out.println(viewBoard);

    board.placeDisc(6, 8);
    System.out.println(viewBoard);

    board.placeDisc(7, 8);
    System.out.println(viewBoard);

    board.placeDisc(8, 5);
    System.out.println(viewBoard);

    board.placeDisc(8, 6);
    System.out.println(viewBoard);

    board.placeDisc(8, 7);
    System.out.println(viewBoard);

    board.placeDisc(9, 6);
    System.out.println(viewBoard);

    board.placeDisc(2, 2);
    System.out.println(viewBoard);

    board.placeDisc(2, 1);
    System.out.println(viewBoard);

    board.placeDisc(6, 9);
    System.out.println(viewBoard);

    board.placeDisc(6, 2);
    System.out.println(viewBoard);

    board.placeDisc(5, 2);
    System.out.println(viewBoard);

    board.placeDisc(6, 1);
    System.out.println(viewBoard);

    board.placeDisc(6, 0);
    System.out.println(viewBoard);

    for (int i = 0; i < board.getWidth(); i++) {
      try {
        board.getDisc(6, i);
      } catch (IllegalArgumentException e) {
        continue;
      }
      Assert.assertEquals(DiscType.WHITE, board.getDisc(6, i));
    }
  }

  @Test
  public void testPlaceOnASandwichedLine() {
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
    System.out.println(viewBoard);

    board.placeDisc(7, 6);
    System.out.println(viewBoard);

    board.placeDisc(6, 7);
    System.out.println(viewBoard);

    board.placeDisc(0, 3);
    System.out.println(viewBoard);

    board.placeDisc(0, 2);
    System.out.println(viewBoard);

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
    System.out.println(viewBoard);

    System.out.println(board.getTurns());
    Assert.assertThrows(IllegalStateException.class, () -> {
      board.placeDisc(9, 4);
    });
  }

  /**
   * if the user input extends the width of that line.
   * but the move is theoretically valid
   */
  @Test
  public void testInvalidMoveOnTheBoarder() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    System.out.println(viewBoard);
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
    System.out.println(viewBoard);


    Assert.assertThrows(IllegalStateException.class, () -> {
      board.placeDisc(4, 10);
    });
  }


  @Test
  public void testPlaceInBetween() {
    BasicBoard board = new BasicBoard();
    board.startGame();
    List<List<DiscType>> piles = board.getPile();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    System.out.println(viewBoard);
    List<DiscType> fifthLine = piles.get(4);
    fifthLine.set(7, DiscType.BLACK); // manually setting a condition
    List<DiscType> sixthLine = piles.get(5);
    sixthLine.set(7, DiscType.WHITE);
    List<DiscType> fourLine = piles.get(3);
    fourLine.set(6, DiscType.BLACK);
    fourLine.set(5, DiscType.WHITE);
    List<DiscType> thirdLine = piles.get(2);
    thirdLine.set(7, DiscType.BLACK);
    // the rigged board

    BasicBoard boardNew = new BasicBoard(piles);
    boardNew.startGame();
    TextualViewBoard viewBoardNew = new TextualViewBoard(boardNew);
    System.out.println(viewBoardNew);

    // now place a white in between the black and see if the black will lose the piece
    boardNew.placeDisc(6, 3);
    System.out.println(viewBoardNew);
    Assert.assertEquals(""
            + "     _ _ _ _ _ _\n"
            + "    _ _ _ _ _ _ _\n"
            + "   _ _ _ _ x _ _ _\n"
            + "  _ _ _ o x _ _ _ _\n"
            + " _ _ _ _ o x x _ _ _\n"
            + "_ _ _ _ x _ o o _ _ _\n"
            + " _ _ _ x x x _ _ _ _\n"
            + "  _ _ _ _ _ _ _ _ _\n"
            + "   _ _ _ _ _ _ _ _\n"
            + "    _ _ _ _ _ _ _\n"
            + "     _ _ _ _ _ _\n", viewBoardNew.toString());


    boardNew.placeDisc(3, 5);
    System.out.println(viewBoardNew);
    Assert.assertEquals(""
            + "     _ _ _ _ _ _\n"
            + "    _ _ _ _ _ _ _\n"
            + "   _ _ _ _ x _ _ _\n"
            + "  _ _ _ o o o _ _ _\n"
            + " _ _ _ _ o x o _ _ _\n"
            + "_ _ _ _ x _ o o _ _ _\n"
            + " _ _ _ x x x _ _ _ _\n"
            + "  _ _ _ _ _ _ _ _ _\n"
            + "   _ _ _ _ _ _ _ _\n"
            + "    _ _ _ _ _ _ _\n"
            + "     _ _ _ _ _ _\n", viewBoardNew.toString());

  }

  @Test
  public void testNoAvailableMoveForcedToSkip() {
    IBoard board = new BasicBoard(5);
    board.startGame();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    board.placeDisc(0, 1);
    board.placeDisc(4, 1);
    System.out.println(viewBoard);
    board.placeDisc(3, 3);
    System.out.println(viewBoard);
    board.placeDisc(1, 3);
    System.out.println(viewBoard);
    board.placeDisc(3, 0);
    System.out.println(viewBoard);
    board.placeDisc(1, 0);
    System.out.println(viewBoard);

    IllegalStateException e = Assert.assertThrows(
            IllegalStateException.class, () -> board.placeDisc(1, 2));
    Assert.assertEquals(e.getMessage(), "game is already over");
  }
}
