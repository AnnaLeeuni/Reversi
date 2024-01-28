package view.panel;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Point;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.JOptionPane;

import model.DiscType;
import model.IBoard;
import model.ReadonlyReversiModel;
import view.singletile.ISingleTile;

/**
 * decorate the giving panel.
 */
public class PanelWithHint extends JPanel implements IBoardPanel, ActionListener {
  private final IBoardPanel delegate;

  private Point2D clickedPhysicalLocation;

  private final ReadonlyReversiModel model;

  private Boolean hintAbled = false;


  /**
   * add a hint feature to the given panel.
   *
   * @param panel the panel to get decorated
   */
  public PanelWithHint(IBoardPanel panel) {
    super();
    this.delegate = panel;
    this.model = delegate.getModel();

    // add a button to the current frame
    JButton hintButton = new JButton("Hint");           // Create a button,
    hintButton.addActionListener(this);         // set the callback,

    InputMap inputMap = hintButton.getInputMap(JComponent.WHEN_FOCUSED);
    inputMap.put(KeyStroke.getKeyStroke("SPACE"), "none"); // Disable the space key
    hintButton.setFocusable(false);
    this.add(hintButton, BorderLayout.NORTH);
  }

  @Override
  public Point2D getClickedLocation() {
    return delegate.getClickedLocation();
  }

  @Override
  public void paintComponent(Graphics g) {
    // keep the original functionalities
    delegate.paintComponent(g);

    if (!model.gameOver() && model.getTurns() == delegate.getPlayerType()) {
      if (delegate.getUserClicked()
              && model.getDisc((int) delegate.getClickedLocation().getX(),
              (int) delegate.getClickedLocation().getY()) == DiscType.EMPTY && hintAbled) {
        // paint number on the tile
        // make a mutable version board
        IBoard fakeBoard = model.getMutableVersion();

        int numberToPrint = 0;
        // get the score of the current player
        int before = fakeBoard.getScore(fakeBoard.getTurns());
        DiscType currentPlayer = fakeBoard.getTurns();
        int toPrint = 0;

        try {
          fakeBoard.placeDisc((int) delegate.getClickedLocation().getX(),
                  (int) delegate.getClickedLocation().getY());
          toPrint = fakeBoard.getScore(currentPlayer) - before - 1;
        } catch (IllegalStateException e) {
          // ignore
        }
        if (clickedPhysicalLocation != null) {
          Font font = new Font("Serif", Font.PLAIN, 40);
          g.setFont(font);
          g.drawString(String.valueOf(toPrint), (int) clickedPhysicalLocation.getX(),
                  (int) clickedPhysicalLocation.getY());
        }
      }
    }
  }

  @Override
  public boolean getUserClicked() {
    return delegate.getUserClicked();
  }

  @Override
  public void resetHighLight() {
    delegate.resetHighLight();
  }

  @Override
  public void repaintBoard(List<List<ISingleTile>> hexPile) {
    delegate.repaintBoard(hexPile);
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
    if (delegate.getUserClicked()) {
      this.clickedPhysicalLocation = new Point(e.getX(), e.getY());
    }

  }

  @Override
  public void mouseEntered(MouseEvent e) {
    delegate.mouseEntered(e);
  }

  @Override
  public void mouseExited(MouseEvent e) {
    delegate.mouseExited(e);
  }

  @Override
  public void addMouseListener(MouseListener l) {
    super.addMouseListener(l);
    System.out.println(Arrays.toString(this.getMouseListeners()));

  }

  @Override
  public ReadonlyReversiModel getModel() {
    return delegate.getModel();
  }

  @Override
  public void setPlayerType(DiscType playerType) {
    delegate.setPlayerType(playerType);
  }

  @Override
  public DiscType getPlayerType() {
    return delegate.getPlayerType();
  }

  @Override
  public void addKeyListener(KeyListener l) {
    super.addKeyListener(l);
    System.out.println(Arrays.toString(this.getKeyListeners()));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.repaint();
    if (hintAbled) {
      hintAbled = false;
      JOptionPane.showMessageDialog(this, "hint disabled");

    } else {
      hintAbled = true;
      JOptionPane.showMessageDialog(this, "hint abled");
    }
  }
}
