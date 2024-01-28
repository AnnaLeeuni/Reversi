package controller.player;

import java.io.IOException;

import model.DiscType;


/**
 * The MockHumanPlayer class extends HumanPlayer to mock the behavior of a human player for testing.
 * It overrides the methods of HumanPlayer to record actions to an Appendable object.
 */
public class MockPlayer implements IPlayer {

  private final Appendable out;
  private final DiscType player;

  /**
   * the default constructor of a mock player.
   *  with the specified appendable for logging.
   */
  public MockPlayer(Appendable out) {
    this.out = out;
    this.player = DiscType.BLACK;
  }

  /**
   * Constructs a MockHumanPlayer with a customized player type.
   */
  public MockPlayer(Appendable out, DiscType player) {
    this.out = out;
    this.player = player;
  }

  /**
   * Mocks notifying listeners of an action.
   */
  @Override
  public void notifyListeners() {
    try {
      out.append("notifyListeners \n");
    } catch (IOException ignored) {
    }
  }

  /**
   * Mocks adding a listener.
   *
   * @param listener The listener to be added.
   */
  @Override
  public void addListener(PlayerActionListener listener) {
    try {
      out.append("addListener \n");
    } catch (IOException ignored) {
    }
  }

  /**
   * Mocks getting the player's disc type.
   *
   */
  @Override
  public DiscType getPlayerType() {
    try {
      out.append("getPlayerType \n");
    } catch (IOException ignored) {
    }
    return player;
  }


  /**
   * Mocks taking an action in the game.
   */
  @Override
  public void takeAction() {

    try {
      out.append(" takeAction \n");
    } catch (IOException ignored) {
    }
  }

  /**
   * Mocks attempting to place a disc at a location.
   *
   * @param command The command representing the location where the disc is to be placed.
   */
  @Override
  public void tryToPlace(String command) {
    try {
      out.append(" tryToPlace \n");
    } catch (IOException ignored) {
    }
  }

  /**
   * Mocks attempting to skip the player's turn.
   */
  @Override
  public void tryToSkip() {
    try {
      out.append(" tryToSkip \n");
    } catch (IOException ignored) {
    }
  }

}
