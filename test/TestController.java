
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controller.BoardController;
import controller.MockController;
import controller.player.MockPlayer;
import model.BasicBoard;
import model.BoardWithNotifier;
import model.DiscType;
import model.SquareReversiModel;
import view.ViewBoard;
import controller.player.IPlayer;
import controller.player.HumanPlayer;

/**
 * Test class to test for method in the controller.
 */
public class TestController {

  private BoardWithNotifier board;
  private MockController controller1;
  private MockController controller2;
  private BoardController controller3;
  private BoardController controller4;
  private IPlayer player2;
  private IPlayer player1;

  private Appendable appendable1;

  private Appendable appendable2;


  // all real class
  @Before
  public void init1() {

    BasicBoard basic = new BasicBoard(7);
    board = new BoardWithNotifier(basic);
    board.startGame();
    ViewBoard viewPlayer1 = new ViewBoard(board);
    ViewBoard viewPlayer2 = new ViewBoard(board);
    player2 = new HumanPlayer(DiscType.BLACK, board);
    player1 = new HumanPlayer(DiscType.WHITE, board);
    controller3 = new BoardController(player1, board, viewPlayer1);
    controller4 = new BoardController(player2, board, viewPlayer2);
  }

  // initialization of square model
  @Before
  public void initSquare1() {

    SquareReversiModel square = new SquareReversiModel(8);
    board = new BoardWithNotifier(square);
    board.startGame();
    ViewBoard viewPlayer1 = new ViewBoard(board);
    ViewBoard viewPlayer2 = new ViewBoard(board);
    appendable1 = new StringBuilder();
    appendable2 = new StringBuilder();
    player2 = new HumanPlayer(DiscType.BLACK, board);
    player1 = new HumanPlayer(DiscType.WHITE, board);
    controller3 = new BoardController(player1, board, viewPlayer1);
    controller4 = new BoardController(player2, board, viewPlayer2);
  }

  // mock controller
  @Before
  public void init2() {

    BasicBoard basic = new BasicBoard(7);
    board = new BoardWithNotifier(basic);
    board.startGame();
    ViewBoard viewPlayer1 = new ViewBoard(board);
    ViewBoard viewPlayer2 = new ViewBoard(board);
    player2 = new HumanPlayer(DiscType.BLACK, board);
    player1 = new HumanPlayer(DiscType.WHITE, board);
    appendable1 = new StringBuilder();
    appendable2 = new StringBuilder();
    controller1 = new MockController(appendable1, player1, board, viewPlayer1);
    controller2 = new MockController(appendable2, player2, board, viewPlayer2);
    board.startGame();

  }

  // mock controller for the Square game
  @Before
  public void initSquare2() {

    SquareReversiModel square = new SquareReversiModel(8);
    board = new BoardWithNotifier(square);
    board.startGame();
    ViewBoard viewPlayer1 = new ViewBoard(board);
    ViewBoard viewPlayer2 = new ViewBoard(board);
    player2 = new HumanPlayer(DiscType.BLACK, board);
    player1 = new HumanPlayer(DiscType.WHITE, board);
    appendable1 = new StringBuilder();
    appendable2 = new StringBuilder();
    controller1 = new MockController(appendable1, player1, board, viewPlayer1);
    controller2 = new MockController(appendable2, player2, board, viewPlayer2);

  }

  // mock player
  @Before
  public void init3() {

    BasicBoard basic = new BasicBoard(7);
    board = new BoardWithNotifier(basic);
    board.startGame();
    ViewBoard viewPlayer1 = new ViewBoard(board);
    ViewBoard viewPlayer2 = new ViewBoard(board);
    appendable1 = new StringBuilder();
    appendable2 = new StringBuilder();
    player2 = new MockPlayer(appendable1);
    player1 = new MockPlayer(appendable2);
    controller3 = new BoardController(player1, board, viewPlayer1);
    controller4 = new BoardController(player2, board, viewPlayer2);

  }

  // mock player
  @Before
  public void initSquare3() {

    SquareReversiModel square = new SquareReversiModel(8);
    board = new BoardWithNotifier(square);
    board.startGame();
    ViewBoard viewPlayer1 = new ViewBoard(board);
    ViewBoard viewPlayer2 = new ViewBoard(board);
    appendable1 = new StringBuilder();
    appendable2 = new StringBuilder();
    player2 = new MockPlayer(appendable1);
    player1 = new MockPlayer(appendable2);
    controller3 = new BoardController(player1, board, viewPlayer1);
    controller4 = new BoardController(player2, board, viewPlayer2);

  }

  @Test
  public void testActionPerformedSkip() {
    init1();
    Assert.assertEquals(DiscType.BLACK, board.getTurns());
    controller4.actionPerformed("P");
    Assert.assertEquals(DiscType.WHITE, board.getTurns());
  }

  @Test
  public void testActionPerformedSkip2() {
    initSquare1();
    Assert.assertEquals(DiscType.BLACK, board.getTurns());
    controller4.actionPerformed("P");
    Assert.assertEquals(DiscType.WHITE, board.getTurns());
  }

  @Test
  public void testActionPerformedOver() {
    init1();
    controller4.actionPerformed("P");
    controller3.actionPerformed("P");
    Assert.assertTrue(board.gameOver());

  }

  @Test
  public void testActionPerformedOver2() {
    initSquare1();
    controller4.actionPerformed("P");
    controller3.actionPerformed("P");
    Assert.assertTrue(board.gameOver());
  }

  @Test
  public void testActionPerformedWithMock() {
    init2();
    player1.tryToSkip();
    Assert.assertEquals("Rest", this.appendable1.toString());
  }

  @Test
  public void testActionPerformedWithMock2() {
    initSquare2();
    player1.tryToSkip();
    Assert.assertEquals("Rest", this.appendable1.toString());
  }

  @Test
  public void testOnSkipRoundWithMock() {
    init3();
    controller4.onSkipRound();
    Assert.assertTrue(this.appendable1.toString().contains("takeAction"));

  }

  @Test
  public void testOnSkipRoundWithMock2() {
    initSquare3();
    controller4.onSkipRound();
    Assert.assertTrue(this.appendable1.toString().contains("takeAction"));

  }

  @Test
  public void testOnGameOverWithMock() {
    init2();
    controller1.onGameOver();
    Assert.assertEquals("GameOver", this.appendable1.toString());
  }

  @Test
  public void testOnGameOverWithMock2() {
    initSquare2();
    controller1.onGameOver();
    Assert.assertEquals("GameOver", this.appendable1.toString());
  }


  // test if player transmit correct message to the controller.
  @Test
  public void testOnPlaceDiscWithMock() {
    init2();
    player2.tryToPlace("coordinate");
    Assert.assertTrue(this.appendable2.toString().contains("coordinate"));
  }

  // test if player transmit correct message to the controller.
  @Test
  public void testOnPlaceDiscWithMock2() {
    initSquare2();
    player2.tryToPlace("coordinate");
    Assert.assertTrue(this.appendable2.toString().contains("coordinate"));
  }
}
