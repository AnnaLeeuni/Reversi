package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A mock model class used for recording a transcript of which coordinates were
 * inspected by a strategy in a game of Reversi. This class extends the BasicBoard
 * and implements the IBoard interface, providing functionality for tracking inspected
 * locations during gameplay.
 */
public class MockReadonlyReversiModel implements IMockModel {

  private final IBoard model;

  protected StringBuilder out;
  // the locations that got inspected
  private List<BoardLocation> inspectedLocations = new ArrayList<>();

  /**
   * Constructor for creating an instance of MockReadonlyReversiModel with default settings.
   */
  public MockReadonlyReversiModel(StringBuilder out, IBoard model) {
    this.model = model;
    this.out = out;
  }



  /**
   * Returns all the potential legal moves on the board.
   * This implementation fakes the legal moves for testing purposes.
   *
   * @return A list of BoardLocation objects representing the legal moves.
   */
  @Override
  public List<BoardLocation> legalMoves() {
    List<BoardLocation> acc = new ArrayList<>();
    try {
      acc = allDiscLocations();
    } catch (IllegalStateException ignored) {
    }
    return acc;
  }

  /**
   * Returns all non-null locations on the board, treating the first non-null element
   * in each row as index 0.
   * @return A list of board locations that are not null.
   */
  private List<BoardLocation> allDiscLocations() {
    List<BoardLocation> locations = new ArrayList<>();
    List<List<DiscType>> pile = this.model.getPile();

    for (int row = 0; row < pile.size(); row++) {
      List<DiscType> rowList = pile.get(row);
      int nonNullCount = 0; // Counter for non-null elements in the row
      for (int col = 0; col < rowList.size(); col++) {
        // Check if the location is not null
        if (rowList.get(col) != null) {
          locations.add(new BoardLocation(row, nonNullCount));
          nonNullCount++; // Increment the counter for each non-null element
        }
      }
    }

    return locations;
  }


  @Override
  public int getScore(DiscType player) {
    return this.model.getScore(player);
  }

  @Override
  public void startGame() {
    this.model.startGame();

  }

  @Override
  public boolean anyLegalMove(DiscType player) {
    return this.model.anyLegalMove(player);
  }

  @Override
  public boolean anyLegalMove() {
    return true;
  }
  
  @Override
  public List<BoardLocation> getCorners() {
    return model.getCorners();
  }

  @Override
  public List<BoardLocation> getCornersNearBy() {
    return model.getCornersNearBy();
  }

  @Override
  public IBoard getMutableVersion() {
    return new MockManipulativeReversiModel(out, model);
  }


  @Override
  public List<BoardLocation> getInspectedLocations() {
    return inspectedLocations;
  }

  @Override
  public void skipRound() {
    this.model.skipRound();

  }

  @Override
  public void placeDisc(int x, int y) {
    inspectedLocations.add(new BoardLocation(x, y));
    //System.out.println(out);
    out.append("visit position: " + "x: ").append(x).append(", y: ").append(y).append("\n");
  }

  /**
   * set the current inspectedLocations as the given inspectedLocation.
   * @param inspectedLocations a list of locations that got inspected by strategy.
   */
  private void setInspectedLocations(List<BoardLocation> inspectedLocations) {
    // keep the aliasing, when the latter get updated, the previous one also get to update.
    this.inspectedLocations = inspectedLocations;
  }

  @Override
  public IBoard makeBoard(ReadonlyReversiModel model) {
    List<BoardLocation> saveInspectedPosition = this.inspectedLocations;
    MockReadonlyReversiModel newBoard =
            new MockReadonlyReversiModel(out, this.model);
    newBoard.setInspectedLocations(inspectedLocations);
    return newBoard;
  }

  @Override
  public boolean gameOver() {
    return this.model.gameOver();
  }

  @Override
  public List<List<DiscType>> getPile() {
    return this.model.getPile();
  }

  @Override
  public int getWidth() {
    return this.model.getWidth();
  }

  @Override
  public int getHeight() {
    return this.model.getHeight();
  }

  @Override
  public DiscType getDisc(int x, int y) {
    return this.model.getDisc(x, y);
  }

  @Override
  public DiscType getWinner() {
    return this.model.getWinner();
  }

  @Override
  public DiscType getTurns() {
    return this.model.getTurns();
  }

  @Override
  public boolean legalMove(int row, int index) {
    return this.model.legalMove(row, index);
  }


}
