package view.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import model.BoardLocation;
import model.DiscType;
import model.ReadonlyReversiModel;
import view.singletile.ISingleTile;

/**
 * render the square board as in JPanel on the frame.
 */
public class SquarePanel extends JPanel implements IBoardPanel {

  private ReadonlyReversiModel model;

  private List<List<ISingleTile>> squarePile;

  private boolean userClicked = false;
  private Point2D clickedLocation;
  private BoardLocation currentActiveTile;

  private DiscType playerType;

  private Map<Point2D, Boolean> highLightInformation = new HashMap<>();

  /**
   * to render a board, the user need to pass in a model and a list of lists of tiles.
   */
  public SquarePanel(ReadonlyReversiModel model, List<List<ISingleTile>> squarePile) {
    super();
    this.model = model;
    this.setBackground(Color.DARK_GRAY);
    this.squarePile = squarePile;

    // set up the mouse listener
    //this.addMouseListener(this);

    // set up the key listener
    //this.addKeyListener(this);
  }


  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    for (int row = 0; row < squarePile.size(); row++) {
      for (int col = 0; col < squarePile.get(row).size(); col++) {
        squarePile.get(row).get(col).draw(g2d);

        if (highLightInformation.getOrDefault(new Point2D.Double(row, col), false)) {
          squarePile.get(row).get(col).highLight(g2d);
        }
      }
    }
  }

  @Override
  public ReadonlyReversiModel getModel() {
    return this.model;
  }

  @Override
  public void setPlayerType(DiscType playerType) {
    this.playerType = playerType;
  }

  @Override
  public DiscType getPlayerType() {
    return this.playerType;
  }


  @Override
  public Point2D getClickedLocation() {
    return clickedLocation;
  }

  @Override
  public boolean getUserClicked() {
    return userClicked;
  }

  @Override
  public void resetHighLight() {
    for (Map.Entry<Point2D, Boolean> entry : highLightInformation.entrySet()) {
      entry.setValue(false);
    }
  }

  @Override
  public void repaintBoard(List<List<ISingleTile>> hexPile) {
    this.squarePile = hexPile;
    repaint();
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
  public void keyReleased(KeyEvent e) {

    if (Character.toUpperCase(e.getKeyChar()) == KeyEvent.VK_P) {
      System.out.println("pass");
    }

    if (Character.toUpperCase(e.getKeyChar()) == KeyEvent.VK_SPACE
            && userClicked && clickedLocation != null) {
      StringBuilder point = new StringBuilder();
      double xPoint = clickedLocation.getX();
      double yPoint = clickedLocation.getY();
      point.append("x=").append(xPoint).append(", y=").append(yPoint);
      System.out.println("Move to " + point);

      // no highlight on the tile
      highLightInformation.put(new Point2D.Double(xPoint, yPoint), false);
      repaint();
    }
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
  public void mouseReleased(MouseEvent e) {
    Point physicalP = e.getPoint();
    boolean everDetected = false;
    int rowNumber = 0; // Renamed for clarity

    while (rowNumber < squarePile.size()) {
      int colIndex = 0; // Renamed for clarity
      int tileSize = squarePile.get(0).get(0).getSize();

      while (colIndex < squarePile.get(rowNumber).size()) {
        int xCo = tileSize * rowNumber; // Use colIndex for xCo
        int yCo = tileSize * colIndex; // Use rowIndex for yCo

        // Tile detection condition
        if (physicalP.getX() > xCo && physicalP.getX() < xCo + tileSize
                && physicalP.getY() > yCo && physicalP.getY() < yCo + tileSize) {
          everDetected = true;
          System.out.println("Logical Location: (" + rowNumber + ", " + colIndex + ")");

          if (!squarePile.get(rowNumber).get(colIndex).getDisc().equals(DiscType.EMPTY)) {
            return;
          }
          clickedLocation = new Point2D.Double(rowNumber, colIndex);
          System.out.println(clickedLocation);
          handleTileClick(rowNumber, colIndex); // Pass rowIndex, colIndex

          System.out.println(clickedLocation);
          repaint();
        }

        colIndex++;
      }
      rowNumber++;
    }

    if (!everDetected && userClicked) {
      handleDeselection();
      repaint();
    }
  }

  @Override
  public void repaint() {
    super.repaint();
  }


  private void handleTileClick(int row, int col) {
    System.out.println("handleTileClick: row=" + row + ", col=" + col);

    if (userClicked && currentActiveTile.getRow() == row && currentActiveTile.getIndex() == col) {
      highLightInformation.put(new Point2D.Double(row, col), false); // Corrected order
      userClicked = false;
    } else {
      if (userClicked) {
        highLightInformation.put(new Point2D.Double(currentActiveTile.getRow(),
                currentActiveTile.getIndex()), false);
      }

      highLightInformation.put(new Point2D.Double(row, col), true); // Corrected order
      currentActiveTile = new BoardLocation(row, col);
      userClicked = true;
    }
  }


  private void handleDeselection() {
    // Only proceed if a tile was previously clicked
    if (userClicked) {
      // Unhighlight the previously clicked tile
      highLightInformation.put(new Point2D.Double(currentActiveTile.getIndex(),
              currentActiveTile.getRow()), false);

      // Reset click state
      userClicked = false;
    }
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
