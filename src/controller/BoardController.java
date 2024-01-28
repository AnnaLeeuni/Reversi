package controller;

import java.awt.geom.Point2D;
import java.util.Objects;

import controller.player.IPlayer;
import controller.player.PlayerActionListener;
import model.BoardLocation;
import model.INotifierBoard;
import model.ModelStatusListener;
import view.IGUIView;

/**
 * The BoardController class serves as the intermediary between the model and the view
 * for the Reversi game. It handles the game logic and updates the view based on the state
 * of the game model. It also processes player actions and updates the model accordingly.
 */
public class BoardController implements ModelStatusListener, PlayerActionListener, IController {

  // the player of the current controller
  private final IPlayer player;

  // a view showing the game from that player’s perspective.
  protected final IGUIView view;

  //private final List<ControllerListener> controllerListeners = new ArrayList<>();

  // a model of the game to be passed in
  protected final INotifierBoard model;

  private boolean userClicked = false;

  // store the current location of user clicked.
  private Point2D clickedLocation;

  // store the location of the currentActiveTile
  private BoardLocation currentActiveTile;


  /**
   * Constructs a new BoardController with the specified player, model, and view.
   *
   * @param player The player associated with this controller.
   * @param model  The game model to be controlled.
   * @param view   The view to be updated based on the game state.
   */
  public BoardController(IPlayer player, INotifierBoard model, IGUIView view) {
    this.player = Objects.requireNonNull(player);
    this.view = Objects.requireNonNull(view);
    this.model = Objects.requireNonNull(model);


    view.setVisible(true);

    // initiate the title of the view
    view.initiateTitle(player.getPlayerType());

    // link the player with view
    view.addFeatures(player);
    player.addListener(this);

    // register the controller as the listener of model
    model.addListener(this);

  }


  /**
   * Processes the event of a round being skipped. This method is called when the current
   * player has no legal moves and decides to pass their turn. The view is updated to
   * reflect this change, and the controller checks if it's the associated player's turn
   * to take action.
   */
  @Override
  public void onSkipRound() {
    // refresh the view
    view.repaintView();

    // make sure player can only action when it's their turn
    boolean turnForCurrentPlayer = false;
    try {
      turnForCurrentPlayer = model.getTurns() == player.getPlayerType();
    } catch (IllegalStateException ignored) {
    }
    if (turnForCurrentPlayer) {

      view.updateTitle();
      view.repaintView();
      // remind the player to take an action
      player.takeAction();

    }
  }

  /**
   * Handles the event when the game is over. This can be triggered by the game reaching
   * a conclusion where no more moves are possible or one player has won. The view is updated
   * to show the game over state, and a message is displayed to the players indicating the
   * end of the game.
   */
  @Override
  public void onGameOver() {
    view.repaintView();
    view.gameOverMessage();
    view.updateTitle();
  }

  /**
   * Responds to the event that the game has started. The controller will check if it's the
   * turn of the associated player to take an action and prompt the player if it is. This method
   * ensures that the player is aware the game has begun and is ready to proceed with their move.
   */
  @Override
  public void onGameStart() {

    // make sure player can only action when it's their turn
    boolean turnForCurrentPlayer = false;
    try {
      turnForCurrentPlayer = model.getTurns() == player.getPlayerType();
    } catch (IllegalStateException ignored) {
    }
    if (turnForCurrentPlayer) {

      // remind the player to take an action
      player.takeAction();
    }
  }

  /**
   * Responds to the event of a disc being placed on the board. If it's the associated player's
   * turn, the controller prompts the player to take action. This ensures that the game flow
   * continues and that the player is given the opportunity to make a move when it's their turn.
   */
  @Override
  public void onPlaceDisc() {

    // refresh the view
    view.repaintView();

    // make sure player can only action when it's their turn
    boolean turnForCurrentPlayer = false;
    try {
      turnForCurrentPlayer = model.getTurns() == player.getPlayerType();
    } catch (IllegalStateException ignored) {
    }
    if (turnForCurrentPlayer) {
      view.updateTitle();
      view.repaintView();

      // remind the player to take an action
      player.takeAction();
    }
  }


  /**
   * Processes the specified command from the player.
   * If the command is "Rest", it is ignored. If the command is "P", the model is instructed
   * to skip the round. Otherwise, it processes the command as a move and updates the model.
   *
   * @param command The command to process.
   */
  @Override
  public void actionPerformed(String command) {
    // make sure player can only action when it's their turn
    boolean turnForCurrentPlayer = false;
    try {
      turnForCurrentPlayer = model.getTurns() == player.getPlayerType();
    } catch (IllegalStateException e) {
      view.repaintView();
    }

    if (turnForCurrentPlayer) {
      switch (command) {
        case "Rest":
          //ignored
          break;
        case "P":
          this.skipRound();

          view.updateTitle();
          view.repaintView();
          break;

        default:
          // System.out.println(player.getPlayerType() + "command: " + command);
          String[] loc = command.split(" ");

          try {

            this.moveDisc((int) Double.parseDouble(loc[0]), (int) Double.parseDouble(loc[1]));

          } catch (IllegalStateException e) {
            if (e.getMessage().contains("no possible move at this moment")) {
              view.notifyToPass();
            }
            System.out.println("Error command: " + this.player.getPlayerType() + command);
            System.out.println(model.gameOver());
            view.errorMessage();
          }

          // update the view
          view.updateTitle();
          view.repaintView();
      }
    }
  }

  /**
   * Places a disc at the specified location on the game board.
   * This method updates the game model by placing a disc for the current player at the given
   * coordinates.
   * It is called in response to a player's action and ensures that the game state is updated
   * accordingly.
   *
   * @param rowNumber The row number where the disc is to be placed, 0-based.
   * @param rowIndex  The column index where the disc is to be placed, 0-based.
   * @throws IllegalStateException If the move is illegal or if it's not the current player's turn.
   */
  @Override
  public void moveDisc(int rowNumber, int rowIndex) {
    this.model.placeDisc(rowNumber, rowIndex);
  }

  /**
   * Instructs the game model to skip the current player's turn.
   * This method is invoked when a player decides to pass their turn, usually because they have no
   * legal moves available.
   * It updates the game model to reflect the skipped turn and moves to the next player.
   *
   * @throws IllegalStateException If it's not the current player's turn or if the game state
   *                               doesn't allow skipping a turn.
   */
  @Override
  public void skipRound() {
    model.skipRound();
  }
}
