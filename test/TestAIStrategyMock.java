import org.junit.Assert;
import org.junit.Test;

import controller.strategy.AICaptureManyPieces;
import controller.strategy.AIGetCorners;
import controller.strategy.IStrategy;
import model.BasicBoard;
import model.BoardLocation;
import model.IBoard;
import model.MockManipulativeReversiModel;
import model.MockReadonlyReversiModel;
import view.textualview.TextualViewBoard;

/**
 * test the mock.
 */
public class TestAIStrategyMock {
  // test if tryMany actually checked all the tiles
  @Test
  public void testAICaptureManyPiecesQueryRightPosition() {
    StringBuilder str = new StringBuilder();
    IBoard basic = new BasicBoard();
    MockReadonlyReversiModel mockModel = new MockReadonlyReversiModel(str, basic);
    /*MockManipulativeReversiModel mockModel = new MockManipulativeReversiModel(str);*/
    TextualViewBoard viewBoard = new TextualViewBoard(mockModel);
    System.out.println(viewBoard);

    IStrategy strategy = new AICaptureManyPieces();
    strategy.getPosition(mockModel);
    Assert.assertEquals("visit position: x: 0, y: 0\n" +
            "visit position: x: 0, y: 1\n" +
            "visit position: x: 0, y: 2\n" +
            "visit position: x: 0, y: 3\n" +
            "visit position: x: 0, y: 4\n" +
            "visit position: x: 0, y: 5\n" +
            "visit position: x: 1, y: 0\n" +
            "visit position: x: 1, y: 1\n" +
            "visit position: x: 1, y: 2\n" +
            "visit position: x: 1, y: 3\n" +
            "visit position: x: 1, y: 4\n" +
            "visit position: x: 1, y: 5\n" +
            "visit position: x: 1, y: 6\n" +
            "visit position: x: 2, y: 0\n" +
            "visit position: x: 2, y: 1\n" +
            "visit position: x: 2, y: 2\n" +
            "visit position: x: 2, y: 3\n" +
            "visit position: x: 2, y: 4\n" +
            "visit position: x: 2, y: 5\n" +
            "visit position: x: 2, y: 6\n" +
            "visit position: x: 2, y: 7\n" +
            "visit position: x: 3, y: 0\n" +
            "visit position: x: 3, y: 1\n" +
            "visit position: x: 3, y: 2\n" +
            "visit position: x: 3, y: 3\n" +
            "visit position: x: 3, y: 4\n" +
            "visit position: x: 3, y: 5\n" +
            "visit position: x: 3, y: 6\n" +
            "visit position: x: 3, y: 7\n" +
            "visit position: x: 3, y: 8\n" +
            "visit position: x: 4, y: 0\n" +
            "visit position: x: 4, y: 1\n" +
            "visit position: x: 4, y: 2\n" +
            "visit position: x: 4, y: 3\n" +
            "visit position: x: 4, y: 4\n" +
            "visit position: x: 4, y: 5\n" +
            "visit position: x: 4, y: 6\n" +
            "visit position: x: 4, y: 7\n" +
            "visit position: x: 4, y: 8\n" +
            "visit position: x: 4, y: 9\n" +
            "visit position: x: 5, y: 0\n" +
            "visit position: x: 5, y: 1\n" +
            "visit position: x: 5, y: 2\n" +
            "visit position: x: 5, y: 3\n" +
            "visit position: x: 5, y: 4\n" +
            "visit position: x: 5, y: 5\n" +
            "visit position: x: 5, y: 6\n" +
            "visit position: x: 5, y: 7\n" +
            "visit position: x: 5, y: 8\n" +
            "visit position: x: 5, y: 9\n" +
            "visit position: x: 5, y: 10\n" +
            "visit position: x: 6, y: 0\n" +
            "visit position: x: 6, y: 1\n" +
            "visit position: x: 6, y: 2\n" +
            "visit position: x: 6, y: 3\n" +
            "visit position: x: 6, y: 4\n" +
            "visit position: x: 6, y: 5\n" +
            "visit position: x: 6, y: 6\n" +
            "visit position: x: 6, y: 7\n" +
            "visit position: x: 6, y: 8\n" +
            "visit position: x: 6, y: 9\n" +
            "visit position: x: 7, y: 0\n" +
            "visit position: x: 7, y: 1\n" +
            "visit position: x: 7, y: 2\n" +
            "visit position: x: 7, y: 3\n" +
            "visit position: x: 7, y: 4\n" +
            "visit position: x: 7, y: 5\n" +
            "visit position: x: 7, y: 6\n" +
            "visit position: x: 7, y: 7\n" +
            "visit position: x: 7, y: 8\n" +
            "visit position: x: 8, y: 0\n" +
            "visit position: x: 8, y: 1\n" +
            "visit position: x: 8, y: 2\n" +
            "visit position: x: 8, y: 3\n" +
            "visit position: x: 8, y: 4\n" +
            "visit position: x: 8, y: 5\n" +
            "visit position: x: 8, y: 6\n" +
            "visit position: x: 8, y: 7\n" +
            "visit position: x: 9, y: 0\n" +
            "visit position: x: 9, y: 1\n" +
            "visit position: x: 9, y: 2\n" +
            "visit position: x: 9, y: 3\n" +
            "visit position: x: 9, y: 4\n" +
            "visit position: x: 9, y: 5\n" +
            "visit position: x: 9, y: 6\n" +
            "visit position: x: 10, y: 0\n" +
            "visit position: x: 10, y: 1\n" +
            "visit position: x: 10, y: 2\n" +
            "visit position: x: 10, y: 3\n" +
            "visit position: x: 10, y: 4\n" +
            "visit position: x: 10, y: 5\n", str.toString());
  }

  @Test
  public void testAICaptureManyPiecesQueryRightPositionManipulative() {
    StringBuilder str = new StringBuilder();
    IBoard basic = new BasicBoard();
    MockReadonlyReversiModel liemodel = new MockManipulativeReversiModel(str,
            new BoardLocation(3, 4), basic);
    /*MockManipulativeReversiModel mockModel = new MockManipulativeReversiModel(str);*/
    TextualViewBoard viewBoard = new TextualViewBoard(liemodel);
    System.out.println(viewBoard);

    IStrategy strategy = new AICaptureManyPieces();
    strategy.getPosition(liemodel);
    System.out.println(str);
    //Assert.assertEquals(1, liemodel.getInspectedLocations().size());
    Assert.assertEquals("visit position: x: 3, y: 4\n", str.toString());
  }


  @Test
  public void testGetCornerQueryRightPosition() {
    StringBuilder str = new StringBuilder();

    IBoard basic = new BasicBoard();
    MockReadonlyReversiModel mockModel = new MockReadonlyReversiModel(str, basic);
    MockManipulativeReversiModel lieModel = new MockManipulativeReversiModel(str, basic);
    TextualViewBoard viewBoard = new TextualViewBoard(mockModel);
    System.out.println(viewBoard);

    IStrategy strategy = new AIGetCorners();
    strategy.getPosition(mockModel);
    System.out.println(str);
    //Assert.assertEquals(6, lieModel.getInspectedLocations().size());
    Assert.assertEquals("visit position: x: 0, y: 0\n"
            + "visit position: x: 0, y: 5\n"
            + "visit position: x: 5, y: 0\n"
            + "visit position: x: 5, y: 10\n"
            + "visit position: x: 10, y: 0\n"
            + "visit position: x: 10, y: 5\n", str.toString());
  }
}
