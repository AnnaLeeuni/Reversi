package controller;

import java.io.IOException;

import controller.player.IPlayer;
import controller.player.PlayerActionListener;
import model.BoardWithNotifier;
import model.ModelStatusListener;
import view.ViewBoard;

/**
 * MockController extends the functionality of BoardController for testing purposes.
 * It overrides the action methods to record commands and game events to an Appendable object,
 * allowing verification of interactions during tests.
 */
public class MockController implements IController, ModelStatusListener, PlayerActionListener {

  private final Appendable out;

  /**
   * Constructs a MockController object which can be used to simulate controller actions
   * and record the results into an Appendable for verification.
   *
   * @param out    An Appendable object to gather the commands and output from the controller.
   * @param player The player associated with this controller.
   * @param model  The game model.
   * @param view   The game view.
   */
  public MockController(Appendable out, IPlayer player, BoardWithNotifier model, ViewBoard view) {

    this.out = out;
    player.addListener(this);
  }

  /**
   * Records the given command into the appendable object.
   *
   * @param command The command to be recorded.
   */
  @Override
  public void actionPerformed(String command) {
    try {
      out.append(command);
    } catch (IOException ignored) {
    }
  }

  /**
   * Records the skip round action into the appendable object.
   */
  @Override
  public void onSkipRound() {
    try {
      out.append("Skip Called");
    } catch (IOException ignored) {
    }
  }


  /**
   * Records the game over event into the appendable object.
   */
  @Override
  public void onGameOver() {
    try {
      out.append("GameOver");
    } catch (IOException ignored) {
    }
  }

  /**
   * Records the game start event into the appendable object.
   */
  @Override
  public void onGameStart() {
    try {
      out.append("Game is starting now");
    } catch (IOException ignored) {
    }
  }

  /**
   * Records the place disc action into the appendable object.
   */
  @Override
  public void onPlaceDisc() {
    try {
      out.append("Some one just placed a Disc");
    } catch (IOException ignored) {
    }
  }

  @Override
  public void moveDisc(int rowNumber, int rowIndex) {
    //Mock controller do not have actual action.

  }

  @Override
  public void skipRound() {
    //Mock controller do not have actual action.
  }
}
