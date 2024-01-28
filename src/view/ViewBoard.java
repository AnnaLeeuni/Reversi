package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


import javax.swing.WindowConstants;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import model.BoardLocation;
import model.DiscType;
import model.ReadonlyReversiModel;
import view.panel.HexagonPanel;
import view.panel.IBoardPanel;
import view.singletile.ISingleTile;

/**
 * render the board with GUI.s
 */
public class ViewBoard extends AViewBoard implements IGUIView {

  // represent the board which is a list of lists of hexagons
  private final List<List<ISingleTile>> hexPile;

  // if it is the player with current view's turn.
  private boolean onCommand;

  // The JPanel on the frame
  protected IBoardPanel panel;

  // the width of the line of the hexagon
  private int hexagonEdgeLength = 35;

  // the listener to receive updates
  // players should register themselves as listener
  private final List<PlayerFeatures> playerFeatures = new ArrayList<>();

  // represent the length of the edge of a single hexagon
  // if the user clicked the tile or not.
  private boolean userClicked = false;

  // store the current location of user clicked.
  private Point2D clickedLocation;

  // store the location of the currentActiveTile
  private BoardLocation currentActiveTile;

  // the piles on the model
  private final List<List<DiscType>> piles;

  // the model to get rendered
  private final ReadonlyReversiModel model;

  private int countGameOverWindow = 1;

  /**
   * help to determine if this is the first time setting the title.
   */
  private int countTitleAppearTimes = 0;

  // the text for the current on the command state
  private String onCommandText;

  // the text for the current on the waiting state
  private String waitCommandText;

  private DiscType playerType;

  /**
   * render the board as in GUI.
   *
   * @param model the model to get rendered
   */
  public ViewBoard(ReadonlyReversiModel model) {
    super(model);

    // the model that used for render
    piles = model.getPile();

    this.model = model;
    int height = model.getHeight();
    int defaultWidth = height * 45 * 2;
    int defaultHeight = height * 45 * 2;

    // default size of the JFrame
    setPreferredSize(new Dimension(defaultWidth,
            defaultHeight));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // initiate the length of a single hexagon
    updateSideLength(defaultWidth, defaultHeight);

    hexPile = initiateBoard(piles);

    panel = new HexagonPanel(model, hexPile);
    if (!(panel instanceof JPanel)) {
      throw new IllegalStateException("IPanel should extends JPanel");
    }
    this.add((JPanel) panel);
    this.addKeyListener(this);
    //this.addMouseListener(this);
    panel.addMouseListener(this);
    panel.addKeyListener(this);


    // listen to the resizing request from the user
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
   * use the given width and height of the board to calculate the border length
   * of a single hexagon.
   *
   * @param viewWidth  the width of the board
   * @param viewHeight the height of the board
   */
  @Override
  public void updateSideLength(int viewWidth, int viewHeight) {
    if (viewWidth <= viewHeight) {
      this.hexagonEdgeLength = (int) (((viewWidth / piles.get(0).size()) / 2) / Math.sqrt(3)) * 2;
    } else {
      int halfSize = (piles.size() - 1) / 2;
      if (halfSize % 2 == 0) {
        // 3* halfSize can get the height of the half of the board
        hexagonEdgeLength = viewHeight / ((halfSize / 2) * 3 * 2 + 1);
      } else {
        hexagonEdgeLength = viewHeight / ((((halfSize - 1) / 2 * 3) + 2) * 2 + 2);
      }
    }
  }


  /**
   * updates all the hexagons to the given size.
   */
  public void resizingPiles() {
    for (List<ISingleTile> ls : hexPile) {
      for (ISingleTile sh : ls) {
        sh.setSize(hexagonEdgeLength);
      }
    }
  }

  @Override
  public DiscType getPlayerType() {
    return this.playerType;
  }


  @Override
  public void addFeatures(PlayerFeatures playerFeatures) {
    this.playerFeatures.add(playerFeatures);
  }

  @Override
  public List<List<ISingleTile>> initiateBoard(List<List<DiscType>> piles) {
    return HexagonInitializer.initiateHexList(piles, hexagonEdgeLength);
  }

  /**
   * repaint the hexagon panel. Pop out message window if it is needed.
   *
   * @param hexPile the updated hexagons
   */
  private void repaintHex(List<List<ISingleTile>> hexPile) {
    this.panel.repaintBoard(hexPile);
  }


  @Override
  public void keyReleased(KeyEvent e) {
    if (model.gameOver()) {
      // ignore the command when the game is over
    } else {
      if (Character.toUpperCase(e.getKeyChar()) == KeyEvent.VK_P) {
        System.out.println("pass");
        for (PlayerFeatures cl : playerFeatures) {
          // inform the features to pass
          cl.tryToSkip();
          userClicked = false;
          break;
        }
      }
      clickedLocation = panel.getClickedLocation();
      userClicked = panel.getUserClicked();
      //panel.keyReleased(e);
      if (Character.toUpperCase(e.getKeyChar()) == KeyEvent.VK_SPACE
              && this.userClicked) {
        StringBuilder point = new StringBuilder();
        double xPoint = this.clickedLocation.getX();
        double yPoint = this.clickedLocation.getY();
        point.append("x=").append(xPoint).append(", y=").append(yPoint);
        System.out.println("Move to " + point);
        this.panel.resetHighLight();
        userClicked = false;

        for (PlayerFeatures cl : playerFeatures) {
          cl.tryToPlace(clickedLocation.getX() + " " + clickedLocation.getY());

        }
      }
    }
  }


  @Override
  public JPanel getJPanel() {
    return (JPanel) this.panel;
  }

  @Override
  public IBoardPanel getHexPanel() {
    return this.panel;
  }

  @Override
  public boolean getUserClicked() {
    return this.userClicked;
  }

  @Override
  public boolean getOnCommand() {
    return onCommand;
  }

  @Override
  public ReadonlyReversiModel getModel() {
    return model;
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
    JOptionPane.showMessageDialog(this, "you need to skip");
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    panel.mouseReleased(e);
  }


  @Override
  public void repaintView() {
    this.repaintHex(updateHexPile(hexPile));
  }

  // update the hexPile
  // keep the original hexagon size
  private List<List<ISingleTile>> updateHexPile(List<List<ISingleTile>> hexPile) {
    // find the locations of the currentPlayer
    List<BoardLocation> currentPlayerLocs = new ArrayList<>();
    List<List<DiscType>> piles = model.getPile();

    int rowNumber = 0;
    while (rowNumber < piles.size()) {
      int index = 0;
      int countNull = 0;
      while (index < piles.get(rowNumber).size()) {
        try {
          if (model.getDisc(rowNumber, index) == DiscType.WHITE) {
            hexPile.get(rowNumber).get(index).setType(DiscType.WHITE);
          }
          else if (!model.getDisc(rowNumber, index).toString().equals(DiscType.EMPTY.toString())) {
            hexPile.get(rowNumber).get(index).setType(DiscType.BLACK);
          }
        } catch (IllegalArgumentException ignore) {
          // catch the illegalArgument and ignore
        }
        index++;
      }
      rowNumber++;
    }
    return hexPile;
  }

  @Override
  public void initiateTitle(DiscType playerType) {
    onCommandText = "reversi" + " " + playerType.toWord() + ": On Command";
    waitCommandText = "reversi" + " "
            + playerType.toWord() + ": Waiting for another player";
    if (playerType == DiscType.BLACK) {
      this.playerType = playerType;
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
  public List<PlayerFeatures> getPlayerFeatures() {
    return this.playerFeatures;
  }

}
