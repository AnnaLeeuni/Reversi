package view;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JPanel;

import model.DiscType;
import model.ReadonlyReversiModel;
import view.panel.IBoardPanel;
import view.panel.PanelWithHint;
import view.singletile.ISingleTile;

/**
 * Decorates a basic Reversi game view with additional functionality to provide players with hints.
 * This class extends JFrame and implements IGUIView, functioning as a decorator
 * for an existing IGUIView instance.
 */
public class ViewBoardWithHint extends JFrame implements IGUIView {
  // add functionalities based on the delegate.
  private final IGUIView delegate;

  // The JPanel on the frame
  protected final IBoardPanel panel;


  // the text for the current on the command state
  private String onCommandText = "On command";

  // the text for the current on the waiting state
  private String waitCommandText = "Wait for command";

  // if it is the player with current view's turn.

  private boolean onCommand;

  private DiscType playerType;


  /**
   * Constructs a ViewBoardWithHint object with an existing IGUIView as its delegate.
   *
   * @param delegate The IGUIView instance to decorate.
   */

  public ViewBoardWithHint(IGUIView delegate) {
    super();
    this.delegate = delegate;
    // register as the listener

    final IBoardPanel decoratePanel;

    panel = delegate.getHexPanel();

    panel.removeMouseListener(delegate);
    panel.removeKeyListener(delegate);

    decoratePanel = new PanelWithHint(panel);
    delegate.replaceJPanel(decoratePanel);

    int height = delegate.getModel().getHeight();
    int defaultWidth = height * 45 * 2;
    int defaultHeight = height * 45 * 2;

    // default size of the JFrame
    setPreferredSize(new Dimension(defaultWidth,
            defaultHeight));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    if (!(decoratePanel instanceof JPanel)) {
      throw new IllegalStateException("IPanel should extends JPanel");
    }
    this.add((JPanel) decoratePanel);
    decoratePanel.addMouseListener(this);
    decoratePanel.addKeyListener(this);
    this.addKeyListener(this);

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
   * Updates the side length of the board based on the window's dimensions.
   *
   * @param width  The new width of the window.
   * @param height The new height of the window.
   */
  @Override
  public void updateSideLength(int width, int height) {
    this.delegate.updateSideLength(width, height);
  }

  /**
   * Resizes all tiles on the board to match the new dimensions.
   */
  @Override
  public void resizingPiles() {
    this.delegate.resizingPiles();
  }

  @Override
  public DiscType getPlayerType() {
    return delegate.getPlayerType();
  }

  @Override
  public List<List<ISingleTile>> initiateBoard(List<List<DiscType>> piles) {
    return delegate.initiateBoard(piles);
  }

  @Override
  public List<List<ISingleTile>> getRenderBoard() {
    return delegate.getRenderBoard();
  }

  @Override
  public void initiateTitle(DiscType playerType) {
    delegate.initiateTitle(playerType);
    onCommandText = "reversi" + " with hint" + " " + playerType.toWord() + ": On Command";
    waitCommandText = "reversi" + " with hint" + " "
            + playerType.toWord() + ": Waiting for another player";
    if (playerType == DiscType.BLACK) {
      setTitle(onCommandText);
      onCommand = true;
    } else {
      setTitle(waitCommandText);
      onCommand = false;
    }
  }

  @Override
  public void addFeatures(PlayerFeatures playerFeatures) {
    delegate.addFeatures(playerFeatures);
  }

  @Override
  public List<PlayerFeatures> getPlayerFeatures() {
    return delegate.getPlayerFeatures();
  }

  @Override
  public void repaintView() {
    delegate.repaintView();
    this.repaint();
  }

  @Override
  public void updateTitle() {
    if (delegate.getModel().gameOver()) {
      setTitle("reversi With Hint Game End");
    } else if (onCommand) {
      setTitle(waitCommandText);
      onCommand = false;
    } else {
      setTitle(onCommandText);
      onCommand = true;
    }
  }

  @Override
  public void gameOverMessage() {
    delegate.gameOverMessage();
  }

  @Override
  public void errorMessage() {
    delegate.errorMessage();
  }

  @Override
  public JPanel getJPanel() {
    return delegate.getJPanel();
  }

  @Override
  public IBoardPanel getHexPanel() {
    return delegate.getHexPanel();
  }

  @Override
  public boolean getUserClicked() {
    return delegate.getUserClicked();
  }

  @Override
  public boolean getOnCommand() {
    return delegate.getOnCommand();
  }

  @Override
  public ReadonlyReversiModel getModel() {
    return delegate.getModel();
  }

  @Override
  public void replaceJPanel(IBoardPanel replacePanel) {
    delegate.replaceJPanel(replacePanel);
  }

  @Override
  public void notifyToPass() {
    delegate.notifyToPass();
  }

  @Override
  public void keyTyped(KeyEvent e) {
    delegate.keyTyped(e);
  }

  @Override
  public void keyPressed(KeyEvent e) {
    delegate.keyPressed(e);
  }

  @Override
  public void keyReleased(KeyEvent e) {
    delegate.keyReleased(e);
    this.repaintView();

  }

  @Override
  public void mouseClicked(MouseEvent e) {
    delegate.mouseClicked(e);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    delegate.mousePressed(e);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    delegate.mouseReleased(e);

    repaint();
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    delegate.mouseEntered(e);
  }

  @Override
  public void mouseExited(MouseEvent e) {
    delegate.mouseExited(e);

  }

}
