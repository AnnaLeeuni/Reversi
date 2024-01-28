package view.singletile;

import java.awt.Graphics;
import java.awt.geom.Point2D;

import model.DiscType;

/**
 * represent a single hexagon.
 */
public interface ISingleTile {

  /**
   * render the current hexagon on the given graphic.
   * @param g represent the canvas
   */
  public void draw(Graphics g);


  /**
   * get the logical position of the hexagon.
   */
  public Point2D getLogicalLocation();

  /**
   * if the player clicked the current hexagon, do some mutation about the hexagon.
   */
  public void onClick();

  /**
   * get the disc on the current hexagon.
   */
  public DiscType getDisc();

  /**
   * set the current hexagon as the given type.
   * @param type the disc placed on the hexagon
   */
  public void setType(DiscType type);

  public void highLight(Graphics g);

  public int getSize();

  public void setSize(int sideSize);

  public int[] getxPoints();

  public int[] getyPoints();

}
