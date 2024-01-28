import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.awt.Point;
import java.awt.Component;
import java.awt.geom.Point2D;

import java.awt.event.MouseEvent;
import java.util.List;


import model.BasicBoard;
import model.BoardWithNotifier;
import model.SquareReversiModel;
import view.panel.IBoardPanel;
import view.singletile.ISingleTile;
import view.ViewBoard;
import view.ViewSquareBoard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for method in HexagonPanel.
 */
public class PanelTest {

  BoardWithNotifier board;

  @Before
  public void init() {
    BasicBoard basic = new BasicBoard(7);
    board = new BoardWithNotifier(basic);
    board.startGame();

  }

  @Before
  public void initSquare() {
    SquareReversiModel basic = new SquareReversiModel(8);
    board = new BoardWithNotifier(basic);
    board.startGame();

  }

  @Test
  public void testMouseReleased() {
    init();
    ViewBoard viewPlayer1 = new ViewBoard(board);
    List<List<ISingleTile>> list = viewPlayer1.getRenderBoard();

    //HexagonPanel panel = new HexagonPanel(new ReadOnlyBasicBoard(board), list);
    IBoardPanel panel = viewPlayer1.getHexPanel();

    MouseEvent mockMouseEvent = new MouseEvent((Component) panel, MouseEvent.MOUSE_RELEASED,
            System.currentTimeMillis(), 0, 200, 100, 1, false);
    panel.mouseReleased(mockMouseEvent);
    System.out.println(panel);
    assertEquals(new Point.Double(1, 1), panel.getClickedLocation());
    assertTrue(panel.getUserClicked());
  }

  @Test
  public void testMouseReleased2() {
    initSquare();
    ViewSquareBoard viewPlayer1 = new ViewSquareBoard(board);
    List<List<ISingleTile>> list = viewPlayer1.getRenderBoard();

    IBoardPanel panel = viewPlayer1.getHexPanel();
    Point point = new Point(5, 5);
    MouseEvent mockMouseEvent = new MouseEvent((Component) panel, MouseEvent.MOUSE_RELEASED,
            System.currentTimeMillis(), 0, 300, 300, 1, false);
    panel.mouseReleased(mockMouseEvent);
    assertEquals(new Point.Double(7, 7), panel.getClickedLocation());
    assertTrue(panel.getUserClicked());
  }

  @Test
  public void testGetClickedLocation() {
    init();
    ViewBoard viewPlayer1 = new ViewBoard(board);
    List<List<ISingleTile>> list = viewPlayer1.getRenderBoard();

    IBoardPanel panel = viewPlayer1.getHexPanel();
    MouseEvent clickEvent = new MouseEvent((Component) panel, MouseEvent.MOUSE_RELEASED,
            System.currentTimeMillis(), 0, 200, 200, 1, false);
    panel.mouseReleased(clickEvent);
    Point2D clickedLocation = panel.getClickedLocation();
    Assert.assertEquals(2, clickedLocation.getX(), 0.1);
    Assert.assertEquals(1, clickedLocation.getY(), 0.1);
  }

  @Test
  public void testGetClickedLocation2() {
    initSquare();
    ViewSquareBoard viewPlayer1 = new ViewSquareBoard(board);
    List<List<ISingleTile>> list = viewPlayer1.getRenderBoard();

    IBoardPanel panel = viewPlayer1.getHexPanel();
    MouseEvent clickEvent = new MouseEvent((Component) panel, MouseEvent.MOUSE_RELEASED,
            System.currentTimeMillis(), 0, 100, 100, 1, false);
    panel.mouseReleased(clickEvent);
    Point2D clickedLocation = panel.getClickedLocation();
    Assert.assertEquals(2, clickedLocation.getX(), 0.1);
    Assert.assertEquals(2, clickedLocation.getY(), 0.1);
  }

  @Test
  public void testGetUserClicked2() {
    init();
    ViewBoard viewPlayer1 = new ViewBoard(board);
    List<List<ISingleTile>> list = viewPlayer1.getRenderBoard();

    IBoardPanel panel = viewPlayer1.getHexPanel();
    Assert.assertFalse(panel.getUserClicked());
    MouseEvent clickEvent = new MouseEvent((Component) panel, MouseEvent.MOUSE_RELEASED,
            System.currentTimeMillis(), 0, 100, 100, 1, false);
    panel.mouseReleased(clickEvent);
    Assert.assertTrue(panel.getUserClicked());
  }

  @Test
  public void testGetUserClicked() {
    initSquare();
    ViewSquareBoard viewPlayer1 = new ViewSquareBoard(board);
    List<List<ISingleTile>> list = viewPlayer1.getRenderBoard();

    IBoardPanel panel = viewPlayer1.getHexPanel();
    Assert.assertFalse(panel.getUserClicked());
    MouseEvent clickEvent = new MouseEvent((Component) panel, MouseEvent.MOUSE_RELEASED,
            System.currentTimeMillis(), 0, 100, 100, 1, false);
    panel.mouseReleased(clickEvent);
    Assert.assertTrue(panel.getUserClicked());
  }

}
