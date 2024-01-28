package view;

import java.io.IOException;
import java.util.List;

import model.DiscType;
import model.ReadonlyReversiModel;
import view.singletile.ISingleTile;

/**
 * a mock view that replaced all the functionalities with append.
 */
public class MockView extends ViewBoard {
  private final Appendable out;

  /**
   * a mockView should be able to take in an appendable.
   */
  public MockView(Appendable out, ReadonlyReversiModel model) {
    super(model);
    this.out = out;
  }


  @Override
  public List<List<ISingleTile>> getRenderBoard() {
    try {
      out.append("getHexagons");
    } catch (IOException ignored) {
    }
    return null;
  }

  @Override
  public void initiateTitle(DiscType playerType) {
    try {
      out.append("initiateTitle");
    } catch (IOException ignored) {
    }
  }

  @Override
  public void addFeatures(PlayerFeatures playerFeatures) {
    try {
      out.append("addFeatures \n");
    } catch (IOException ignored) {
    }
  }

  @Override
  public void repaintView() {
    try {
      out.append("repaintView \n");
    } catch (IOException ignored) {
    }
  }

  @Override
  public void updateTitle() {
    try {
      out.append("updateTitle \n");
    } catch (IOException ignored) {
    }
  }

  @Override
  public void setVisible(boolean display) {
    try {
      out.append("setVisible \n");
    } catch (IOException ignored) {
    }
  }

  @Override
  public void gameOverMessage() {
    try {
      out.append("gameOverMessage \n");
    } catch (IOException ignored) {
    }
  }

  @Override
  public void errorMessage() {
    try {
      out.append("errorMessage \n");
    } catch (IOException ignored) {
    }
  }
}
