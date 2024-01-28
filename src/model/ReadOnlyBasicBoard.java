package model;

/**
 * Represents a read-only version of a BasicBoard for the Reversi game. This class is designed to
 * provide a view of the game state without allowing modifications. Any attempts to use setter
 * methods will result in a RuntimeException.
 */
public class ReadOnlyBasicBoard extends BasicBoard implements ReadonlyReversiModel {

  /**
   * Constructs a new ReadOnlyBasicBoard with default settings.
   */
  public ReadOnlyBasicBoard() {

    super();
    gameStarted = true;
  }

  /**
   * Constructs a ReadOnlyBasicBoard based on an existing IBoard instance.
   * Initializes the board state to match the provided model.
   *
   * @param model The IBoard instance whose state is to be copied.
   */
  public ReadOnlyBasicBoard(IBoard model) {
    super(model.getPile());

    this.turns = model.getTurns();
    gameStarted = true;
  }

  /**
   * Unsupported operation in ReadOnlyBasicBoard. Attempting to call this method will
   * result in a RuntimeException.
   *
   * @throws RuntimeException Always thrown to indicate that this operation is not supported.
   */
  @Override
  public void skipRound() {
    throw new RuntimeException("do not support the current method");
  }

  /**
   * Unsupported operation in ReadOnlyBasicBoard. Attempting to call this method will
   * result in a RuntimeException.
   *
   * @throws RuntimeException Always thrown to indicate that this operation is not supported.
   */
  @Override
  public void placeDisc(int x, int y) {
    throw new RuntimeException("do not support the current method");
  }




}
