package view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JPanel;

import model.DiscType;
import model.ReadonlyReversiModel;
import view.panel.IBoardPanel;
import view.singletile.ISingleTile;

/**
 * Defines the interface for a GUI view that displays a hexagonal game board.
 * This interface extends from IView and provides
 * additional methods specific to a hexagon-based view.
 */
public interface IGUIView extends IView, KeyListener, MouseListener {
  public List<List<ISingleTile>> initiateBoard(List<List<DiscType>> piles);

  /**
   * Retrieves the hexagons that make up the game board.
   * Each hexagon is represented within a nested list structure.
   *
   * @return A List of Lists, where each inner list represents a row of hexagons on the board.
   */
  public List<List<ISingleTile>> getRenderBoard();

  /**
   * Initializes the frame with a title based on the player's disc type.
   * This method is used to set the title of the game window to reflect the current player.
   *
   * @param playerType The disc type of the current player (e.g., BLACK or WHITE).
   */
  public void initiateTitle(DiscType playerType);

  /**
   * Adds a feature listener to respond to actions performed on the view.
   * This method allows the view to communicate with other components like the controller.
   *
   * @param playerFeatures The features object that will be notified of actions on the view.
   */
  public void addFeatures(PlayerFeatures playerFeatures);

  /**
   * get the features of the current view.
   *
   * @return a list of player features
   */
  public List<PlayerFeatures> getPlayerFeatures();

  /**
   * repaint the view since view is the listener.
   */
  public void repaintView();

  /**
   * update the view title to notify the player.
   */
  public void updateTitle();

  /**
   * render the game over message.
   */
  public void gameOverMessage();

  public void errorMessage();

  /**
   * get JPanel of the current view.
   */
  public JPanel getJPanel();

  public IBoardPanel getHexPanel();

  /**
   * if the user is clicked on the board or not.
   *
   * @return true if the user is clicking on the tile
   */
  public boolean getUserClicked();

  public boolean getOnCommand();

  public ReadonlyReversiModel getModel();

  public void replaceJPanel(IBoardPanel replacePanel);

  public void notifyToPass();

  public void updateSideLength(int width, int height);

  /**
   * update the pile with new size.
   */
  public void resizingPiles();

  /**
   * get the player type of the current view.
   */
  public DiscType getPlayerType();


}
