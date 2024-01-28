import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import model.BasicBoard;
import model.DiscType;
import model.IBoard;
import model.SquareReversiModel;
import view.SquareTextualView;

/**
 * All the tests for square model, the public method.
 */
public class TestSquareBoard {

  // test if gameOvers after a complete game play
  @Test
  public void testCompleteGamePlay() {
    SquareReversiModel model = new SquareReversiModel(4);
    SquareTextualView view = new SquareTextualView(model);
    System.out.println(view);
    model.startGame();
    model.placeDisc(1, 3);
    System.out.println(view);
    model.placeDisc(0, 1);
    System.out.println(view);
    model.placeDisc(1, 0);
    System.out.println(view);
    model.placeDisc(2, 3);
    System.out.println(view);
    model.placeDisc(3, 3);
    System.out.println(view);
    model.placeDisc(3, 2);
    System.out.println(view);
    model.placeDisc(3, 1);
    System.out.println(view);
    model.placeDisc(3, 0);
    System.out.println(view);
    model.placeDisc(0, 2);
    System.out.println(view);
    model.placeDisc(0, 3);
    System.out.println(view);
    model.placeDisc(2, 0);
    System.out.println(view);
    model.placeDisc(0, 0);
    System.out.println(view);

    Assert.assertTrue(model.gameOver());
  }

  // test of the initiation of the game is correct
  @Test
  public void testGenerateSquareReversi() {
    SquareReversiModel model = new SquareReversiModel(8);
    SquareTextualView view = new SquareTextualView(model);
    Assert.assertEquals("_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ x o _ _ _ \n"
            + "_ _ _ o x _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n", view.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSquareReversiConstructor1() {
    SquareReversiModel model = new SquareReversiModel(5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSquareReversiConstructor2() {
    SquareReversiModel model = new SquareReversiModel(3);
  }

  // test of skipping twice is going to end the game
  @Test
  public void testSkipAndGameOver() {
    SquareReversiModel model = new SquareReversiModel(8);
    model.startGame();
    model.skipRound();
    model.skipRound();
    Assert.assertTrue(model.gameOver());
  }

  // test to see skipping once and moved and then skip is not going to end the game
  @Test
  public void testSkipOnceAndContinue() {
    SquareReversiModel model = new SquareReversiModel(8);
    model.startGame();
    model.skipRound();
    model.placeDisc(3, 2);
    model.skipRound();
    Assert.assertFalse(model.gameOver());
  }

  // PlaceDisc out of bound
  @Test(expected = IllegalArgumentException.class)
  public void testPlaceDiscException() {
    SquareReversiModel model = new SquareReversiModel(8);
    model.startGame();
    model.placeDisc(8, 0);
  }

  // PlaceDisc out of bound
  @Test(expected = IllegalArgumentException.class)
  public void testPlaceDiscException2() {
    SquareReversiModel model = new SquareReversiModel(8);
    model.startGame();
    model.placeDisc(-1, 0);
  }


  @Test
  public void testMakeCopyBoard() {
    SquareReversiModel model = new SquareReversiModel(8);
    model.startGame();
    IBoard copyBoard = model.makeBoard(model);
    Assert.assertEquals(8, copyBoard.getPile().size());

    Assert.assertEquals(8, copyBoard.getPile().get(0).size());
    SquareTextualView view = new SquareTextualView(copyBoard);
    Assert.assertEquals("_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ x o _ _ _ \n"
            + "_ _ _ o x _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n", view.toString());
  }

  @Test
  public void testGetPile() {
    SquareReversiModel model = new SquareReversiModel(8);
    model.startGame();
    List<List<DiscType>> copyBoard = model.getPile();
    Assert.assertEquals(8, copyBoard.size());
  }

  @Test
  public void testGetWidth() {
    SquareReversiModel model = new SquareReversiModel(8);
    model.startGame();
    int width = model.getWidth();
    Assert.assertEquals(8, width);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGetDisc1() {
    SquareReversiModel model = new SquareReversiModel(8);
    model.startGame();
    model.getDisc(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetDis2c() {
    SquareReversiModel model = new SquareReversiModel(8);
    model.startGame();
    model.getDisc(8, 0);
  }

  // test get winner when game is not finish yet
  @Test(expected = IllegalStateException.class)
  public void testGetWinner() {
    SquareReversiModel model = new SquareReversiModel(4);
    model.startGame();
    model.getWinner(); // the game has not ended yet
  }

  // test get winner after a regular game play
  @Test
  public void testGetWinner2() {
    SquareReversiModel model = new SquareReversiModel(4);
    model.startGame();
    SquareTextualView view = new SquareTextualView(model);
    System.out.println(view);
    model.placeDisc(1, 3);
    model.skipRound();
    model.skipRound();
    Assert.assertEquals(DiscType.BLACK, model.getWinner());
  }

  // when there is a tie, return null
  @Test
  public void testGetWinner3() {
    SquareReversiModel model = new SquareReversiModel(4);
    model.startGame();
    SquareTextualView view = new SquareTextualView(model);
    System.out.println(view);
    model.skipRound();
    model.skipRound();
    Assert.assertNull(model.getWinner());
  }

  @Test
  public void testGetTurns() {
    SquareReversiModel model = new SquareReversiModel(4);
    model.startGame();
    Assert.assertEquals(DiscType.BLACK, model.getTurns());
    model.placeDisc(1, 3);
    Assert.assertEquals(DiscType.WHITE, model.getTurns());
    model.skipRound();
    Assert.assertEquals(DiscType.BLACK, model.getTurns());
  }


  // test if legalMove(int, int) is correct.
  @Test
  public void testLegalMove() {
    SquareReversiModel model = new SquareReversiModel(4);
    model.startGame();
    Assert.assertTrue(model.legalMove(1, 3));
    model.placeDisc(1, 3);
    Assert.assertTrue(model.legalMove(0, 3));
    model.placeDisc(0, 3);
    Assert.assertFalse(model.legalMove(2, 3));
    model.placeDisc(0, 2);
    model.placeDisc(0, 1);
    model.placeDisc(3, 0);
    model.placeDisc(2, 3);
    model.placeDisc(0, 0);
    model.placeDisc(3, 1);
    model.placeDisc(3, 3);
    SquareTextualView view = new SquareTextualView(model);
    System.out.println(view);
    Assert.assertEquals(
            "x o o o \n"
                    + "_ x o o \n"
                    + "_ o x o \n"
                    + "x o _ x \n", view.toString());
  }


  // test when there isn't any legal moves and anyLegalMoves for current
  // player should be false
  @Test
  public void testAnyLegalMoves() {
    SquareReversiModel model = new SquareReversiModel(4);
    model.startGame();
    model.placeDisc(1, 3);
    model.placeDisc(0, 3);
    model.placeDisc(0, 2);
    model.placeDisc(0, 1);
    model.placeDisc(3, 0);
    model.placeDisc(2, 3);
    model.placeDisc(0, 0);
    model.placeDisc(3, 1);
    model.placeDisc(3, 3);
    SquareTextualView view = new SquareTextualView(model);
    System.out.println(view);
    Assert.assertTrue(model.anyLegalMove());
    Assert.assertTrue(model.anyLegalMove(model.getTurns()));
    Assert.assertEquals(3, model.legalMoves().size());
    model.placeDisc(3, 2);
    System.out.println(view);
    Assert.assertFalse(model.anyLegalMove());
    Assert.assertFalse(model.anyLegalMove(model.getTurns()));
    Assert.assertEquals(0, model.legalMoves().size());

  }

  @Test
  public void testGetScore() {
    SquareReversiModel model = new SquareReversiModel(4);
    model.startGame();
    model.placeDisc(1, 3);
    Assert.assertEquals(4, model.getScore(DiscType.BLACK));
    Assert.assertEquals(1, model.getScore(DiscType.WHITE));
  }


  @Test
  public void testPlaceDisc() {
    SquareReversiModel model = new SquareReversiModel(8);
    SquareTextualView view = new SquareTextualView(model);
    model.startGame();
    // the initial state of the board
    Assert.assertEquals("_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ x o _ _ _ \n"
            + "_ _ _ o x _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n", view.toString());
    model.placeDisc(3, 5);
    // placing one Disc at horizontal direction
    Assert.assertEquals("_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ x x x _ _ \n"
            + "_ _ _ o x _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n", view.toString());
    model.placeDisc(2, 5);
    // placing one Disc at Diagonal direction
    Assert.assertEquals("_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ o _ _ \n"
            + "_ _ _ x o x _ _ \n"
            + "_ _ _ o x _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n", view.toString());
    model.placeDisc(2, 4);
    // placing one Disc at Vertical direction
    Assert.assertEquals("_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ x o _ _ \n"
            + "_ _ _ x x x _ _ \n"
            + "_ _ _ o x _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n", view.toString());
    model.placeDisc(2, 3);
    // capturing at two direction
    Assert.assertEquals("_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ o o o _ _ \n"
            + "_ _ _ o x x _ _ \n"
            + "_ _ _ o x _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n", view.toString());
    model.skipRound();
    model.placeDisc(3, 6);
    Assert.assertEquals("_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ o o o _ _ \n"
            + "_ _ _ o o o o _ \n"
            + "_ _ _ o x _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n", view.toString());
    model.placeDisc(1, 4);
    Assert.assertEquals("_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ x _ _ _ \n"
            + "_ _ _ o x o _ _ \n"
            + "_ _ _ o x o o _ \n"
            + "_ _ _ o x _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n", view.toString());
    model.placeDisc(4, 5);
    // capturing at two different directions.
    Assert.assertEquals("_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ x _ _ _ \n"
            + "_ _ _ o x o _ _ \n"
            + "_ _ _ o o o o _ \n"
            + "_ _ _ o o o _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n", view.toString());
    model.placeDisc(5, 4);
    System.out.println(view);
    model.skipRound();
    model.placeDisc(2, 7);
    System.out.println(view);


  }

  @Test(expected = IllegalStateException.class)
  public void testIllegalMove() {
    SquareReversiModel model = new SquareReversiModel(4);
    model.startGame();
    SquareTextualView view = new SquareTextualView(model);
    System.out.println(view);
    model.placeDisc(1, 0);
  }

  @Test
  public void getMutableVersion() {
    SquareReversiModel model = new SquareReversiModel(4);
    model.startGame();
    Assert.assertTrue(model.getMutableVersion() instanceof SquareReversiModel);
  }

  @Test
  public void getMutableVersion2() {
    BasicBoard model = new BasicBoard(7);
    model.startGame();
    Assert.assertTrue(model.getMutableVersion() instanceof BasicBoard);
  }

}
