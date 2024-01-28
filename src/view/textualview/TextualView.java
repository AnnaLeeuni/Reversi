package view.textualview;

/**
 * Represents a textual view of a board or any other object.
 * Implementing classes should provide a string representation of the object.
 */
public interface TextualView {

  /**
   * Returns the string representation of the object implementing this interface.
   *
   * @return The string representation.
   */
  @Override
  public String toString();

}