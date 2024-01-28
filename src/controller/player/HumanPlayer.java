package controller.player;

import java.util.ArrayList;
import java.util.List;

import model.DiscType;
import model.ReadonlyReversiModel;

/**
 * This class represents a human player in the game of Reversi.
 * It holds the disc type associated with the player and interacts with the game model.
 * The class is responsible for notifying action listeners when the player makes a move
 * or chooses to skip a turn.
 */
public class HumanPlayer implements IPlayer {

  // the command coming from player
  private String command;

  // represent the disc current player is using
  protected final DiscType playerDisc;

  private final ReadonlyReversiModel model;

  // listeners that need to informed for player action
  private final List<PlayerActionListener> listeners = new ArrayList<>();

  /**
   * a human player need the disc type and the model to be played.
   *
   * @param playerDisc the disc type to be placed.
   * @param model      a readonlyModel in case the player made any mutation in the player class.
   */
  public HumanPlayer(DiscType playerDisc, ReadonlyReversiModel model) {
    this.playerDisc = playerDisc;
    this.model = model;

    //model.addListener(this);
  }

  /**
   * Notifies all registered action listeners of the player's action.
   */
  @Override
  public void notifyListeners() {
    for (PlayerActionListener pl : listeners) {
      pl.actionPerformed(command);
    }
  }

  /**
   * Adds an action listener to be notified of the player's actions.
   *
   * @param listener the PlayerActionListener to add.
   */
  @Override
  public void addListener(PlayerActionListener listener) {
    this.listeners.add(listener);
  }

  /**
   * Gets the disc type of the player.
   *
   * @return the disc type used by the player.
   */
  @Override
  public DiscType getPlayerType() {
    return playerDisc;
  }

  /**
   * Takes an action in the game. This method is used to prompt the player to make a move.
   * For a human player, this typically does not perform any action as the player's moves
   * are determined by user input rather than programmatically.
   */
  @Override
  public void takeAction() {
    // don't need to notify the human player
  }


  /**
   * Attempts to place a disc on the board at a specified location.
   * If it is not the player's turn, a "Rest" command is recorded.
   * Otherwise, the specified command is recorded.
   *
   * @param command the command representing the location where the disc is to be placed.
   */
  @Override
  public void tryToPlace(String command) {
    if (!model.getTurns().equals(playerDisc)) {
      this.command = "Rest";
    } else {
      this.command = command;
    }
    notifyListeners();
  }

  /**
   * Attempts to skip the player's turn. If it is not the player's turn, a "Rest" is recorded.
   * Otherwise, a "P" command is recorded to indicate a pass.
   */
  @Override
  public void tryToSkip() {
    if (!model.getTurns().equals(playerDisc)) {
      this.command = "Rest";
    } else {
      this.command = "P";
    }
    notifyListeners();
  }
}