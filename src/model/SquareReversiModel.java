package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * new game mode that play the game in squares.
 */
public class SquareReversiModel extends ABoard {


  /**
   * the default constructor of the model.
   */
  public SquareReversiModel() {
    super.width = 8;
    super.height = 8;
    this.pile = generatePiles(width);
    turns = DiscType.BLACK; // Game starts with BLACK
    this.winner = null;
    this.gameStarted = false;
    this.skipped = false;
  }

  @Override
  protected List<List<DiscType>> initiateDiscs(List<List<DiscType>> piles) {
    return null;
  }


  /**
   * model with customized size.
   *
   * @param width the size of the board
   */
  public SquareReversiModel(int width) {
    if (width < 4) {
      throw new IllegalArgumentException("not a valid input for pile");
    }
    if (width % 2 != 0) {
      throw new IllegalArgumentException("Pile side have to be even");
    }

    this.width = width;
    this.height = width;
    this.pile = generatePiles(width);
    turns = DiscType.BLACK; // Game starts with BLACK
    this.winner = null;
    this.gameStarted = false;
    this.skipped = false;
  }

  /**
   * can take in a customized pile, for testing purpose.
   */
  public SquareReversiModel(List<List<DiscType>> pile) {
    this.pile = Objects.requireNonNull(deepCopyPile(pile));
    this.width = pile.size();
    this.turns = DiscType.BLACK;
    this.winner = null;
    this.gameStarted = false;
    this.skipped = false;
  }

  /**
   * can make a copy of a board with its pile and current player type.
   */
  public SquareReversiModel(List<List<DiscType>> pile, DiscType turn) {
    this.pile = Objects.requireNonNull(deepCopyPile(pile));
    this.turns = Objects.requireNonNull(turn);
    this.width = pile.size();
    this.winner = null;
    this.gameStarted = false;
    this.skipped = false;
  }


  /**
   * Generate the initial Board arrangement.
   *
   * @param width of the board, tile wise
   * @return the board tile list
   */
  protected List<List<DiscType>> generatePiles(int width) {
    List<List<DiscType>> piles = new ArrayList<>();
    for (int i = 0; i < width; i++) {
      List<DiscType> row = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        row.add(DiscType.EMPTY);
      }
      piles.add(row);
    }

    // Placing the initial four discs at the center
    int midWidth = width / 2;
    int midHeight = width / 2;

    // Ensure the initial placement of discs is in the center four squares
    piles.get(midHeight - 1).set(midWidth - 1, DiscType.BLACK);
    piles.get(midHeight - 1).set(midWidth, DiscType.WHITE);
    piles.get(midHeight).set(midWidth - 1, DiscType.WHITE);
    piles.get(midHeight).set(midWidth, DiscType.BLACK);

    return piles;
  }


  /**
   * Determines the locations of all the opponent's discs surrounding
   * a particular location.
   *
   * @param x the row number of the location
   * @param y the index in that row
   * @return a list of BoardLocation objects representing the nearby opponent discs
   */
  private List<BoardLocation> getDiscsNearby(int x, int y) {
    List<BoardLocation> nearBy = new ArrayList<>();
    for (int dx = -1; dx <= 1; dx++) {
      for (int dy = -1; dy <= 1; dy++) {
        if (dx == 0 && dy == 0) {
          continue;
        }
        addLocation(x + dx, y + dy, nearBy);
      }
    }
    return nearBy;
  }


  // placeDiscHelper method uses these helpers to implement the logic of placing a disc
  // and flipping the necessary opponent discs.
  @Override
  protected void placeDiscHelper(int x, int y) {
    if (pile.get(x).get(y) != DiscType.EMPTY) {
      throw new IllegalStateException("not an allowed move");
    }

    List<BoardLocation> bl = getDiscsNearby(x, y);
    if (bl.isEmpty()) {
      throw new IllegalStateException("not an allowed move");
    }

    flipDiscs(bl, x, y);
    turns = nextDisc(turns);
  }


  // checks along straight lines in the eight directions:
  private void flipDiscs(List<BoardLocation> lo, int x, int y) {
    int countRefusedTimes = 0;
    for (BoardLocation bl : lo) {
      int xDiff = bl.getRow() - x;
      int yDiff = bl.getIndex() - y;
      if (!flipDiscsHelper(bl, xDiff, yDiff, x, y)) {
        countRefusedTimes++;
      }
    }

    if (countRefusedTimes == lo.size()) {
      throw new IllegalStateException("not a valid move");
    }
  }


  // The flipDiscsHelper recursively checks if there's a line of opponent discs
  // ending with a disc of the current player's color.
  private boolean flipDiscsHelper(BoardLocation bl, int xDiff, int yDiff, int x, int y) {
    BoardLocation newBl = new BoardLocation(bl.getRow() + xDiff, bl.getIndex() + yDiff);

    try {
      if (pile.get(newBl.getRow()).get(newBl.getIndex()) == turns) {
        flipLine(x, y, xDiff, yDiff); // Flip the entire line of discs
        pile.get(x).set(y, turns); // Place the new disc
        return true;
      } else if (pile.get(newBl.getRow()).get(newBl.getIndex()) == DiscType.EMPTY) {
        return false;
      } else {

        //return flipDiscsHelper(newBl, xDiff, yDiff, x, y);
        if (flipDiscsHelper(newBl, xDiff, yDiff, newBl.getRow(), newBl.getIndex())) {

          // set the nearby disc to the player's disc.
          pile.get(bl.getRow()).set(bl.getIndex(), turns);
          pile.get(x).set(y, turns);

          return true;
        }
        return false;

      }
    } catch (IndexOutOfBoundsException | NullPointerException e) {
      return false;
    }
  }

  // The flipLine method flips all discs in a straight line between two points.
  private void flipLine(int startX, int startY, int xDiff, int yDiff) {
    int curX = startX + xDiff;
    int curY = startY + yDiff;

    while (pile.get(curX).get(curY) != turns) {
      pile.get(curX).set(curY, turns);
      curX += xDiff;
      curY += yDiff;
    }
  }


  //placeableDiscsLocations iterates over every cell in the board. For each empty cell,
  // it checks if placing a disc there would be a legal move using the isLegalMove method.
  protected List<BoardLocation> placeableDiscsLocations() {
    List<BoardLocation> legalMoves = new ArrayList<>();

    // Iterate over all positions on the board
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < width; y++) {
        // Check if the current position is empty and could potentially be a legal move
        if (pile.get(x).get(y) == DiscType.EMPTY && isLegalMove(x, y)) {
          legalMoves.add(new BoardLocation(x, y));
        }
      }
    }

    return legalMoves;
  }

  // checks all eight directions from the given cell to see if placing a disc
  // there would capture any of the opponent's discs. It returns true if
  // at least one direction is a legal move.
  private boolean isLegalMove(int x, int y) {
    // Check all eight directions for a legal move
    for (int dx = -1; dx <= 1; dx++) {
      for (int dy = -1; dy <= 1; dy++) {
        if (dx == 0 && dy == 0) {
          continue; // Skip checking the same cell
        }
        if (checkDirection(x, y, dx, dy)) {
          return true;
        }
      }
    }
    return false;
  }


  // looks in a specific direction from the given cell and determines if placing a disc
  // would result in capturing opponent's discs. It checks for a line of opponent's discs
  // ending with one of the player's discs.
  private boolean checkDirection(int x, int y, int dx, int dy) {
    int curX = x + dx;
    int curY = y + dy;
    boolean hasOpponentDisc = false;

    while (curX >= 0 && curX < width && curY >= 0 && curY < width) {
      DiscType curDisc = pile.get(curX).get(curY);
      if (curDisc == DiscType.EMPTY) {
        break; // Empty space, not a valid direction
      } else if (curDisc == turns) {
        return hasOpponentDisc; // Valid move if we've seen at least one opponent disc
      } else {
        hasOpponentDisc = true; // Opponent disc found, continue checking in this direction
      }
      curX += dx;
      curY += dy;
    }

    return false;
  }

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



  // different
  @Override
  public IBoard makeBoard(ReadonlyReversiModel model) {
    SquareReversiModel copyModel = new SquareReversiModel(model.getPile(), model.getTurns());
    copyModel.startGame();
    return copyModel;
  }


  // different
  @Override
  public DiscType getDisc(int x, int y) {
    // gameOverState();
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("not a valid input to see Disc");
    }

    if ((!(x < getPile().size() && y < getPile().get(x).size())) || pile.get(x).get(y) == null) {
      throw new IllegalArgumentException("row or index exceed the limits.");
    }

    return pile.get(x).get(y);
  }


  @Override
  public boolean anyLegalMove(DiscType player) {
    // make a copy of the board
    SquareReversiModel copy = new SquareReversiModel(this.getPile(), player);
    return copy.anyLegalMove();
  }

  @Override
  public List<BoardLocation> getCorners() {
    List<BoardLocation> corners = new ArrayList<>();
    corners.add(new BoardLocation(0, 0));
    corners.add(new BoardLocation(0, pile.get(0).size() - 1));
    corners.add(new BoardLocation(pile.size() - 1, pile.get(0).size() - 1));
    corners.add(new BoardLocation(pile.size() - 1, 0));
    return corners;
  }

  @Override
  public List<BoardLocation> getCornersNearBy() {
    List<BoardLocation> nearCorner = new ArrayList<>();
    for (BoardLocation bl : getCorners()) {
      try {
        getDisc(bl.getRow() - 1, bl.getIndex() - 1);
        nearCorner.add(new BoardLocation(bl.getRow() - 1, bl.getIndex() - 1));
      } catch (IllegalArgumentException ignored) {
      }
      try {
        getDisc(bl.getRow() - 1, bl.getIndex());
        nearCorner.add(new BoardLocation(bl.getRow() - 1, bl.getIndex()));
      } catch (IllegalArgumentException ignored) {
      }
      try {
        getDisc(bl.getRow() - 1, bl.getIndex() + 1);
        nearCorner.add(new BoardLocation(bl.getRow() - 1, bl.getIndex() + 1));
      } catch (IllegalArgumentException ignored) {
      }
      try {
        getDisc(bl.getRow(), bl.getIndex() - 1);
        nearCorner.add(new BoardLocation(bl.getRow(), bl.getIndex() - 1));
      } catch (IllegalArgumentException ignored) {
      }
      try {
        getDisc(bl.getRow(), bl.getIndex() + 1);
        nearCorner.add(new BoardLocation(bl.getRow(), bl.getIndex() + 1));
      } catch (IllegalArgumentException ignored) {
      }
      try {
        getDisc(bl.getRow() + 1, bl.getIndex());
        nearCorner.add(new BoardLocation(bl.getRow() + 1, bl.getIndex()));

      } catch (IllegalArgumentException ignored) {
      }
      try {
        getDisc(bl.getRow() + 1, bl.getIndex() - 1);
        nearCorner.add(new BoardLocation(bl.getRow() + 1, bl.getIndex() - 1));
      } catch (IllegalArgumentException ignored) {
      }
      try {
        getDisc(bl.getRow() + 1, bl.getIndex() + 1);
        nearCorner.add(new BoardLocation(bl.getRow() + 1, bl.getIndex() + 1));
      } catch (IllegalArgumentException ignored) {
      }
    }

    return nearCorner;
  }


  @Override
  public IBoard getMutableVersion() {
    IBoard copy = new SquareReversiModel(this.pile, this.turns);
    copy.startGame();
    return copy;
  }

}
