package model;



/**
 * Represents a specific location on the board,
 * identified by its row number and its index within that row.
 *
 * <p>This class provides a way to encapsulate the coordinates of a location on the board, ensuring
 * clarity when working with positions on the game board.</p>
 */
public class BoardLocation {

  /** The row number on the board. */
  private final int numberRow;

  /** The index within the specified row. */
  private final int index;

  /**
   * Initializes a new BoardLocation with the specified row number and index within that row.
   *
   * @param numberRow the row number on the board
   * @param index the index within the specified row
   */
  public BoardLocation(int numberRow, int index) {
    this.numberRow = numberRow;
    this.index = index;
  }

  /**
   * Retrieves the row number of this BoardLocation.
   *
   * @return the row number stored in this BoardLocation
   */
  public int getRow() {
    return numberRow;
  }

  /**
   * Retrieves the index within the row of this BoardLocation.
   *
   * @return the index stored in this BoardLocation
   */
  public int getIndex() {
    return index;
  }

  @Override
  public String toString() {
    return "x = " + this.numberRow + ", y = " + this.index;
  }

}
