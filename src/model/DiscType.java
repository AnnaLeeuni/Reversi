package model;


/**
 * Enumerates the different types of discs used in the game.
 *
 * <p>This enumeration represents the three possible states for a position on the game board:
 * either it contains a BLACK disc, a WHITE disc, or it is EMPTY.</p>
 */
public enum DiscType implements IDiscType {

  /** Represents a black disc. */
  BLACK,

  /** Represents a white disc. */
  WHITE,

  /** Represents an empty spot on the board. */
  EMPTY;

  /**
   * Provides a string representation for the DiscType,
   * suitable for displaying in a textual view of the game.
   *
   * <ul>
   *   <li>BLACK disc is represented by the string "x"</li>
   *   <li>WHITE disc is represented by the string "o"</li>
   *   <li>EMPTY spot is represented by the string "_"</li>
   * </ul>
   *
   * @return a string representation of the DiscType
   * @throws AssertionError if the DiscType value is not recognized
   */
  @Override
  public String toString() {
    switch (this) {
      case BLACK:
        return "x";
      case WHITE:
        return "o";
      case EMPTY:
        return "_";
      default:
        throw new AssertionError("Unknown enum value: " + this);
    }
  }

  /**
   * convert the current disc type as word.
   */
  @Override
  public String toWord() {
    switch (this) {
      case BLACK:
        return "Black";
      case WHITE:
        return "White";
      case EMPTY:
        return "Empty";
      default:
        throw new AssertionError("Unknown enum value: " + this);
    }
  }

}
