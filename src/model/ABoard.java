package model;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class representing a game board.
 * This class provides a basic structure for a board and its configurations.
 */
public abstract class ABoard implements IBoard {

  /**
   * Represents the width in the center of the board.
   * It is the maximum number of Disc space of the most center row
   * Width should be at least four.
   */
  protected int width;

  /**
   * Represents the height of the board.
   * Which is the number of row in the board, or the number of list in the pile.
   * Should be at least four.
   * The height should always satisfy the condition "max height=2×width−1".
   */
  protected int height;

  /**
   * Each row is represented as a list of DiscType.
   * A pile is represented as a list of rows.
   * INVARIANT(1): the size of the pile should be the same as the height of the board.
   * INVARIANT(2): the size of each row should be the same as the width of the board.
   */
  protected List<List<DiscType>> pile;

  /**
   * Indicates whose turn it is to play, either Black or White.
   * INVARIANT: Should either be Black or White.
   */
  protected DiscType turns;

  /**
   * Represents whether the game is over or not.
   */
  protected boolean gameOver;

  // to represent the winner of the game.
  // null to represent draw
  protected DiscType winner;

  //game started or not
  protected boolean gameStarted;

  // Indicates if a player has skipped their turn.
  protected boolean skipped;


  /**
   * constructor of basicBoard.
   */
  public ABoard() {
    this.winner = null;
    gameOver = false;
    gameStarted = false;
    turns = DiscType.BLACK;
    skipped = false;
  }


  /**
   * given the current discTpe and return the next discType.
   *
   * @param turns the disc for the current player
   * @return discType for the next player
   */
  protected DiscType nextDisc(DiscType turns) {
    if (turns == DiscType.WHITE) {
      return DiscType.BLACK;
    } else {
      return DiscType.WHITE;
    }
  }


  /**
   * Adds a location to the list of nearby locations if the given location
   * contains an opponent's disc.
   *
   * @param x      the row number of the location
   * @param y      the index in that row
   * @param nearBy the list to which the location is to be added
   */
  protected void addLocation(int x, int y, List<BoardLocation> nearBy) {

    try {
      if (opponentDisc(pile.get(x).get(y), turns)) {

        nearBy.add(new BoardLocation(x, y));
      }
    } catch (IndexOutOfBoundsException ignored) {
    }
  }

  /**
   * Determines if a disc is an opponent's disc based on the current turn.
   *
   * @param that  the disc type at a particular location
   * @param turns the current turn's disc type
   * @return true if the given disc type is an opponent's disc, false otherwise
   */
  private boolean opponentDisc(DiscType that, DiscType turns) {
    return that != null && that != turns
            && that != DiscType.EMPTY;
  }


  /**
   * Sets the initial configuration of discs on the board.
   * The board at the beginning of the game which have some Disc initially.
   *
   * @param piles Initial configuration of piles.
   * @return Updated configuration of piles with discs.
   */
  protected abstract List<List<DiscType>> initiateDiscs(List<List<DiscType>> piles);


  /**
   * Fetches the disc type of the current turn.
   *
   * @return Current player's disc type.
   */
  @Override
  public DiscType getTurns() {
    gameOverState();
    return turns;
  }

  /**
   * Skips the current player's round.
   * If a player skips twice in a row, the game is considered over.
   *
   * @throws IllegalStateException If there is no valid disc type for the current turn.
   */
  @Override
  public void skipRound() {
    gameOverState();
    ensureGameStarted();

    if (turns == null || turns == DiscType.EMPTY) {
      throw new IllegalStateException("can't skip when the discType is not valid");
    }
    turns = nextDisc(turns);
    if (skipped) {
      gameOver = true;
    }
    skipped = true;
  }


  /**
   * Places a disc at the specified location on the board.
   * Checks if the move is valid and updates the game state accordingly.
   *
   * @param x The row number, 0-base.
   * @param y The column number, 0-base.
   * @throws IllegalArgumentException If the provided coordinates are negative.
   * @throws IllegalStateException    If there's no valid move available or if the specified
   *                                  location is already occupied.
   */
  @Override
  public void placeDisc(int x, int y) {
    gameOverState();
    ensureGameStarted();
    List<List<DiscType>> piles = this.getPile();

    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("not a valid input to place Disc");
    }

    if (!(x < piles.size() && y < piles.get(x).size())) {
      throw new IllegalArgumentException("row or index exceed the limits.");
    }
    DiscType save = turns;
    if (placeableDiscsLocations().isEmpty()) {
      throw new IllegalStateException("no possible move at this moment");
    }

    turns = save;
    placeDiscHelper(x, y);
    skipped = false; // game continues.

    if (!anyLegalMove(DiscType.BLACK) && !anyLegalMove(DiscType.WHITE)) {
      gameOver = true;
    }
  }

  protected abstract List<BoardLocation> placeableDiscsLocations();


  protected abstract void placeDiscHelper(int x, int y);


  /**
   * Calculates the score of a player based on the number of their discs on the board.
   *
   * @param playerDisc The disc type representing the player (either {@link DiscType#WHITE} or
   *                   {@link DiscType#BLACK}).
   * @return The score of the player.
   */
  public int getScore(DiscType playerDisc) {
    int score = 0;
    for (List<DiscType> ld : pile) {
      for (DiscType dt : ld) {
        if (dt == playerDisc) {
          score++;
        }
      }
    }
    return score;
  }

  /**
   * Throws an exception if the game is over.
   */
  protected void gameOverState() {
    if (gameOver) {
      throw new IllegalStateException("game is already over");
    }
  }

  @Override
  public void startGame() {
    gameStarted = true;
  }

  /**
   * if game haven't started, through exception.
   */
  protected void ensureGameStarted() {
    if (!gameStarted) {
      throw new IllegalArgumentException("Game has not started yet.");
    }
  }


  /**
   * Returns a copy of the current state of the board.
   *
   * @return A list of lists representing the board.
   */
  @Override
  public List<List<DiscType>> getPile() {
    // don't need to examine if game is already over
    // can still read the pile after game is over
    // gameOverState();
    List<List<DiscType>> acc = new ArrayList<>();
    for (List<DiscType> ld : pile) {
      acc.add(new ArrayList<>(ld));
    }
    // return a new pile in case of aliasing
    return acc;
  }


  /**
   * Returns the width of the board.
   *
   * @return The width.
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * Returns the height of the board.
   *
   * @return The height.
   */
  @Override
  public int getHeight() {

    return this.height;
  }


  /**
   * Determines the winner of the game based on the number of discs each player has on the board,
   * or null if it's a tie.
   *
   * @throws IllegalStateException If the game is still in progress.
   */
  @Override
  public DiscType getWinner() {
    if (!gameOver) {
      throw new IllegalStateException("game in progress");
    }

    int playerA = getScore(DiscType.WHITE);
    int playerB = getScore(DiscType.BLACK);
    if (playerA > playerB) {
      winner = DiscType.WHITE;
      return winner;
    } else if (playerA == playerB) {
      return null;
    } else {
      winner = DiscType.BLACK;
      return winner;
    }
  }

  /**
   * Checks if the game is over.
   * A game is considered over if there are no valid moves left.
   *
   * @return true if the game is over, false otherwise.
   */

  @Override
  public boolean gameOver() {
    if (!anyLegalMove() && skipped) {
      return true;
    } else {
      return gameOver;
    }
  }


  protected List<List<DiscType>> deepCopyPile(List<List<DiscType>> originalPile) {
    List<List<DiscType>> copyPiles = new ArrayList<>();
    for (List<DiscType> ld : originalPile) {
      copyPiles.add(new ArrayList<>(ld));
    }
    return copyPiles;
  }

  @Override
  public boolean legalMove(int row, int index) {
    // can't invoke this method when the game is over.
    gameOverState();

    // Create a deep copy of the pile
    List<List<DiscType>> copyPiles = deepCopyPile(pile);

    // Try to place a disc in the given location
    try {
      placeDisc(row, index);
      resetPiles(copyPiles);// Reset the piles to the original state
      this.turns = nextDisc(turns);
      return true;
    } catch (IllegalStateException e) {
      return false;
    }
  }

  /**
   * Resets the piles to the initial state.
   * Helper method for placeableDiscsLocations(),
   * Since placeableDiscsLocations() mutate the piles.
   *
   * @param initialPile The initial state of the board.
   */

  protected void resetPiles(List<List<DiscType>> initialPile) {
    pile = deepCopyPile(initialPile);
  }


  @Override
  public boolean anyLegalMove() {
    // can't invoke this method when game is over.
    // gameOverState();
    boolean legalMove;
    if (gameOver) {
      return false;
    } else {
      try {
        legalMove = !placeableDiscsLocations().isEmpty();
      } catch (IllegalStateException e) {
        legalMove = false;
      }
      return legalMove;
    }
  }


  @Override
  public List<BoardLocation> legalMoves() {
    List<BoardLocation> acc = new ArrayList<>();
    try {
      acc = placeableDiscsLocations();
    } catch (IllegalStateException ignored) {
    }
    return acc;
  }

  public abstract List<BoardLocation> getCorners();
}
