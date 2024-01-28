import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.event.KeyEvent;

import controller.BoardController;
import controller.player.HumanPlayer;
import controller.player.IPlayer;
import controller.player.MockPlayer;
import model.BasicBoard;
import model.BoardWithNotifier;
import model.DiscType;
import model.SquareReversiModel;
import view.ViewBoard;
import view.MockView;

/**
 * Test class to test method in view using mock players and mock controllers.
 */
public class TestViewBoard {

  ViewBoard viewPlayer1;
  ViewBoard viewPlayer2;

  IPlayer player1;
  IPlayer player2;

  Appendable appendable1;
  Appendable appendable2;

  BoardController controller1;

  BoardController controller2;

  // with regular game features
  @Before
  public void init() {
    BasicBoard basic = new BasicBoard(7);
    BoardWithNotifier board = new BoardWithNotifier(basic);
    board.startGame();
    viewPlayer1 = new ViewBoard(board);
    viewPlayer2 = new ViewBoard(board);
    player2 = new HumanPlayer(DiscType.BLACK, board);
    player1 = new HumanPlayer(DiscType.WHITE, board);
    controller1 = new BoardController(player1, board, viewPlayer1);
    controller2 = new BoardController(player2, board, viewPlayer2);
  }

  @Before
  public void initSquare() {
    SquareReversiModel square = new SquareReversiModel(8);
    BoardWithNotifier board = new BoardWithNotifier(square);
    board.startGame();
    viewPlayer1 = new ViewBoard(board);
    viewPlayer2 = new ViewBoard(board);
    player2 = new HumanPlayer(DiscType.BLACK, board);
    player1 = new HumanPlayer(DiscType.WHITE, board);
    controller1 = new BoardController(player1, board, viewPlayer1);
    controller2 = new BoardController(player2, board, viewPlayer2);

  }

  // with mock views
  @Before
  public void init3() {
    BasicBoard basic = new BasicBoard(7);
    BoardWithNotifier board = new BoardWithNotifier(basic);
    board.startGame();
    appendable1 = new StringBuilder();
    appendable2 = new StringBuilder();
    viewPlayer1 = new MockView(appendable1, board);
    viewPlayer2 = new MockView(appendable2, board);
    player2 = new HumanPlayer(DiscType.BLACK, board);
    player1 = new HumanPlayer(DiscType.WHITE, board);
    controller1 = new BoardController(player1, board, viewPlayer1);
    controller2 = new BoardController(player2, board, viewPlayer2);

  }

  @Before
  public void initSquare3() {
    SquareReversiModel square = new SquareReversiModel(8);
    BoardWithNotifier board = new BoardWithNotifier(square);
    board.startGame();
    appendable1 = new StringBuilder();
    appendable2 = new StringBuilder();
    viewPlayer1 = new MockView(appendable1, board);
    viewPlayer2 = new MockView(appendable2, board);
    player2 = new HumanPlayer(DiscType.BLACK, board);
    player1 = new HumanPlayer(DiscType.WHITE, board);
    controller1 = new BoardController(player1, board, viewPlayer1);
    controller2 = new BoardController(player2, board, viewPlayer2);

  }


  // with mock players
  @Before
  public void init2() {
    BasicBoard basic = new BasicBoard(7);
    BoardWithNotifier board = new BoardWithNotifier(basic);
    board.startGame();
    viewPlayer1 = new ViewBoard(board);
    viewPlayer2 = new ViewBoard(board);
    appendable1 = new StringBuilder();
    appendable2 = new StringBuilder();
    player2 = new MockPlayer(appendable2);
    player1 = new MockPlayer(appendable1);
    controller1 = new BoardController(player1, board, viewPlayer1);
    controller2 = new BoardController(player2, board, viewPlayer2);
  }

  @Before
  public void initSquare2() {
    SquareReversiModel square = new SquareReversiModel(8);
    BoardWithNotifier board = new BoardWithNotifier(square);
    board.startGame();
    viewPlayer1 = new ViewBoard(board);
    viewPlayer2 = new ViewBoard(board);
    appendable1 = new StringBuilder();
    appendable2 = new StringBuilder();
    player2 = new MockPlayer(appendable2);
    player1 = new MockPlayer(appendable1);
    controller1 = new BoardController(player1, board, viewPlayer1);
    controller2 = new BoardController(player2, board, viewPlayer2);
  }

  @Test
  public void testAddFeaturesAndGet() {
    init();
    Assert.assertEquals(1, viewPlayer1.getPlayerFeatures().size());
    viewPlayer1.addFeatures(player1);
    Assert.assertEquals(2, viewPlayer1.getPlayerFeatures().size());
  }

  @Test
  public void testAddFeaturesAndGet2() {
    initSquare();
    Assert.assertEquals(1, viewPlayer1.getPlayerFeatures().size());
    viewPlayer1.addFeatures(player1);
    Assert.assertEquals(2, viewPlayer1.getPlayerFeatures().size());
  }


  /**
   * when the controller was informed in onGameOver()
   * it should calls view to pop out the game over message.
   */
  @Test
  public void testGameOverMessage() {
    init3();
    controller1.onGameOver();
    Assert.assertTrue(appendable1.toString().contains("gameOver"));
  }

  @Test
  public void testGameOverMessage2() {
    initSquare3();
    controller1.onGameOver();
    Assert.assertTrue(appendable1.toString().contains("gameOver"));
  }

  @Test
  public void testKeyReleaseP() {
    init2();
    KeyEvent pRelease = new KeyEvent(viewPlayer1, KeyEvent.KEY_RELEASED, System.currentTimeMillis(),
            0, KeyEvent.VK_P, 'p');
    viewPlayer1.keyReleased(pRelease);
    // should inform player1 on the view to skip the round
    Assert.assertTrue(appendable1.toString().contains("tryToSkip"));
    // playerB is unable to receive the commands
    Assert.assertFalse(appendable2.toString().contains("tryToSkip"));
  }

  @Test
  public void testKeyReleaseP2() {
    initSquare2();
    KeyEvent pRelease = new KeyEvent(viewPlayer1, KeyEvent.KEY_RELEASED, System.currentTimeMillis(),
            0, KeyEvent.VK_P, 'p');
    viewPlayer1.keyReleased(pRelease);
    // should inform player1 on the view to skip the round
    Assert.assertTrue(appendable1.toString().contains("tryToSkip"));
    // playerB is unable to receive the commands
    Assert.assertFalse(appendable2.toString().contains("tryToSkip"));
  }

}
