package view;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.JOptionPane;


import model.BoardLocation;
import model.DiscType;
import model.ReadonlyReversiModel;
import view.panel.IBoardPanel;
import view.panel.SquarePanel;
import view.singletile.ISingleTile;
import view.singletile.SingleSquare;

/**
 * Represents the graphical user interface for rendering a square-based Reversi game board.
 * This class extends AViewBoard and implements IGUIView.
 */
public class ViewSquareBoard extends AViewBoard implements IGUIView {

  private List<List<ISingleTile>> squarePile;

  // The JPanel on the frame
  protected IBoardPanel panel;

  // the width of the side of a square
  private int squareSideLength = 35;

  private final List<PlayerFeatures> playerFeatures = new ArrayList<>();


  // if the user clicked the tile or not.
  //private boolean userClicked = false;

  // store the current location of user clicked.
  //private Point2D clickedLocation;

  // store the location of the currentActiveTile
  private BoardLocation currentActiveTile;

  // the piles on the model
  //private final List<List<DiscType>> piles;

  // the model to get rendered
  private final ReadonlyReversiModel model;
  private int tileSize = 40;

  private int countGameOverWindow = 1;

  // the text for the current on the command state
  private String onCommandText;

  // the text for the current on the waiting state
  private String waitCommandText;

  // if it is the player with current view's turn.
  private boolean onCommand;

  private DiscType playerType;


  /**
   * Constructs a ViewSquareBoard object with the specified game model.
   *
   * @param model The game model to render in the view.
   */
  public ViewSquareBoard(ReadonlyReversiModel model) {
    super(model);


    this.model = model;
    int height = model.getHeight();
    int defaultWidth = height * 45 * 2;
    int defaultHeight = height * 45 * 2;

    // default size of the JFrame
    setPreferredSize(new Dimension(defaultWidth,
            defaultHeight));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    //updateSquareSideLength(defaultHeight);

    // Set up the panel for squares instead of hexagons
    squarePile = initiateBoard(model.getPile());
    panel = new SquarePanel(model, squarePile);
    this.add((JPanel) panel);

    panel.addMouseListener(this);
    panel.addKeyListener(this);
    this.addKeyListener(this);
    //this.addMouseListener(this);


    // Listen to the resizing request from the user
    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        updateSideLength(getWidth(), getHeight());
        resizingPiles();
        repaint();
      }
    });
    pack();
  }

  /**
   * Initializes the board with square tiles based on the given piles.
   *
   * @param piles The configuration of the board.
   * @return A list of lists representing the square tiles on the board.
   */
  @Override
  public List<List<ISingleTile>> initiateBoard(List<List<DiscType>> piles) {
    List<List<ISingleTile>> squarePile = new ArrayList<>();

    for (int row = 0; row < piles.size(); row++) {
      List<ISingleTile> squareRow = new ArrayList<>();
      for (int col = 0; col < piles.get(row).size(); col++) {
        DiscType disc = piles.get(row).get(col);
        SingleSquare square = new SingleSquare(row, col, this.tileSize, disc);
        squareRow.add(square);
      }
      squarePile.add(squareRow);
    }

    return squarePile;
  }


  /**
   * Updates the square pile to reflect the current state of the game model.
   *
   * @param squarePile The current list of lists of SingleSquare objects.
   * @return The updated list of lists of SingleSquare objects.
   */
  private List<List<ISingleTile>> updateSquarePile(List<List<ISingleTile>> squarePile) {
    List<List<DiscType>> piles = model.getPile();

    for (int row = 0; row < piles.size(); row++) {
      List<DiscType> discRow = piles.get(row);
      List<ISingleTile> squareRow = squarePile.get(row);

      for (int col = 0; col < discRow.size(); col++) {
        DiscType disc = discRow.get(col);
        ISingleTile square = squareRow.get(col);

        // Update the disc type of the square based on the model
        if (disc != DiscType.EMPTY) {
          square.setType(disc);
        }
      }
    }

    return squarePile;
  }


  /**
   * Updates the dimensions of the board based on the size of the window for resizing.
   *
   * @param width  The new width of the window.
   * @param height The new height of the window.
   */
  @Override
  public void updateSideLength(int width, int height) {
    // Assuming a square board for simplicity
    int shorterSide = Math.min(width, height);
    this.squareSideLength = shorterSide / model.getWidth();
    System.out.println("size: " + squareSideLength);
  }

  /**
   * updates all the hexagons to the given size.
   */
  @Override
  public void resizingPiles() {
    for (List<ISingleTile> ls : squarePile) {
      for (ISingleTile s : ls) {
        s.setSize(squareSideLength);
      }
    }
  }

  @Override
  public DiscType getPlayerType() {
    return this.playerType;
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // if the user clicked the tile or not.
    boolean userClicked = false;

    // store the current location of user clicked.
    Point2D clickedLocation;

    // Check if the game is over first
    if (model.gameOver()) {
      // Ignore any input if the game is already over
      return;
    }

    // Check for the 'P' key to pass the turn
    if (Character.toUpperCase(e.getKeyChar()) == KeyEvent.VK_P) {
      System.out.println("Pass");
      for (PlayerFeatures feature : playerFeatures) {
        feature.tryToSkip();
        break;
      }
    }
    clickedLocation = panel.getClickedLocation();
    userClicked = panel.getUserClicked();

    // Check for the 'Space' key to confirm the move
    if (Character.toUpperCase(e.getKeyChar()) == KeyEvent.VK_SPACE && userClicked) {
      // Assuming you have a method to convert clicked location to row and col
      int row = (int) clickedLocation.getX();
      int col = (int) clickedLocation.getY();

      for (PlayerFeatures feature : playerFeatures) {
        System.out.println("this");

        feature.tryToPlace(row + " " + col);
        break;
      }

      // Reset user clicked state
      userClicked = false;
      panel.resetHighLight(); // Reset highlights on the panel
    }
  }


  @Override
  public void replaceJPanel(IBoardPanel replacePanel) {
    if (!(panel instanceof JPanel)) {
      throw new IllegalArgumentException("hexagonPanel need to implement JPanel");
    }
    this.panel = replacePanel;
  }

  @Override
  public void notifyToPass() {
    JOptionPane.showMessageDialog(this, "you need to pass");
  }

  @Override
  public void initiateTitle(DiscType playerType) {
    onCommandText = "reversi" + " " + playerType.toWord() + ": On Command";
    waitCommandText = "reversi" + " "
            + playerType.toWord() + ": Waiting for another player";
    if (playerType == DiscType.BLACK) {
      this.playerType = DiscType.BLACK;
      panel.setPlayerType(this.playerType);
      setTitle(onCommandText);
      onCommand = true;
    } else {
      this.playerType = DiscType.WHITE;
      panel.setPlayerType(this.playerType);
      setTitle(waitCommandText);
      onCommand = false;
    }
  }

  @Override
  public void updateTitle() {
    if (model.gameOver()) {
      setTitle("reversi Game End");
    } else if (onCommand) {
      setTitle(waitCommandText);
      onCommand = false;
    } else {
      setTitle(onCommandText);
      onCommand = true;
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    panel.mouseReleased(e);
  }

  @Override
  public void addFeatures(PlayerFeatures playerFeatures) {
    this.playerFeatures.add(playerFeatures);
  }

  @Override
  public void repaintView() {
    this.panel.repaintBoard(updateSquarePile(squarePile));
    //this.repaintSquare();
  }

  @Override
  public IBoardPanel getHexPanel() {
    return this.panel;
  }

  @Override
  public ReadonlyReversiModel getModel() {
    return this.model;
  }

}
