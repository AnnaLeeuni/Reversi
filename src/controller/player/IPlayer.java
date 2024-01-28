package controller.player;

import model.DiscType;
import view.PlayerFeatures;

/**
 * Represents the interface for a player in a game. This interface extends PlayerFeatures
 * and includes additional functionalities specific to a player, such as notifying listeners
 * of actions and managing the player's turn.
 * IPlayer serves as a bridge between the controller and view, facilitating communication
 * and ensuring the appropriate response to player actions within the game's logic.
 */
public interface IPlayer extends PlayerFeatures {

  /**
   * Notifies all registered listeners of a player action. This method is typically called
   * when a player makes a move or takes some action that affects the game state.
   * The listeners, usually instances of the controller, are then responsible for processing
   * these actions and updating the game model and view as necessary.
   */
  public void notifyListeners();

  /**
   * Registers a new listener to receive notifications of player actions. This is important
   * for linking the player's actions to the game's response mechanisms, such as updating
   * the model or the view.
   *
   * @param listener The listener (usually a controller) that will respond to player actions.
   */
  public void addListener(PlayerActionListener listener);

  /**
   * Retrieves the type of disc associated with the player, either black or white.
   * This information is crucial for determining the player's moves and interactions
   * within the game's rules.
   *
   * @return The type of disc (black or white) that the player is using.
   */
  public DiscType getPlayerType();

  /**
   * Instructs the player to take an action. This could involve making a move, passing
   * a turn, or any other action defined within the game's rules. The implementation
   * of this method should facilitate the player's interaction with the game at the
   * appropriate time, especially in a turn-based game like Reversi.
   */
  public void takeAction();


}
