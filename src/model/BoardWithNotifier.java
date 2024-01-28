package model;

import java.util.ArrayList;
import java.util.List;

/**
 * extended version of basicBoard, with the ability to inform the listener
 * if there's any mutation.
 */
public class BoardWithNotifier implements INotifierBoard {

  private final IBoard model;

  private final List<ModelStatusListener> listeners = new ArrayList<>();


  public BoardWithNotifier(IBoard model) {
    this.model = model;
  }


  /**
   * add a new listener to the current model.
   */
  @Override
  public void addListener(ModelStatusListener modelListener) {
    listeners.add(modelListener);
  }


  @Override
  public void notifyListener(String command) {

    switch (command) {
      case "startGame":
        for (ModelStatusListener ml : listeners) {
          ml.onGameStart();
        }
        break;
      case "place":
        for (ModelStatusListener ml : listeners) {
          ml.onPlaceDisc();
        }
        break;
      case "skip":
        for (ModelStatusListener ml : listeners) {
          ml.onSkipRound();
        }
        break;
      case "gameEnd":
        for (ModelStatusListener ml : listeners) {
          ml.onGameOver();
        }
        break;
      default:
        throw new IllegalArgumentException("no known command: " + command);
    }
  }

  @Override
  public void placeDisc(int x, int y) {
    model.placeDisc(x, y);
    notifyListener("place");
    if (model.gameOver()) {
      System.out.println("gameEnd");
      notifyListener("gameEnd");
    }
  }

  @Override
  public IBoard makeBoard(ReadonlyReversiModel model) {
    return this.model.makeBoard(model);
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

  @Override
  public boolean anyLegalMove() {
    return this.model.anyLegalMove();
  }

  @Override
  public boolean anyLegalMove(DiscType player) {
    return model.anyLegalMove(player);
  }

  @Override
  public List<BoardLocation> legalMoves() {
    return this.model.legalMoves();
  }

  @Override
  public int getScore(DiscType player) {
    return this.model.getScore(player);
  }

  @Override
  public void startGame() {
    this.model.startGame();
    notifyListener("startGame");
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
    return model.getMutableVersion();
  }


  @Override
  public void skipRound() {
    this.model.skipRound();
    notifyListener("skip");
    if (this.model.gameOver()) {
      notifyListener("gameEnd");
    }
  }
}
