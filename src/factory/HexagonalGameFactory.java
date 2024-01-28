package factory;

import model.BasicBoard;
import model.BoardWithNotifier;
import view.IGUIView;
import view.ViewBoard;
import controller.BoardController;
import controller.player.IPlayer;

/**
 * factory class of making a board.
 */
public class HexagonalGameFactory implements IGameFactory {

  @Override
  public BoardWithNotifier createBoard() {
    return new BoardWithNotifier(new BasicBoard(11));
  }

  @Override
  public IGUIView createView(BoardWithNotifier board) {
    return new ViewBoard(board);
  }

  @Override
  public BoardController createController(IPlayer player, BoardWithNotifier board, IGUIView view) {
    return new BoardController(player, board, view);
  }
}

