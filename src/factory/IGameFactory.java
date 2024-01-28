package factory;

import model.BoardWithNotifier;
import view.IGUIView;
import controller.BoardController;
import controller.player.IPlayer;

/**
 * factory classes, help to creat classes.
 */
public interface IGameFactory {

  /**
   * create a board based on the current factory.
   */
  public BoardWithNotifier createBoard();

  /**
   * create a view based on the current factory.
   */
  public IGUIView createView(BoardWithNotifier board);

  /**
   * create a controller based on the current factory.
   */
  public BoardController createController(IPlayer player, BoardWithNotifier board, IGUIView view);
}
