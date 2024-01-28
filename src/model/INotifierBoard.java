package model;

/**
 * interface for board with ability to notify its listeners.
 */
public interface INotifierBoard extends IBoard {

  /**
   * add a new listener to the current model.
   */
  public void addListener(ModelStatusListener modelListener);

  /**
   * notify the listeners of the model.
   */
  public void notifyListener(String command);

}
