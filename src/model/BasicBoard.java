package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the basic mode in Reversi (also known as Othello).
 * <p>
 * The BasicBoard manages the game state, including placing and flipping discs,
 * skipping rounds, determining game-over, and calculating scores.
 * </p>
 */
public class BasicBoard extends ABoard {


  //private boolean skipped;

  /**
   * Constructs a standard BasicBoard.
   * Initializes the board with default dimensions and places initial discs.
   */

  private List<ModelStatusListener> statusListeners = new ArrayList<>();


  /**
   * A default constructor of the game board.
   * initiate the piles.
   */
  public BasicBoard() {
    super();
    height = 11;
    width = 11;
    this.pile = generatePiles(width, height);
  }


  // if the board looks like this, no way to keep playing it
  // o x
  //x _ o
  // o x

  // if the height number and width number are even number, board would not looks right;
  // _ _ _ _
  //_ _ _ _ _
  //_ _ _ _ _
  // _ _ _ _

  /**
   * Constructs a BasicBoard with custom dimensions.
   * <p>
   * Initializes the board with given dimensions and places initial discs.
   * If the board dimensions are too small, the game is set as over.
   * </p>
   *
   * @param size the size of the board which is both the height and the width.
   */
  public BasicBoard(int size) {
    if (size < 4) {
      throw new IllegalArgumentException("not a valid input for pile");
    }

    // if the height is even number, board would not be right;
    // _ _ _ _
    //_ _ _ _ _
    //_ _ _ _ _
    // _ _ _ _
    if (size % 2 == 0) {
      throw new IllegalArgumentException("Pile height cannot be even");
    }

    if (size > 2 * size - 1) {
      throw new IllegalArgumentException("Height exceeds the allowed maximum for the given width");
    }
    // in the basicBoard model, the width and the height are the same.
    this.height = size;
    this.width = size;
    this.pile = generatePiles(size, size);
    this.winner = null;
    this.gameOver = false;
    gameStarted = false;

    turns = DiscType.BLACK;
    skipped = false;
  }

  /**
   * Constructs a BasicBoard using a pre-defined set of piles.
   * Useful for testing purposes.
   *
   * @param pile A 2D list representing the initial state of the board.
   */
  public BasicBoard(List<List<DiscType>> pile) {
    Objects.requireNonNull(pile);
    // check if the pile that got passed in is valid
    if (validPileChecker(pile)) {
      throw new IllegalArgumentException("not a valid pile");
    }

    this.height = pile.size();
    this.width = pile.get((this.height + 1) / 2).size();

    // make a copy of the input
    List<List<DiscType>> copy = new ArrayList<>();
    List<DiscType> insideCopy;
    for (List<DiscType> lc : pile) {
      insideCopy = new ArrayList<>(lc);
      copy.add(insideCopy);
    }
    this.pile = copy;
    this.winner = null;
    this.gameOver = false;

    // since this constructor is for testing, assume the pile that got passed in
    // already got disc on it.
    gameStarted = true;
    turns = DiscType.BLACK;
    this.skipped = false;
  }

  /**
   * Constructs a BasicBoard using a pre-defined set of piles.
   * Useful for testing purposes.
   *
   * @param pile       A 2D list representing the initial state of the board.
   * @param playerTurn A turn indication.
   */

  public BasicBoard(List<List<DiscType>> pile, DiscType playerTurn) {
    this(pile);
    this.skipped = false;
    this.turns = playerTurn;
  }


  /**
   * check if the given pile is valid.
   *
   * @param pile lists of list of discTypes
   * @return true if the given pile is valid
   */
  private boolean validPileChecker(List<List<DiscType>> pile) {
    // pile can't be empty
    if (pile.isEmpty()) {
      return false;
    }

    int width = pile.get(0).size();
    int height = pile.size();

    // pile's width and height should meet the requirement
    if (checkValidDimension(pile, width, height)) {
      return false;
    }

    // Validate the shape of the pile and the null placement
    return checkNullsInCorrectPosition(width, height);
  }

  /**
   * each raw need to have same length.
   * the length and width should meet the basic requirement of a pile
   */
  private static boolean checkValidDimension(List<List<DiscType>> pile, int width, int height) {
    for (List<DiscType> row : pile) {
      if (row.size() != width) {
        return false;
      }
    }
    return width >= 1 && height % 2 == 1 && height <= 2 * width - 1;
  }

  /**
   * check if there are enough nulls and nulls are at right location.
   */
  private boolean checkNullsInCorrectPosition(int width, int height) {
    int midRow = (height - 1) / 2;
    for (int i = 0; i <= midRow; i++) {
      int nullsInRow = width - (i + 1);
      try {
        if (countNulls(pile.get(i)) != nullsInRow
                || countNulls(pile.get(height - 1 - i)) != nullsInRow) {
          return false;
        }
      } catch (NullPointerException ignored) {

      }
    }
    return true;
  }

  /**
   * count how many nulls are inside the given row.
   */
  private int countNulls(List<DiscType> row) {
    int count = 0;
    for (DiscType disc : row) {
      if (disc == null) {
        count++;
      }
    }
    return count;
  }

  /**
   * Generates the initial configuration of the piles on the board.
   * Fills the places outside the board with null.
   *
   * @param width  Width of the board.
   * @param height Height of the board.
   * @return List of piles representing the board.
   */
  protected List<List<DiscType>> generatePiles(int width, int height) {

    // get the first half of the board.
    // the width of the first row.
    int rowWidth = width - (height - 1) / 2;
    List<List<DiscType>> board = new ArrayList<>();
    // also include the row in the middle.
    while (rowWidth <= width) {
      List<DiscType> row = new ArrayList<>();
      int i = 0;
      while (i < rowWidth) {
        row.add(DiscType.EMPTY);
        i++;
      }
      while (row.size() < width) {
        row.add(0, null);
      }
      board.add(row);
      rowWidth++;
    }

    // get the second half of the board.
    List<List<DiscType>> boardDown = new ArrayList<>();
    // ignore the last row in the first half.
    int index = board.size() - 1;
    while (index > 0) {
      index--;
      List<DiscType> list = reverse(board.get(index));
      boardDown.add(list);
    }

    // assemble the board.
    board.addAll(boardDown);
    return new ArrayList<>(initiateDiscs(board));
  }

  /**
   * Reverses the provided list of discs.
   * A Helper for the generates piles
   *
   * @param lo List of discs to reverse.
   * @return Reversed list of discs.
   */
  private List<DiscType> reverse(List<DiscType> lo) {
    int i = 0;
    List<DiscType> acc = new ArrayList<>();
    while (i < lo.size()) {
      acc.add(lo.get(lo.size() - i - 1));
      i++;
    }
    return acc;
  }


  /**
   * Converts a user input coordinate to the correct index of a column,
   * adjusting for null grid values.
   *
   * @param x the row number
   * @param y the index in that row
   * @return the correct index for the given coordinates
   */
  private int getIndex(int x, int y) {
    int i = 0;

    try {
      // just need to check the first half of the line
      while (i < (width - 1) / 2 + 1) {
        if (pile.get(x).get(i) == null) {
          y = y + 1;
        }
        i++;
      }
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("not a valid location to place");
    }
    return y;
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
    // check if the location is surrounded by any opponent's disc.
    List<BoardLocation> nearBy = new ArrayList<>();
    // 3, 2
    addLocation(x, y - 1, nearBy); // 3, 1
    addLocation(x, y + 1, nearBy); // 3, 3
    addLocation(x - 1, y + 1, nearBy); // 2, 3
    addLocation(x + 1, y - 1, nearBy);// 4, 1
    addLocation(x + 1, y, nearBy);// 4, 2
    addLocation(x - 1, y, nearBy);// 2, 2

    return nearBy;
  }

  /**
   * Analyzes surrounding discs and flips them if they meet the required conditions.
   *
   * @param lo a list of nearby locations to consider for flipping
   * @param x  the x-coordinate of the input location
   * @param y  the y-coordinate of the input location
   */
  private void flipDiscs(List<BoardLocation> lo, int x, int y) {
    int countRefusedTimes = 0;
    for (BoardLocation bl : lo) {
      int xDiff = x - bl.getRow();
      int yDiff = y - bl.getIndex();
      if (!flipDiscsHelper(bl, xDiff, yDiff, x, y)) {
        countRefusedTimes++;
      }
    }

    // if all the move have been rejected
    if (countRefusedTimes == lo.size()) {
      throw new IllegalStateException("not a valid move");
    }
  }

  /**
   * Recursive helper method to flip a sequence of discs.
   *
   * @param bl    the starting disc location to be considered for flipping
   * @param xDiff diff in x-coordinate between the input location and the current disc location
   * @param yDiff diff in y-coordinate between the input location and the current disc location
   * @param x     the x-coordinate of the original location
   * @param y     the y-coordinate of the original location
   * @return true if a sequence of discs is flipped, false otherwise
   */
  private boolean flipDiscsHelper(BoardLocation bl, int xDiff, int yDiff, int x, int y) {
    // the location of the disc on the same line as the disc nearby and the location player
    // selected
    BoardLocation newBl = getBoardLocation(bl, xDiff, yDiff);

    try {
      //
      if (pile.get(newBl.getRow()).get(newBl.getIndex()).equals(turns)) {

        // disc places on the selected location
        pile.get(x).set((y), turns);

        // set the nearby disc to the player's disc.
        pile.get(bl.getRow()).set(bl.getIndex(), turns);
        return true;
      }
      // if the line of discs lead to empty.
      else if (pile.get(newBl.getRow()).get(newBl.getIndex()).equals(DiscType.EMPTY)) {
        return false;
      } else {
        if (flipDiscsHelper(newBl, xDiff, yDiff, newBl.getRow(), newBl.getIndex())) {

          // set the nearby disc to the player's disc.
          pile.get(bl.getRow()).set(bl.getIndex(), turns);
          pile.get(x).set(y, turns);

          return true;
        }
        return false;
      }
    } catch (NullPointerException | IndexOutOfBoundsException e) {
      return false;
    }
  }

  /**
   * Finds the next BoardLocation in a sequence based on differences in coordinates.
   *
   * @param bl    the starting disc location
   * @param xDiff difference in x-coordinate from the input location
   * @param yDiff difference in y-coordinate from the input location
   * @return the next BoardLocation in the sequence
   */
  private static BoardLocation getBoardLocation(BoardLocation bl, int xDiff, int yDiff) {
    BoardLocation newBl;
    // the player placed on the left.
    // or the player placed on the right.

    if (xDiff == 0 && yDiff < 0 || (xDiff == 0 && yDiff > 0)) {

      newBl = new BoardLocation(bl.getRow(), bl.getIndex() - yDiff);
    }
    // the player placed on the upper left.
    // or the player placed on the lower right.

    else if (xDiff < 0 && yDiff == 0 || (xDiff > 0 && yDiff == 0)) {

      newBl = new BoardLocation(bl.getRow() - xDiff, bl.getIndex());
    }

    // the player placed on the upper right.
    // or the player placed on the lower left.
    else {
      newBl = new BoardLocation(bl.getRow() - xDiff, bl.getIndex() - yDiff);

    }
    return newBl;
  }


  /**
   * Initializes the game board by setting up the initial placement of discs.
   * Typically, the board is initiated with six discs in the center, three of each color.
   *
   * @param piles The board represented as a list of lists of {@link DiscType}.
   * @return The modified board with the initial placement of discs.
   */
  @Override
  protected List<List<DiscType>> initiateDiscs(List<List<DiscType>> piles) {
    try {
      piles.get((height - 1) / 2).set((width - 1) / 2 - 1, DiscType.BLACK);
      piles.get((height - 1) / 2).set((width - 1) / 2 + 1, DiscType.WHITE);
      piles.get((height - 1) / 2 + 1).set((width - 1) / 2 - 1, DiscType.WHITE);
      piles.get((height - 1) / 2 + 1).set((width - 1) / 2, DiscType.BLACK);
      piles.get((height - 1) / 2 - 1).set((width - 1) / 2, DiscType.WHITE);
      piles.get((height - 1) / 2 - 1).set((width - 1) / 2 + 1, DiscType.BLACK);
    } catch (IndexOutOfBoundsException ignored) {
    }

    return piles;
  }


  /**
   * Returns the available locations on the board where a disc can be placed.
   *
   * @return A list of board locations where discs can be placed.
   */
  protected List<BoardLocation> placeableDiscsLocations() {
    List<BoardLocation> lb = new ArrayList<>();
    List<List<DiscType>> copyPiles = new ArrayList<>();
    for (List<DiscType> ld : pile) {
      copyPiles.add(new ArrayList<>(ld));
    }

    // starts from the first row
    int rowNumber = 0;
    while (rowNumber < height) {

      // starts from the first element in the row
      int index = 0;
      while (index < width - getIndex(rowNumber, 0)) {
        try {
          placeDiscHelper(rowNumber, index);
          if (this.turns == DiscType.WHITE) {
            turns = DiscType.BLACK;
          } else {
            turns = DiscType.WHITE;
          }
          lb.add(new BoardLocation(rowNumber, index));

          // reset the piles
          this.resetPiles(copyPiles);
        } catch (IllegalStateException ignored) {
        }
        index++;
      }
      rowNumber++;
    }

    // if there is no available move, the player can only skip.
    if (lb.isEmpty()) {
      throw new IllegalStateException("no available move at this moment. You need to skip");
    }

    return lb;
  }


  @Override
  public IBoard makeBoard(ReadonlyReversiModel model) {
    BasicBoard copyModel = new BasicBoard(model.getPile(), model.getTurns());
    copyModel.startGame();
    return copyModel;
  }

  @Override
  public List<BoardLocation> getCorners() {
    // get the x coordinate of the last item in the row
    int bound = this.getWidth() - 1;
    while (bound > 0) {
      try {
        this.getDisc(0, bound);
        break;
      } catch (IllegalArgumentException e) {
        bound--;
      }
    }
    List<BoardLocation> cornerPosition = new ArrayList<>();
    cornerPosition.add(new BoardLocation(0, 0));
    cornerPosition.add(new BoardLocation(0, bound));
    cornerPosition.add(new BoardLocation((this.getHeight() - 1) / 2, 0));
    cornerPosition.add(new BoardLocation((this.getHeight() - 1) / 2,
            this.getWidth() - 1));
    cornerPosition.add(new BoardLocation(this.getHeight() - 1, 0));
    cornerPosition.add(new BoardLocation(this.getHeight() - 1, bound));
    return cornerPosition;
  }

  @Override
  public IBoard getMutableVersion() {
    IBoard copy =  new BasicBoard(this.pile, this.turns);
    copy.startGame();
    return copy;
  }


  /**
   * Helper method to actually place the disc at the specified location.
   *
   * @param x The row number.
   * @param y The column number.
   * @throws IllegalStateException If the move isn't allowed or if the location is invalid.
   */
  protected void placeDiscHelper(int x, int y) {
    // transfer the coordinate to the coordinate on the board.
    y = getIndex(x, y);

    try {
      // return false if the given location is not empty.
      if (!pile.get(x).get(y).equals(DiscType.EMPTY)) {
        throw new IllegalStateException("not a allowed move");
      }
    } catch (NullPointerException | IndexOutOfBoundsException e) {
      throw new IllegalStateException("not a valid location");
    }

    // collect the location of the discs nearby.
    List<BoardLocation> bl = getDiscsNearby(x, y);
    if (bl.isEmpty()) {
      throw new IllegalStateException("not a allowed move");
    }

    // flip the disc
    flipDiscs(bl, x, y);

    // assign the turns to the next discType
    turns = nextDisc(turns);
  }


  /**
   * Retrieves the disc type at the specified location.
   *
   * @param x The row number, 0-bars.
   * @param y The column number, 0-base.
   * @return The type of disc at the specified location.
   */
  @Override
  public DiscType getDisc(int x, int y) {
    // gameOverState();

    y = getIndex(x, y);

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
    BasicBoard basicBoard = new BasicBoard(this.pile, player);
    return basicBoard.anyLegalMove();
  }

  @Override
  public List<BoardLocation> getCornersNearBy() {
    List<BoardLocation> nearCorner = new ArrayList<>();
    for (BoardLocation bl : getCorners()) {
      try {
        this.getDisc(bl.getRow(), bl.getIndex() - 1);
        nearCorner.add(new BoardLocation(bl.getRow(), bl.getIndex() - 1));
      } catch (IllegalArgumentException ignored) {
      }
      try {
        this.getDisc(bl.getRow(), bl.getIndex() + 1);
        nearCorner.add(new BoardLocation(bl.getRow(), bl.getIndex() + 1));
      } catch (IllegalArgumentException ignored) {
      }
      try {
        this.getDisc(bl.getRow() - 1, bl.getIndex() + 1);
        nearCorner.add(new BoardLocation(bl.getRow() - 1, bl.getIndex() + 1));
      } catch (IllegalArgumentException ignored) {
      }
      try {
        this.getDisc(bl.getRow() + 1, bl.getIndex() - 1);
        nearCorner.add(new BoardLocation(bl.getRow() + 1, bl.getIndex() - 1));
      } catch (IllegalArgumentException ignored) {
      }
      try {
        this.getDisc(bl.getRow() + 1, bl.getIndex());
        nearCorner.add(new BoardLocation(bl.getRow() + 1, bl.getIndex()));
      } catch (IllegalArgumentException ignored) {
      }
      try {
        this.getDisc(bl.getRow() - 1, bl.getIndex());
        nearCorner.add(new BoardLocation(bl.getRow() - 1, bl.getIndex()));
      } catch (IllegalArgumentException ignored) {
      }
    }
    return nearCorner;
  }


  //  public void addModelStatusListener(ModelStatusListener listener) {
  //    statusListeners.add(listener);
  //  }


  //  private void notifyGameEnd() {
  //    for (ModelStatusListener listener : statusListeners) {
  //      listener.onGameOver();
  //    }
  //  }

}
