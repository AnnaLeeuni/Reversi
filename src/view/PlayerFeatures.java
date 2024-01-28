package view;

/**
 * listen to the click on the tiles.
 */
public interface PlayerFeatures {

  /**
   * transmit the place command from view to controller.
   */
  public void tryToPlace(String event);

  /**
   * transmit the skip round command from view to controller.
   */
  public void tryToSkip();
}
