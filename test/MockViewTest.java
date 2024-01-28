
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controller.BoardController;
import model.BasicBoard;
import model.BoardWithNotifier;
import model.DiscType;
import model.SquareReversiModel;
import view.MockView;
import controller.player.IPlayer;
import controller.player.HumanPlayer;

/**
 * tests using the mockView.
 */
public class MockViewTest {

  BoardWithNotifier board;
  BoardController controller1;
  BoardController controller2;
  IPlayer player2;
  IPlayer player1;
  MockView viewPlayer1;
  MockView viewPlayer2;

  Appendable appendable1;
  Appendable appendable2;
  Appendable appendableView1;
  Appendable appendableView2;


  @Before
  public void init1() {

    BasicBoard basic = new BasicBoard(7);
    BoardWithNotifier board = new BoardWithNotifier(basic);
    appendableView1 = new StringBuilder();
    appendableView2 = new StringBuilder();

    viewPlayer1 = new MockView(appendableView1, board);
    viewPlayer2 = new MockView(appendableView2, board);
    player2 = new HumanPlayer(DiscType.BLACK, board);
    player1 = new HumanPlayer(DiscType.WHITE, board);
    appendable1 = new StringBuilder();
    appendable2 = new StringBuilder();
    controller1 = new BoardController(player1, board, viewPlayer1);
    controller2 = new BoardController(player2, board, viewPlayer2);

    board.startGame();
  }

  @Before
  public void initSquare() {

    SquareReversiModel basic = new SquareReversiModel(8);
    BoardWithNotifier board = new BoardWithNotifier(basic);
    appendableView1 = new StringBuilder();
    appendableView2 = new StringBuilder();

    viewPlayer1 = new MockView(appendableView1, board);
    viewPlayer2 = new MockView(appendableView2, board);
    player2 = new HumanPlayer(DiscType.BLACK, board);
    player1 = new HumanPlayer(DiscType.WHITE, board);
    appendable1 = new StringBuilder();
    appendable2 = new StringBuilder();
    controller1 = new BoardController(player1, board, viewPlayer1);
    controller2 = new BoardController(player2, board, viewPlayer2);

    board.startGame();
  }


  // can only update title for the player on the current controller
  @Test
  public void testUpdateTitleOnlyCalledOnCurrentTurn() {
    init1();
    // see if skip round actually triggers updateTitle
    controller2.onPlaceDisc();
    System.out.println(appendableView2);
    System.out.println(appendableView1);

    // only updates the title when it is the player's turn
    Assert.assertFalse(appendableView1.toString().contains("updateTitle"));
    Assert.assertTrue(appendableView2.toString().contains("updateTitle"));
  }

  @Test
  public void testUpdateTitleOnlyCalledOnCurrentTurn2() {
    initSquare();
    // see if skip round actually triggers updateTitle
    controller2.onPlaceDisc();
    System.out.println(appendableView2);
    System.out.println(appendableView1);

    // only updates the title when it is the player's turn
    Assert.assertFalse(appendableView1.toString().contains("updateTitle"));
    Assert.assertTrue(appendableView2.toString().contains("updateTitle"));
  }

  @Test
  public void testUpdateTitle() {
    init1();
    // see if skip round actually triggers updateTitle
    controller2.onPlaceDisc();
    System.out.println(appendableView2);
    System.out.println(appendableView1);

    // only updates the title when it is the player's turn
    Assert.assertFalse(appendableView1.toString().contains("updateTitle"));
    Assert.assertTrue(appendableView2.toString().contains("updateTitle"));
  }

  @Test
  public void testUpdateTitle2() {
    initSquare();
    // see if skip round actually triggers updateTitle
    controller2.onPlaceDisc();
    System.out.println(appendableView2);
    System.out.println(appendableView1);

    // only updates the title when it is the player's turn
    Assert.assertFalse(appendableView1.toString().contains("updateTitle"));
    Assert.assertTrue(appendableView2.toString().contains("updateTitle"));
  }


  @Test
  public void testInitiateTitle() {
    init1();
    System.out.println(appendableView1);
    // see if skip round actually triggers updateTitle
    Assert.assertTrue(appendableView1.toString().contains("initiateTitle"));

  }

  @Test
  public void testInitiateTitle2() {
    initSquare();
    System.out.println(appendableView1);
    // see if skip round actually triggers updateTitle
    Assert.assertTrue(appendableView1.toString().contains("initiateTitle"));

  }

  @Test
  public void testRepaintView() {
    init1();
    // see if skip round actually triggers repaintView.
    controller1.onSkipRound();
    Assert.assertTrue(appendableView1.toString().contains("repaintView"));

  }

  @Test
  public void testRepaintView2() {
    initSquare();
    // see if skip round actually triggers repaintView.
    controller1.onSkipRound();
    Assert.assertTrue(appendableView1.toString().contains("repaintView"));

  }

  @Test
  public void testMouseReleased() {
    init1();
    // see if skip round actually triggers repaintView.
    controller1.onSkipRound();
    Assert.assertTrue(appendableView1.toString().contains("repaintView"));

  }

  @Test
  public void testMouseReleased2() {
    initSquare();
    // see if skip round actually triggers repaintView.
    controller1.onSkipRound();
    Assert.assertTrue(appendableView1.toString().contains("repaintView"));

  }

  @Test
  public void testGameOverMessage() {
    init1();
    viewPlayer1.gameOverMessage();
    Assert.assertTrue(appendableView1.toString().contains("gameOverMessage"));
  }

  @Test
  public void testGameOverMessage2() {
    initSquare();
    viewPlayer1.gameOverMessage();
    Assert.assertTrue(appendableView1.toString().contains("gameOverMessage"));
  }

  @Test
  public void testSetVisible() {
    init1();
    viewPlayer1.setVisible(true);
    Assert.assertTrue(appendableView1.toString().contains("setVisible"));
  }

  @Test
  public void testSetVisible2() {
    initSquare();
    viewPlayer1.setVisible(true);
    Assert.assertTrue(appendableView1.toString().contains("setVisible"));
  }

  @Test
  public void testErrorMessage() {
    init1();
    viewPlayer1.errorMessage();
    Assert.assertTrue(appendableView1.toString().contains("errorMessage"));
  }

  @Test
  public void testErrorMessage2() {
    initSquare();
    viewPlayer1.errorMessage();
    Assert.assertTrue(appendableView1.toString().contains("errorMessage"));
  }

}
