package model;

/**
 * Create an interface that allows the model to notify controllers about significant game events,
 * such as turn changes or game completion.
 */
public interface ModelStatusListener {

  /**
   * Invoked when a player decides to skip their turn. Implementing classes should define
   * how the game model and view are updated in response to a turn being skipped.
   */
  public void onSkipRound();

  /**
   * Called when the game is over. This method should handle any necessary cleanup,
   * display a game over message, or perform other actions required to conclude the game.
   */

  public void onGameOver();

  /**
   * Triggered at the start of the game. This method can be used to set up initial game state,
   * update views, or perform other actions needed to begin the game.
   */

  public void onGameStart();

  /**
   * Invoked when a disc is placed on the board. This method should handle the logic
   * for updating the game state in response to the new placement, including updating
   * the model and view, and possibly checking for win conditions or turn changes.
   */

  public void onPlaceDisc();
}