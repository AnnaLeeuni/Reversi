import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import model.DiscType;
import view.textualview.TextualViewBoard;
import model.BasicBoard;

/**
 * A test class that tests all the possible constructor and there possible edge cases.
 */
public class ConstructorTest {

  /**
   * Test if constructor generate piles that have right size.
   * Width and height have limit to them, therefore we have these tests.
   */
  @Test
  public void testConstructor() {
    BasicBoard board = new BasicBoard(5);
    Assert.assertEquals(5, board.getPile().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConNegative() {
    BasicBoard board = new BasicBoard(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConNegative2() {
    BasicBoard board = new BasicBoard(-1);
  }


  // define a board with a correct pile shouldn't throw any exception
  @Test
  public void testConstructorGivenRightPileDontThrowException() {
    BasicBoard board = new BasicBoard();
    BasicBoard board1 = new BasicBoard(board.getPile());
    Assert.assertEquals(2, 1 + 1);
  }

  @Test
  public void testConstructorGivenInvalidPileThrowException() {
    BasicBoard board = new BasicBoard();
    List<List<DiscType>> pile = board.getPile();
    pile.get(0).remove(6);
    Assert.assertThrows(IllegalArgumentException.class, () -> new BasicBoard(pile));
  }

  // using the standard constructor that creates 11x11 board.
  @Test
  public void testBasicConstructor() {
    BasicBoard board = new BasicBoard();
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    System.out.println(viewBoard);
    Assert.assertEquals("     _ _ _ _ _ _\n"
            + "    _ _ _ _ _ _ _\n"
            + "   _ _ _ _ _ _ _ _\n"
            + "  _ _ _ _ _ _ _ _ _\n"
            + " _ _ _ _ o x _ _ _ _\n"
            + "_ _ _ _ x _ o _ _ _ _\n"
            + " _ _ _ _ o x _ _ _ _\n"
            + "  _ _ _ _ _ _ _ _ _\n"
            + "   _ _ _ _ _ _ _ _\n"
            + "    _ _ _ _ _ _ _\n"
            + "     _ _ _ _ _ _\n", viewBoard.toString());
  }


  // test constructor
  @Test
  public void testConstructor1Valid() {
    BasicBoard board = new BasicBoard(5);
    TextualViewBoard viewBoard = new TextualViewBoard(board);
    System.out.println(viewBoard);
    Assert.assertEquals(""
            + "  _ _ _\n"
            + " _ o x _\n"
            + "_ x _ o _\n"
            + " _ o x _\n"
            + "  _ _ _\n", viewBoard.toString());
  }

  @Test
  public void testGeneratePileForRightSize() {
    BasicBoard board = new BasicBoard();
    Assert.assertEquals(board.getPile().get(0).size(), 11);
  }


  //test when height is even which is not allowed.
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor5Invalid() {
    BasicBoard board = new BasicBoard(8);
  }


  // if the board looks like this, no way to keep playing it
  // o x
  //x _ o
  // o x
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor7InValid() {
    BasicBoard board = new BasicBoard(3);
  }

}
