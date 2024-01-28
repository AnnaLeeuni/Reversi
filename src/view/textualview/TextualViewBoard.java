package view.textualview;

import java.util.List;

import model.DiscType;
import model.ReadonlyReversiModel;

/**
 * This class renders the board into a string representation.
 */
public class TextualViewBoard implements TextualView {

  /**
   * The board to be rendered.
   */
  private final ReadonlyReversiModel board;

  /**
   * Constructs a textual view of the given board.
   *
   * @param board the board to be rendered
   */
  public TextualViewBoard(ReadonlyReversiModel board) {
    this.board = board;
  }

  /**
   * Converts the board into a string representation.
   *
   * @return the string representation of the board
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();

    List<List<DiscType>> pile = this.board.getPile();
    int height = this.board.getHeight();

    int maxDiscs = determineMaxDiscs(pile);
    int midpoint = height / 2;

    for (int row = 0; row < height; row++) {
      appendLeadingSpacesForRow(result, row, midpoint);
      appendDiscsForRow(result, pile.get(row));
      result.append("\n");
    }

    return removeTrailingWhitespaces(result.toString());
  }

  /**
   * Determines the maximum number of discs across all rows of the board.
   *
   * @param pile The 2D list representing the board's rows and their discs.
   * @return The maximum number of discs present in any row.
   */
  private int determineMaxDiscs(List<List<DiscType>> pile) {
    int maxDiscs = 0;
    for (List<DiscType> rowPile : pile) {
      if (rowPile.size() > maxDiscs) {
        maxDiscs = rowPile.size();
      }
    }
    return maxDiscs;
  }

  /**
   * Appends the leading spaces for a specific row to the result StringBuilder.
   *
   * @param result   The StringBuilder to which the spaces will be appended.
   * @param row      The current row number being processed.
   * @param midpoint The vertical midpoint of the diamond board representation.
   */
  private void appendLeadingSpacesForRow(StringBuilder result, int row, int midpoint) {
    int leadingSpaces = (row <= midpoint) ? 0 : row - midpoint;
    result.append(" ".repeat(Math.max(0, leadingSpaces)));
  }

  /**
   * Appends the discs for a specific row to the result StringBuilder.
   *
   * @param result  The StringBuilder to which the discs will be appended.
   * @param rowPile The list of discs for the current row.
   */
  private void appendDiscsForRow(StringBuilder result, List<DiscType> rowPile) {
    for (DiscType targetDisc : rowPile) {
      if (targetDisc == null) {
        result.append(" ");
      } else {
        result.append(targetDisc).append(" ");
      }
    }
  }


  /**
   * Remove trailing whitespaces.
   * @param input String
   * @return String
   */
  private static String removeTrailingWhitespaces(String input) {
    return input.replaceAll("[ \t]+(?=\n)", "");
  }

}