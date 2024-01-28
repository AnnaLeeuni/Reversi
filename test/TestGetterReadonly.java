import org.junit.Assert;
import org.junit.Test;

import model.BasicBoard;
import model.ReadOnlyBasicBoard;

/**
 * tests for getter methods in ReadOnly.
 */
public class TestGetterReadonly {

  @Test
  public void testThrowExceptionSkipReadonly() {
    BasicBoard readOnlyBoard = new ReadOnlyBasicBoard();
    Assert.assertThrows(RuntimeException.class, () -> readOnlyBoard.skipRound());
  }

  @Test
  public void testThrowExceptionPlaceDiscReadonly() {
    BasicBoard readOnlyBoard = new ReadOnlyBasicBoard();
    Assert.assertThrows(RuntimeException.class, () -> readOnlyBoard.placeDisc(1, 0));
  }
}
