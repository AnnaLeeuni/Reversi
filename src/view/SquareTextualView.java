package view;

import model.DiscType;
import model.ReadonlyReversiModel;
import view.textualview.TextualView;

/**
 * render the square board as in string.
 */
public class SquareTextualView implements TextualView {

  /**
   * The board to be rendered.
   */
  private final ReadonlyReversiModel model;

  /**
   * Constructs a textual view of the given board.
   *
   * @param board the board to be rendered
   */
  public SquareTextualView(ReadonlyReversiModel board) {
    this.model = board;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    // Adding board rows
    for (int x = 0; x < model.getPile().size(); x++) {
      for (int y = 0; y < model.getPile().get(0).size(); y++) {
        DiscType disc = model.getDisc(x, y);
        sb.append(disc.toString()).append(" ");
      }
      sb.append("\n");
    }

    return sb.toString();
  }
}
