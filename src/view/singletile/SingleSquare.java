package view.singletile;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;

import java.awt.geom.Point2D;

import model.DiscType;

/**
 * represent a single tile on the square board.
 */
public class SingleSquare implements ISingleTile {
  private final int rowNumber;

  private final int index;

  private int size;

  private DiscType type;

  private boolean clicked = false;

  private int[] xPoints;

  private int[] yPoints;

  /**
   * to represent a single square on the game board, need its rowNumber, index in that row
   * width and what kind of disc it is.
   */
  public SingleSquare(int rowNumber, int index, int size, DiscType disc) {
    this.rowNumber = rowNumber;
    this.index = index;
    this.size = size;
    this.type = disc;

    this.xPoints = new int[]{rowNumber * size, rowNumber * size,
        rowNumber * size + size, rowNumber * size + size};

    this.yPoints = new int[]{index * size, index * size + size,
        index * size + size, index * size};


  }

  @Override
  public void draw(Graphics g) {
    int x = rowNumber * size;
    int y = index * size;

    Graphics2D g2d = (Graphics2D) g.create();

    // Draw the square
    g2d.setColor(Color.LIGHT_GRAY);
    g2d.fillRect(x, y, size, size);
    g2d.setColor(Color.BLACK);
    g2d.drawRect(x, y, size, size);

    // Draw the disc if one is present
    if (type != null && type != DiscType.EMPTY) {
      g2d.setColor(type == DiscType.BLACK ? Color.BLACK : Color.WHITE);
      g2d.fillOval(x + size / 4, y + size / 4, size / 2, size / 2);
    }

    g2d.dispose(); // Clean up the graphics context
  }

  /**
   * Highlights the current square tile.
   *
   * @param g the graphics object used for drawing.
   */
  //@Override
  public void highLight(Graphics g) {
    // create a copy of the graphic
    Graphics2D g2d = (Graphics2D) g.create();

    // if the user clicked the hexagon, the background should highlight
    g2d.setColor(Color.cyan);

    g2d.fillRect(rowNumber * size, index * size, size, size);
    g2d.setColor(Color.black);
    //g2d.drawPolygon(xPoints, yPoints, xPoints.length);
  }


  // Getters and setters for size and disc
  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  @Override
  public int[] getxPoints() {
    return xPoints;
  }

  @Override
  public int[] getyPoints() {
    return yPoints;
  }


  // the same
  @Override
  public Point2D getLogicalLocation() {
    System.out.println("row: " + rowNumber);
    System.out.println("index: " + index);

    return new Point(rowNumber, index);
  }

  // the same
  @Override
  public void onClick() {
    this.clicked = !clicked;
  }

  @Override
  public DiscType getDisc() {
    return this.type;
  }

  @Override
  public void setType(DiscType type) {
    this.type = type;
  }


}
