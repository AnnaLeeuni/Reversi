
import controller.strategy.AIEasyStrategy;
import controller.strategy.AIHardStrategy;
import controller.strategy.AIMedianStrategy;
import controller.BoardController;
import model.BoardWithNotifier;
import model.DiscType;

import view.IGUIView;
import controller.player.IPlayer;
import controller.player.HumanPlayer;
import controller.player.MachinePlayer;
import view.ViewBoardWithHint;

import factory.IGameFactory;
import factory.SquareGameFactory;
import factory.HexagonalGameFactory;

/**
 * Main class to run the GUI view version of the game reversi.
 * Can customize to set the desired board size.
 */
public class ReversiMain {

  // the first disc should be black
  private static final DiscType firstBlack = DiscType.BLACK;

  // the second piece is white
  private static final DiscType secondWhite = DiscType.WHITE;

  // whether the black disc has already been chosen
  private static boolean blackTakenFirst = false;

  // automatically pick the disc type for player
  private static DiscType chooseDisctype() {
    if (!blackTakenFirst) {
      blackTakenFirst = true;
      return firstBlack;

    } else {
      return secondWhite;
    }
  }

  /**
   * factory method to create player.
   *
   * @param type  what type of player want to get created
   * @param model the model the  game is using
   */
  private static IPlayer createPlayer(String type, BoardWithNotifier model) {
    switch (type) {
      case "human":
        return new HumanPlayer(chooseDisctype(), model);
      case "strategy1":
        return new MachinePlayer(chooseDisctype(), new AIEasyStrategy(), model);// Example strategy
      case "strategy2":
        return new MachinePlayer(chooseDisctype(), new AIMedianStrategy(), model);
      case "strategy3":
        return new MachinePlayer(chooseDisctype(), new AIHardStrategy(), model);
      default:
        throw new IllegalArgumentException("Unknown player type: " + type);
    }
  }

  /**
   * run the game, set the board size as 7, and pass in the board to a view.
   *
   * @param args args.
   */
  public static void main(String[] args) {

    if (args.length < 3) {
      throw new IllegalArgumentException("should have at least 3 arguments: board type, " +
              "player1 type, player2 type");
    }

    String boardType = args[0];
    IGameFactory gameFactory;

    switch (boardType) {
      case "square":
        gameFactory = new SquareGameFactory();
        break;
      case "hexagonal":
        gameFactory = new HexagonalGameFactory();
        break;
      default:
        throw new IllegalArgumentException("Invalid board type: " + boardType);
    }

    BoardWithNotifier board = gameFactory.createBoard();

    String withHint = args[1];

    IGUIView viewPlayer1 = gameFactory.createView(board);
    IGUIView viewPlayer2 = gameFactory.createView(board);

    if (withHint.equals("hint")) {
      viewPlayer1 = new ViewBoardWithHint(viewPlayer1);
      viewPlayer2 = new ViewBoardWithHint(viewPlayer2);
    } else if (withHint.equals("noHint")) {
      // ignore
    } else {
      throw new IllegalArgumentException("unknown command");
    }

    String player1Type = args[2];
    String player2Type = args[3];
    IPlayer player1 = createPlayer(player1Type, board);
    IPlayer player2 = createPlayer(player2Type, board);

    BoardController controller1 = gameFactory.createController(player1, board, viewPlayer1);
    BoardController controller2 = gameFactory.createController(player2, board, viewPlayer2);

    board.startGame();
  }
}

