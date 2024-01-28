import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import view.textualview.TextualViewBoard;
import model.BasicBoard;
import model.DiscType;

import static org.junit.Assert.assertEquals;

/**
 * Test on TextualView.
 */
public class TestTextualViewBoard {

  private BasicBoard basicBoard;
  private List<List<DiscType>> pile;
  List<List<DiscType>> customPile;

  @Before
  public void setUp() {
    basicBoard = new BasicBoard();
  }

  @Test
  public void testToStringView() {
    List<DiscType> firstLine = new ArrayList<>();
    firstLine.add(null);
    firstLine.add(null);
    firstLine.add(null);
    firstLine.add(null);
    firstLine.add(null);
    List<DiscType> firstLineRest = Collections.nCopies(6, DiscType.EMPTY);
    firstLine.addAll(firstLineRest);

    List<DiscType> secondLine = new ArrayList<>();
    secondLine.add(null);
    secondLine.add(null);
    secondLine.add(null);
    secondLine.add(null);
    List<DiscType> secondLineRest = Collections.nCopies(7, DiscType.EMPTY);
    secondLine.addAll(secondLineRest);

    List<DiscType> thirdLine = new ArrayList<>();
    thirdLine.add(null);
    thirdLine.add(null);
    thirdLine.add(null);
    List<DiscType> thirdLineRest = Collections.nCopies(8, DiscType.EMPTY);
    thirdLine.addAll(thirdLineRest);

    List<DiscType> forthLine = new ArrayList<>();
    forthLine.add(null);
    forthLine.add(null);
    List<DiscType> forthLineRest = Collections.nCopies(9, DiscType.EMPTY);
    forthLine.addAll(forthLineRest);

    List<DiscType> fifthLine = new ArrayList<>();
    fifthLine.add(null);
    List<DiscType> fifthLineRes = Collections.nCopies(10, DiscType.EMPTY);
    fifthLine.addAll(fifthLineRes);


    List<DiscType> sixthLine = new ArrayList<>(Collections.nCopies(4, DiscType.EMPTY));
    List<DiscType> sixthLineM = new ArrayList<>(
            List.of(DiscType.BLACK, DiscType.WHITE, DiscType.BLACK));
    List<DiscType> sixthLineRes = Collections.nCopies(4, DiscType.EMPTY);
    sixthLine.addAll(sixthLineM);
    sixthLine.addAll(sixthLineRes);


    List<DiscType> sevenLine = new ArrayList<>(Collections.nCopies(10, DiscType.EMPTY));
    sevenLine.add(null);

    List<DiscType> eightLine = new ArrayList<>(Collections.nCopies(9, DiscType.EMPTY));
    eightLine.add(null);
    eightLine.add(null);


    List<DiscType> nineLine = new ArrayList<>(Collections.nCopies(8, DiscType.EMPTY));
    nineLine.add(null);
    nineLine.add(null);
    nineLine.add(null);

    List<DiscType> tenLine = new ArrayList<>(Collections.nCopies(7, DiscType.EMPTY));
    tenLine.add(null);
    tenLine.add(null);
    tenLine.add(null);
    tenLine.add(null);

    List<DiscType> elevenLine = new ArrayList<>(Collections.nCopies(6, DiscType.EMPTY));
    elevenLine.add(null);
    elevenLine.add(null);
    elevenLine.add(null);
    elevenLine.add(null);
    elevenLine.add(null);


    customPile = List.of(firstLine, secondLine, thirdLine, forthLine, fifthLine, sixthLine,
            sevenLine, eightLine, nineLine, tenLine, elevenLine);

    basicBoard = new BasicBoard(customPile);

    TextualViewBoard view = new TextualViewBoard(basicBoard);
    assertEquals("     _ _ _ _ _ _\n"
            + "    _ _ _ _ _ _ _\n"
            + "   _ _ _ _ _ _ _ _\n"
            + "  _ _ _ _ _ _ _ _ _\n"
            + " _ _ _ _ _ _ _ _ _ _\n"
            + "_ _ _ _ x o x _ _ _ _\n"
            + " _ _ _ _ _ _ _ _ _ _\n"
            + "  _ _ _ _ _ _ _ _ _\n"
            + "   _ _ _ _ _ _ _ _\n"
            + "    _ _ _ _ _ _ _\n"
            + "     _ _ _ _ _ _\n", view.toString());


  }

  @Test
  public void testToStringView1() {
    List<DiscType> firstLine = new ArrayList<>();
    firstLine.add(null);
    firstLine.add(null);
    firstLine.add(null);
    firstLine.add(null);
    firstLine.add(null);
    List<DiscType> firstLineRest = Collections.nCopies(6, DiscType.EMPTY);
    firstLine.addAll(firstLineRest);

    List<DiscType> secondLine = new ArrayList<>();
    secondLine.add(null);
    secondLine.add(null);
    secondLine.add(null);
    secondLine.add(null);
    List<DiscType> secondLineRest = Collections.nCopies(7, DiscType.EMPTY);
    secondLine.addAll(secondLineRest);

    List<DiscType> thirdLine = new ArrayList<>();
    thirdLine.add(null);
    thirdLine.add(null);
    thirdLine.add(null);
    List<DiscType> thirdLineRest = Collections.nCopies(8, DiscType.EMPTY);
    thirdLine.addAll(thirdLineRest);

    List<DiscType> forthLine = new ArrayList<>();
    forthLine.add(null);
    forthLine.add(null);
    List<DiscType> forthLineRest = Collections.nCopies(9, DiscType.EMPTY);
    forthLine.addAll(forthLineRest);

    List<DiscType> fifthLine = new ArrayList<>();
    fifthLine.add(null);
    List<DiscType> fifthLineRes = Collections.nCopies(10, DiscType.EMPTY);
    fifthLine.addAll(fifthLineRes);


    List<DiscType> sixthLine = new ArrayList<>(Collections.nCopies(4, DiscType.EMPTY));
    List<DiscType> sixthLineM = new ArrayList<>(
            List.of(DiscType.BLACK, DiscType.WHITE, DiscType.BLACK));
    List<DiscType> sixthLineRes = Collections.nCopies(4, DiscType.EMPTY);
    sixthLine.addAll(sixthLineM);
    sixthLine.addAll(sixthLineRes);


    List<DiscType> sevenLine = new ArrayList<>(Collections.nCopies(10, DiscType.EMPTY));
    sevenLine.add(null);

    List<DiscType> eightLine = new ArrayList<>(Collections.nCopies(9, DiscType.EMPTY));
    eightLine.add(null);
    eightLine.add(null);


    List<DiscType> nineLine = new ArrayList<>(Collections.nCopies(8, DiscType.EMPTY));
    nineLine.add(null);
    nineLine.add(null);
    nineLine.add(null);

    List<DiscType> tenLine = new ArrayList<>(Collections.nCopies(7, DiscType.EMPTY));
    tenLine.add(null);
    tenLine.add(null);
    tenLine.add(null);
    tenLine.add(null);

    List<DiscType> elevenLine = new ArrayList<>(Collections.nCopies(6, DiscType.EMPTY));
    elevenLine.add(null);
    elevenLine.add(null);
    elevenLine.add(null);
    elevenLine.add(null);
    elevenLine.add(null);


    customPile = List.of(firstLine, secondLine, thirdLine, forthLine, fifthLine, sixthLine,
            sevenLine, eightLine, nineLine, tenLine, elevenLine);

    basicBoard = new BasicBoard(customPile);
    basicBoard.startGame();
    // basicBoard.turns = DiscType.WHITE;
    basicBoard.skipRound();

    basicBoard.placeDisc(5, 3);

    basicBoard.skipRound();

    basicBoard.placeDisc(5, 7);

    // assertEquals(basicBoard.pile.get(5).get(3), null);

    TextualViewBoard view = new TextualViewBoard(basicBoard);
    assertEquals("     _ _ _ _ _ _\n"
            + "    _ _ _ _ _ _ _\n"
            + "   _ _ _ _ _ _ _ _\n"
            + "  _ _ _ _ _ _ _ _ _\n"
            + " _ _ _ _ _ _ _ _ _ _\n"
            + "_ _ _ o o o o o _ _ _\n"
            + " _ _ _ _ _ _ _ _ _ _\n"
            + "  _ _ _ _ _ _ _ _ _\n"
            + "   _ _ _ _ _ _ _ _\n"
            + "    _ _ _ _ _ _ _\n"
            + "     _ _ _ _ _ _\n", view.toString());
  }


}