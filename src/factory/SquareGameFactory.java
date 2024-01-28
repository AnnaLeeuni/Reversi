package factory;

import model.BoardWithNotifier;
import model.SquareReversiModel;
import view.IGUIView;
import view.ViewSquareBoard;
import controller.BoardController;
import controller.player.IPlayer;

/**
 * return classes based on the square game mode.
 */
public class SquareGameFactory implements IGameFactory {

  @Override
  public BoardWithNotifier createBoard() {
    return new BoardWithNotifier(new SquareReversiModel(10));
  }

  @Override
  public IGUIView createView(BoardWithNotifier board) {
    return new ViewSquareBoard(board);
  }

  @Override
  public BoardController createController(IPlayer player, BoardWithNotifier board, IGUIView view) {
    return new BoardController(player, board, view);
  }
}

