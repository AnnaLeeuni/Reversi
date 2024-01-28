package model;

import java.util.List;

/**
 * the interface of reversiModel that the user is unable to mutate the board.
 */
public interface ReadonlyReversiModel {

  /**
   * Determines if the game has reached a terminal state.
   *
   * @return true if the game is over, false otherwise
   */
  public boolean gameOver();

  /**
   * Retrieves the current configuration of the game board.
   *
   * @return a list of lists representing the board's rows and columns with the discs placed
   */
  public List<List<DiscType>> getPile();

  /**
   * Gets the maximum width of the board.
   *
   * @return the width of the board
   */
  public int getWidth();

  /**
   * Gets the height of the board.
   *
   * @return the height of the board
   */
  public int getHeight();

  /**
   * Retrieves the type of disc at the specified location on the board.
   *
   * @param x the x-coordinate of the location
   * @param y the y-coordinate of the location
   * @return the type of disc at the given location
   */
  public DiscType getDisc(int x, int y);

  /**
   * Determines the winner of the game.
   *
   * <p>If the game has not ended yet, this method throws an IllegalStateException.</p>
   *
   * @return the type of disc that represents the winner, or null if there's a draw
   * @throws IllegalStateException if the game has not ended
   */
  public DiscType getWinner();

  /**
   * Retrieves the disc type of the player whose turn it currently is.
   *
   * @return the disc type of the current player
   */
  public DiscType getTurns();

  /**
   * determine if the location is valid to move for the current player.
   *
   * @param row   the number of row
   * @param index the index at that row
   * @return true if the location is allowed to place
   */
  public boolean legalMove(int row, int index);

  /**
   * determine if the current player have any legal move to make.
   *
   * @return true if there is any.
   */
  public boolean anyLegalMove();


  /**
   * determine if player with the disc type has any place to move.
   */
  public boolean anyLegalMove(DiscType player);

  /**
   * get all the possible location as list for the given player.
   */
  public List<BoardLocation> legalMoves();

  /**
   * get the current score of the given player.
   *
   * @return the score of the given player
   */
  public int getScore(DiscType player);

  public void startGame();


  /**
   * get the corners on the board.
   */
  public List<BoardLocation> getCorners();

  /**
   * get a list of positions that is nearby the corner.
   * @return the list of positions as in list of board location.
   */
  public List<BoardLocation> getCornersNearBy();

  /**
   * determine the current gameMode.
   *
   * @return Hexagon if playing iin
   */
  public IBoard getMutableVersion();
}
