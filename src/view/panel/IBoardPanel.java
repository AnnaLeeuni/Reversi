package view.panel;

import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.List;

import model.DiscType;
import model.ReadonlyReversiModel;
import view.singletile.ISingleTile;

/**
 * the functionalities needed for a HexagonPanel.
 */
public interface IBoardPanel extends KeyListener, MouseListener {

  /**
   * get the location the user just clicked.
   *
   * @return the position as in Point2D, null if user haven't clicked
   */
  public Point2D getClickedLocation();

  /**
   * if the user is clicking or not. to determine whether to show the highlight
   * on the tile.
   *
   * @return true if the panel should show the highlight
   */
  public boolean getUserClicked();

  /**
   * Resets the highlighting information for all hexagons.
   */
  public void resetHighLight();

  /**
   * Repaints the hexagonal tiles with updated information.
   *
   * @param hexPile The updated list of hexagons to be repainted
   */
  public void repaintBoard(List<List<ISingleTile>> hexPile);

  /**
   * similar to component's paintComponent.
   */
  public void paintComponent(Graphics g);

  public void removeMouseListener(MouseListener l);

  public void removeKeyListener(KeyListener l);

  public void addMouseListener(MouseListener l);

  public void addKeyListener(KeyListener l);

  public ReadonlyReversiModel getModel();

  /**
   * set the player type of the  current view.
   */
  public void setPlayerType(DiscType playerType);

  /**
   * get the player type of the  current view.
   */
  public DiscType getPlayerType();

}
