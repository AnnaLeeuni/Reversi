package view.singletile;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Point;


import java.util.Objects;

import model.DiscType;

/**
 * represent a single hexagon on the board.
 */
public class SingleHexagon implements ISingleTile {
  // represent the row number for the current hexagon
  private final int rowNumber;

  // represent the index at that row.
  private final int index;

  // length of the edge of a single hexagon
  private int width;

  // y coordinate of the hexagon of the top most point
  private int startYCo;

  // x coordinate of the hexagon of the top most point
  private int startXco;

  // addWidth is the length of the boarder to the midline
  private int addWidth;

  // the disc on the hexagon,
  // if there's no disc on it, the type is Empty.
  private DiscType player;

  private int[] xPoints;

  private int[] yPoints;

  private boolean clicked = false;

  private int indent;

  /**
   * a single hexagon takes in its coordinate on the board.
   *
   * @param rowNumber the row number
   * @param index     the index at that row
   */
  public SingleHexagon(int rowNumber, int index, int width, int indent,
                       DiscType player, int[] xPoints, int[] yPoints) {

    this.rowNumber = rowNumber;
    this.index = index;
    this.width = width;
    this.player = Objects.requireNonNull(player);
    this.indent = indent;

    addWidth = (int) ((width / 2) * Math.sqrt(3));
    int indentation = indent * addWidth;

    // represent the first point to start drawing hexagon
    startYCo = rowNumber * (width + width / 2);
    startXco = index * (addWidth * 2) + indentation + addWidth;

    // represent index
    this.xPoints = new int[]{startXco, startXco + addWidth,
        startXco + addWidth, startXco, startXco - addWidth, startXco - addWidth};

    // represent rows
    this.yPoints = new int[]{startYCo, startYCo + width / 2,
        startYCo + width / 2 + width, startYCo + width + width,
        startYCo + width / 2 + width, startYCo + width / 2};
  }


  @Override
  public void draw(Graphics g) {
    // create a copy of the graphic
    Graphics2D g2d = (Graphics2D) g.create();


    addWidth = (int) ((width / 2) * Math.sqrt(3));
    int indentation = indent * addWidth;

    // represent the first point to start drawing hexagon
    startYCo = rowNumber * (width + width / 2);
    startXco = index * (addWidth * 2) + indentation + addWidth;

    // represent index
    this.xPoints = new int[]{startXco, startXco + addWidth,
        startXco + addWidth, startXco, startXco - addWidth, startXco - addWidth};

    // represent rows
    this.yPoints = new int[]{startYCo, startYCo + width / 2,
        startYCo + width / 2 + width, startYCo + width + width,
        startYCo + width / 2 + width, startYCo + width / 2};

    g2d.setColor(Color.lightGray);

    g2d.fillPolygon(xPoints, yPoints, xPoints.length);
    g2d.setColor(Color.black);
    g2d.drawPolygon(xPoints, yPoints, xPoints.length);

    // render the disc
    if (player.equals(DiscType.BLACK)) {
      g2d.setColor(Color.BLACK);
      g2d.fillOval(startXco - addWidth / 2, startYCo + addWidth / 2, width - 3, width - 3);
    } else if (player.equals(DiscType.WHITE)) {
      g2d.setColor(Color.white);
      g2d.fillOval(startXco - addWidth / 2 - 2, startYCo + addWidth / 2 + 2, width - 3, width - 3);
    }
  }


  @Override
  public Point2D getLogicalLocation() {
    return new Point(rowNumber, index);
  }

  @Override
  public void onClick() {
    this.clicked = !clicked;
  }

  /**
   * highlight the current hexagon.
   */
  public void highLight(Graphics g) {
    // create a copy of the graphic
    Graphics2D g2d = (Graphics2D) g.create();

    // if the user clicked the hexagon, the background should highlight
    g2d.setColor(Color.cyan);

    g2d.fillPolygon(xPoints, yPoints, xPoints.length);
    g2d.setColor(Color.black);
    g2d.drawPolygon(xPoints, yPoints, xPoints.length);
  }

  @Override
  public int getSize() {
    return width;
  }

  @Override
  public void setSize(int sideSize) {
    this.width = sideSize;
  }

  @Override
  public DiscType getDisc() {
    return this.player;
  }

  @Override
  public void setType(DiscType type) {
    this.player = type;
  }

  @Override
  public int[] getxPoints() {
    return this.xPoints;
  }

  @Override
  public int[] getyPoints() {
    return this.yPoints;
  }
}
