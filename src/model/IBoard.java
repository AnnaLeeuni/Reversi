package model;


/**
 * Represents the interface for game boards. This interface provides methods
 * for manipulating the game board, querying its state, and performing game operations.
 */
public interface IBoard extends ReadonlyReversiModel {

  /**
   * If the user chooses to skip their turn, this method switches the active turn
   * to the opponent's disc type.
   */
  public void skipRound();

  /**
   * Places a disc at the specified board location.
   *
   * @param x the x-coordinate where the disc should be placed
   * @param y the y-coordinate where the disc should be placed
   */
  public void placeDisc(int x, int y);


  /**
   * set the current model as the mutable version of the given model.
   */
  public IBoard makeBoard(ReadonlyReversiModel model);
}
