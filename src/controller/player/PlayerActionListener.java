package controller.player;


/**
 * Define an interface that encapsulates actions a player can perform, such as selecting
 * a move or passing their turn.
 * This interface should be implemented by both human and AI players.
 */
public interface PlayerActionListener {

  /**
   * Responds to an action performed by a player. This method should encapsulate the
   * logic for handling different types of actions such as making a move, passing a turn,
   * or any other interaction within the game.
   *
   * @param event A string representation of the player's action. This could be coordinates
   *              for a move, a command to pass the turn, or other game-specific commands.
   */
  void actionPerformed(String event);
}
