package controller;

/**
 * represent an interface for controller.
 */
public interface IController {

  /**
   * Places a disc at the specified location on the game board.
   * This method updates the game model by placing a disc for the current player at the given
   * coordinates.
   * It is called in response to a player's action and ensures that the game state is updated
   * accordingly.
   *
   * @param rowNumber The row number where the disc is to be placed, 0-based.
   * @param rowIndex  The column index where the disc is to be placed, 0-based.
   * @throws IllegalStateException If the move is illegal or if it's not the current player's turn.
   */
  public void moveDisc(int rowNumber, int rowIndex);

  /**
   * Instructs the game model to skip the current player's turn.
   * This method is invoked when a player decides to pass their turn, usually because they have
   * no legal moves available.
   * It updates the game model to reflect the skipped turn and moves to the next player.
   *
   * @throws IllegalStateException If it's not the current player's turn or if the game state
   *                               doesn't allow skipping a turn.
   */
  public void skipRound();

}
