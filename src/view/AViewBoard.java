package view;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


import model.BoardLocation;
import model.DiscType;
import model.ReadonlyReversiModel;
import view.panel.IBoardPanel;
import view.singletile.ISingleTile;

/**
 * abstract class of view.
 */
public abstract class AViewBoard extends JFrame implements IGUIView {
  private List<List<ISingleTile>> squarePile;

  // The JPanel on the frame
  protected IBoardPanel panel;

  // the width of the side of a square
  private int squareSideLength = 35;

  private final List<PlayerFeatures> playerFeatures = new ArrayList<>();

  // represent the length of the edge of a single hexagon
  // if the user clicked the tile or not.
  private boolean userClicked = false;

  // store the current location of user clicked.
  private Point2D clickedLocation;

  // store the location of the currentActiveTile
  private BoardLocation currentActiveTile;


  // the model to get rendered
  private final ReadonlyReversiModel model;
  private int tileSize = 40;

  private int countGameOverWindow = 1;

  // the text for the current on the command state
  private String onCommandText;

  // the text for the current on the waiting state
  private String waitCommandText;

  // if it is the player with current view's turn.
  private boolean onCommand;

  /**
   * lifted the common field between views to an abstract view.
   */
  public AViewBoard(ReadonlyReversiModel model) {
    super();
    squarePile = initiateBoard(model.getPile());
    this.model = model;
    int height = model.getHeight();
    int defaultWidth = height * 45 * 2;
    int defaultHeight = height * 45 * 2;
  }


  @Override
  public List<List<ISingleTile>> getRenderBoard() {
    List<List<ISingleTile>> copyOfHexPile = new ArrayList<>();

    for (List<ISingleTile> row : squarePile) {
      List<ISingleTile> copyOfRow = new ArrayList<>(row);
      copyOfHexPile.add(copyOfRow);
    }

    return copyOfHexPile;
  }



  @Override
  public void addFeatures(PlayerFeatures playerFeatures) {
    this.playerFeatures.add(playerFeatures);
  }

  @Override
  public List<PlayerFeatures> getPlayerFeatures() {
    return this.playerFeatures;
  }


  @Override
  public void gameOverMessage() {
    if (model.gameOver() && countGameOverWindow > 0) {
      String winner = "White Disc Win!";
      if (model.getWinner() == DiscType.BLACK) {
        winner = "Black Disc Win!";
      } else if (model.getWinner() == null) {
        winner = "Draw!";
      }
      JOptionPane.showMessageDialog(this, "Game Over!" + "\n"
              + winner);

    }
    countGameOverWindow--;
  }


  @Override
  public void errorMessage() {
    JOptionPane.showMessageDialog(this, "invalid move");
  }

  @Override
  public JPanel getJPanel() {
    // this panel need to extend JPanel.
    return (JPanel) this.panel;
  }



  @Override
  public boolean getUserClicked() {
    return this.userClicked;
  }

  @Override
  public boolean getOnCommand() {
    return this.onCommand;
  }



  @Override
  public void keyTyped(KeyEvent e) {
    // ignored
  }

  @Override
  public void keyPressed(KeyEvent e) {
    // ignored
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // ignored
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // ignored
  }


  @Override
  public void mouseEntered(MouseEvent e) {
    // ignored
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // ignored
  }


}
