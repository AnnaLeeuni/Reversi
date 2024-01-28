import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.event.KeyEvent;

import controller.strategy.AIEasyStrategy;
import controller.BoardController;
import controller.IController;
import controller.player.IPlayer;
import controller.strategy.IStrategy;
import controller.player.MockPlayer;
import model.BoardWithNotifier;
import model.DiscType;
import model.SquareReversiModel;
import view.ViewBoard;

/**
 * Test class to test method in both human and AI Player using their mocks.
 */
public class TestMockPlayer {
  private BoardWithNotifier board;
  private BoardController controller2;

  private Appendable appendable1;
  private Appendable appendable2;

  private IPlayer player2;

  private ViewBoard viewPlayer1;
  private ViewBoard viewPlayer2;

  @Before
  public void init() {
    SquareReversiModel basic = new SquareReversiModel(6);
    board = new BoardWithNotifier(basic);
    viewPlayer1 = new ViewBoard(board);
    viewPlayer2 = new ViewBoard(board);
    appendable1 = new StringBuilder();
    appendable2 = new StringBuilder();

    player2 = new MockPlayer(appendable1);
    IPlayer player1 = new MockPlayer(appendable2, DiscType.WHITE);
    BoardController controller1 = new BoardController(player1, board, viewPlayer1);
    controller2 = new BoardController(player2, board, viewPlayer2);

    board.startGame();
  }


  // when gameStart is called, player should be informed to take action
  @Test
  public void testModelMutatedInformPlayer() {
    init();
    Assert.assertTrue(appendable1.toString().contains("takeAction"));
  }

  // don't inform the player if it is not their turn.
  @Test
  public void testModelMutatedDontInformPlayer() {
    init();

    Assert.assertFalse(appendable2.toString().contains("takeAction"));
  }

  // skip the round, player with white disc should be notified to take action
  @Test
  public void testModelMutatedInformPlayer2() {
    init();
    board.skipRound();
    Assert.assertTrue(appendable2.toString().contains("takeAction"));
  }


  @Test
  public void testAddListenerBeenCalledInTheController() {

    SquareReversiModel basic = new SquareReversiModel(6);
    BoardWithNotifier board = new BoardWithNotifier(basic);
    ViewBoard viewPlayer1 = new ViewBoard(board);
    ViewBoard viewPlayer2 = new ViewBoard(board);
    Appendable appendable1 = new StringBuilder();
    Appendable appendable2 = new StringBuilder();
    IStrategy aiStrategy = new AIEasyStrategy();
    IPlayer aiPlayer2 = new MockPlayer(appendable1);
    IPlayer aiPlayer1 = new MockPlayer(appendable2);
    IController controller1 = new BoardController(aiPlayer1, board, viewPlayer1);
    IController controller2 = new BoardController(aiPlayer2, board, viewPlayer2);

    board.startGame();

    // should add the player as listener in the constructor
    Assert.assertTrue(appendable1.toString().contains("addListener"));
    Assert.assertTrue(appendable2.toString().contains("addListener"));

  }

  // if the user typed "p" in view that is waiting for command.
  @Test
  public void testRespondToViewEvent() {
    init();
    Assert.assertTrue(appendable1.toString().contains("takeAction"));
    KeyEvent key = new KeyEvent(viewPlayer1, KeyEvent.KEY_RELEASED, System.currentTimeMillis(),
            0, KeyEvent.VK_P, 'p');
    viewPlayer1.keyReleased(key);

    // if playerA has received right command from view.
    Assert.assertTrue(appendable2.toString().contains("tryToSkip"));
    // if successfully skipped the round
    Assert.assertTrue(appendable1.toString().contains("takeAction"));
  }


  // if the user typed "p" in view that is not active.
  // should not report to the controller.
  @Test
  public void testRespondToViewEvent2() {
    init();
    Assert.assertTrue(appendable1.toString().contains("takeAction"));
    KeyEvent key = new KeyEvent(viewPlayer2, KeyEvent.KEY_RELEASED, System.currentTimeMillis(),
            0, KeyEvent.VK_P, 'p');
    viewPlayer2.keyReleased(key);

    // if playerA has received right command from view.
    Assert.assertFalse(appendable2.toString().contains("tryToSkip"));
    // if successfully skipped the round
    Assert.assertTrue(appendable1.toString().contains("tryToSkip"));
    Assert.assertTrue(appendable1.toString().contains("tryToSkip"));
  }

  @Test
  public void testGetPlayerType() {
    init();
    Assert.assertTrue(appendable1.toString().contains("getPlayerType"));
  }

  @Test
  public void testTakeAction() {
    init();
    player2.takeAction();
    Assert.assertTrue(appendable1.toString().contains("takeAction"));
  }


  @Test
  public void testTryToPlace() {
    init();
    player2.tryToPlace("tryToPlace");
    Assert.assertTrue(appendable1.toString().contains("tryToPlace"));

  }

  @Test
  public void testTryToSkip() {
    init();
    player2.tryToSkip();
    Assert.assertTrue(appendable1.toString().contains("tryToSkip"));

  }

  @Test
  public void testNotifyListenersAI() {
    init();
    player2.notifyListeners();
    Assert.assertTrue(appendable1.toString().contains("notifyListeners"));

  }

  @Test
  public void testAddListenerAI() {
    init();
    player2.addListener(controller2);
    Assert.assertTrue(appendable1.toString().contains("addListener"));

  }

  @Test
  public void testGetPlayerTypeAI() {
    init();
    Assert.assertTrue(appendable1.toString().contains("getPlayerType"));

  }

  @Test
  public void testTakeActionAI() {
    init();
    player2.takeAction();
    Assert.assertTrue(appendable1.toString().contains("takeAction"));

  }

  @Test
  public void testTryToPlaceAI() {
    init();
    player2.tryToPlace("tryToPlace");
    Assert.assertTrue(appendable1.toString().contains("tryToPlace"));

  }

  @Test
  public void testTryToSkipAI() {
    init();
    player2.tryToSkip();
    Assert.assertTrue(appendable1.toString().contains("tryToSkip"));
  }

}
