package controller.player;

import java.util.ArrayList;
import java.util.List;

import controller.strategy.IStrategy;
import model.BoardLocation;
import model.DiscType;
import model.ReadonlyReversiModel;

/**
 * The MachinePlayer class represents an AI player in the game of Reversi.
 * This player uses a strategy to determine its moves and notifies listeners about its actions.
 * It implements the IPlayer interface and can respond to changes in the game model.
 */
public class MachinePlayer implements IPlayer {

  // The strategy used by the AI to make moves
  private final IStrategy strategy;
  private final List<PlayerActionListener> actionListeners = new ArrayList<>();

  private final DiscType playerType;

  private final ReadonlyReversiModel model;

  /**
   * Constructs a MachinePlayer with the specified disc type, strategy, and game model.
   *
   * @param playerType The type of disc the player uses.
   * @param strategy   The strategy used by the player to make moves.
   * @param model      The game model that provides the state of the game.
   */
  public MachinePlayer(DiscType playerType, IStrategy strategy, ReadonlyReversiModel model) {
    this.strategy = strategy;
    this.playerType = playerType;
    this.model = model;

  }


  /**
   * Notifies all registered action listeners of the player's action.
   * If the player is unable to determine a move or the game is over, a "P" or "GameOver"
   * command is sent respectively. Otherwise, it sends the determined move.
   */
  @Override
  public void notifyListeners() {
    boolean exceptionThrown = false;

    try {
      strategy.getPosition(model);
    } catch (IllegalStateException e) {

      exceptionThrown = true;
    }

    if (exceptionThrown) {
      for (PlayerActionListener pl : actionListeners) {
        pl.actionPerformed("P");
      }

    } else if (model.gameOver()) {
      for (PlayerActionListener pl : actionListeners) {
        pl.actionPerformed("GameOver");
      }
    } else {
      for (PlayerActionListener pl : actionListeners) {
        BoardLocation bl = strategy.getPosition(model);
        pl.actionPerformed(bl.getRow() + " " + bl.getIndex());
      }
    }
  }

  /**
   * Adds an action listener to be notified of the player's actions.
   *
   * @param listener The PlayerActionListener to add.
   */
  @Override
  public void addListener(PlayerActionListener listener) {
    this.actionListeners.add(listener);
  }


  /**
   * Gets the disc type of the player.
   *
   * @return The disc type used by the player.
   */
  @Override
  public DiscType getPlayerType() {
    return playerType;
  }

  /**
   * Takes an action in the game. This method is used by the AI to decide on its next move.
   */
  @Override
  public void takeAction() {
    boolean record = false;
    boolean gameOver = this.model.gameOver();
    try {
      record = this.model.getTurns().toString().equals(playerType.toString());
    } catch (IllegalStateException e) {
      System.err.println("Exception in takeAction: " + e.getMessage());
    }
    //System.out.println(gameOver);
    if (record && !gameOver) {
      notifyListeners();
    }
  }

  /**
   * AI's moves are determined by its strategy.
   * @param event The command representing the location where the disc is to be placed.
   */
  @Override
  public void tryToPlace(String event) {
    // AI player don't need to respond to the action from view.
  }

  /**
   * AI's moves are determined by its strategy.
   */
  @Override
  public void tryToSkip() {
    // AI player don't need to respond to the action from view.
  }

}

