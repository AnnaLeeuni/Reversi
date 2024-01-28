package view.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.JPanel;


import model.BoardLocation;
import model.DiscType;
import model.ReadonlyReversiModel;
import view.singletile.ISingleTile;

/**
 * This class extends JPanel to render a collection of hexagons representing a game board.
 * It is designed to interact with mouse and keyboard inputs to handle game actions such as
 * selecting a tile or skipping a turn.
 */
public class HexagonPanel extends JPanel implements IBoardPanel {

  // represent the board which is a list of lists of hexagons
  private List<List<ISingleTile>> hexPile;

  // represent the length of the edge of a single hexagon
  // if the user clicked the tile or not.
  private boolean userClicked = false;

  // store the current location of user clicked.
  private Point2D clickedLocation;

  // store the location of the currentActiveTile
  private BoardLocation currentActiveTile;

  Map<Point2D, Boolean> highLightInformation = new HashMap<>();

  private final ReadonlyReversiModel model;

  private DiscType playerType;


  /**
   * Constructs a HexagonPanel with a given model and a list of hexagon representations.
   *
   * @param model   The game model to render
   * @param hexPile A list of lists representing the hexagonal tiles of the game board
   */
  public HexagonPanel(ReadonlyReversiModel model, List<List<ISingleTile>> hexPile) {
    super();
    // the model that used for render
    this.model = Objects.requireNonNull(model);
    List<List<DiscType>> pile = this.model.getPile();
    this.setBackground(Color.DARK_GRAY);

    this.hexPile = hexPile;

    // set up the mouse listener
    //this.addMouseListener(this);

    // set up the key listener
    //this.addKeyListener(this);
  }


  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    int row = 0;
    while (row < hexPile.size()) {
      int index = 0;
      while (index < hexPile.get(row).size()) {
        hexPile.get(row).get(index).draw(g2d);

        if (highLightInformation.get(new Point2D.Double(row, index)) != null
                && highLightInformation.get(new Point2D.Double(row, index))) {
          hexPile.get(row).get(index).highLight(g2d);
        }
        index++;
      }
      row++;
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
  public void keyTyped(KeyEvent e) {
    //not used
  }

  @Override
  public void keyPressed(KeyEvent e) {
    //not used
  }



  /**
   * Resets the highlighting information for all hexagons.
   */
  @Override
  public void resetHighLight() {
    for (Map.Entry<Point2D, Boolean> entry : highLightInformation.entrySet()) {
      entry.setValue(false);
    }
  }

  /**
   * Handles key release events for game actions.
   * For example, pressing 'P' for pass or 'Space' to select a tile.
   *
   * @param e The KeyEvent associated with the key release
   */
  @Override
  public void keyReleased(KeyEvent e) {
    // appendable = new StringBuilder();

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

  /**
   * Repaints the hexagonal tiles with updated information.
   *
   * @param hexPile The updated list of hexagons to be repainted
   */
  @Override
  public void repaintBoard(List<List<ISingleTile>> hexPile) {
    this.hexPile = hexPile;
    super.repaint();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    //not used
  }

  @Override
  public void mousePressed(MouseEvent e) {
    //not used
  }

  /**
   * Handles mouse release events to detect clicks on hexagonal tiles.
   *
   * @param e The MouseEvent associated with the mouse release
   */
  @Override
  public void mouseReleased(MouseEvent e) {

    Point physicalP = e.getPoint();
    double mouseXco = physicalP.getX();
    double mouseYco = physicalP.getY();
    physicalP.setLocation(mouseXco, mouseYco);
    boolean everDetected = false;
    int hexagonPileNumber = 0;
    while (hexagonPileNumber < hexPile.size()) {
      int index = 0;

      // loop over every hexagon in the hexagon list
      while (index < hexPile.get(hexagonPileNumber).size()) {

        int[] xPoints = hexPile.get(hexagonPileNumber).get(index).getxPoints();
        int[] yPoints = hexPile.get(hexagonPileNumber).get(index).getyPoints();
        // if the location of mouse is the same of the location of hexagon
        if (new Polygon(xPoints, yPoints, xPoints.length).contains(physicalP)) {

          // clicked inside the hexagon
          everDetected = true;
          // print the location that got clicked.
          System.out.println(hexPile.get(hexagonPileNumber).get(index).getLogicalLocation());

          if (!hexPile.get(hexagonPileNumber).get(index).getDisc().equals(DiscType.EMPTY)) {
            return;
          }

          // if user haven't clicked the board
          if (!userClicked) {
            hexPile.get(hexagonPileNumber).get(index).onClick();
            //hexPile.get(hexagonPileNumber).get(index).highLight();
            highLightInformation.put(new Point2D.Double(hexagonPileNumber, index), true);

            // store the tile data
            currentActiveTile = new BoardLocation(hexagonPileNumber, index);
            userClicked = true;
          }
          // if the user is already clicked before
          else if (clickedLocation.getX() != hexagonPileNumber
                  || clickedLocation.getY() != index) {
            // hexPile.get(hexagonPileNumber).get(index).onClick();

            highLightInformation.put(new Point2D.Double(hexagonPileNumber, index), true);


            // store the data of the clicked tile
            currentActiveTile = new BoardLocation(hexagonPileNumber, index);

            // stop the highlight of the previous click
            //hexPile.get((int) clickedLocation.getX()).get((int) clickedLocation.getY()).onClick();
            highLightInformation.put(new Point2D.Double(clickedLocation.getX(),
                    clickedLocation.getY()), false);

            userClicked = true;
          }
          // if the user clicked the same tile as before
          else {
            // hexPile.get((int)clickedLocation.getX()).get((int) clickedLocation.getY()).onClick();

            highLightInformation.put(new Point2D.Double(clickedLocation.getX(),
                    clickedLocation.getY()), false);

            userClicked = false;
          }
          repaint();
          clickedLocation = new Point(hexagonPileNumber, index);
        }

        index++;
      }
      hexagonPileNumber++;
    }
    if (!everDetected && userClicked) {
      System.out.println(currentActiveTile);
      userClicked = false;
      //hexPile.get(currentActiveTile.getRow()).get(currentActiveTile.getIndex()).onClick();
      highLightInformation.put(new Point2D.Double(currentActiveTile.getRow(),
              currentActiveTile.getIndex()), false);

      repaint();
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    //not used
  }

  @Override
  public void mouseExited(MouseEvent e) {
    //not used
  }

}
