package view;

import java.util.ArrayList;
import java.util.List;

import model.DiscType;
import view.singletile.ISingleTile;
import view.singletile.SingleHexagon;

/**
 * util class with the ability to initiate hexagons.
 */
public class HexagonInitializer {

  /**
   * initiate hexagon piles as a list of lists of singleHexagons.
   *
   * @param pile              the board of the model
   * @param hexagonEdgeLength the length of a single hexagon
   */
  public static List<List<ISingleTile>> initiateHexList(List<List<DiscType>> pile,
                                                        int hexagonEdgeLength) {
    boolean firstHalf = true;
    List<List<ISingleTile>> hexagons = new ArrayList<>();
    int rowNumber = 0;
    int indent = (pile.size() - 1) / 2;
    while (rowNumber < pile.size()) {
      List<ISingleTile> row = new ArrayList<>();
      int index = 0;
      int countNull = 0;
      while (index < pile.get(rowNumber).size()) {
        if (pile.get(rowNumber).get(index) == null) {
          index++;
          countNull++;
          continue;
        }
        int addWidth = (int) ((hexagonEdgeLength / 2) * Math.sqrt(3));
        int indentation = indent * addWidth;

        int startYCo = rowNumber * (hexagonEdgeLength + hexagonEdgeLength / 2);
        int startXco = index * (addWidth * 2) + indentation + addWidth;
        int[] xPoints = new int[]{startXco, startXco + addWidth, startXco + addWidth,
            startXco, startXco - addWidth, startXco - addWidth};
        int[] yPoints = new int[]{startYCo, startYCo + hexagonEdgeLength / 2,
            startYCo + hexagonEdgeLength / 2 + hexagonEdgeLength,
            startYCo + hexagonEdgeLength + hexagonEdgeLength,
            startYCo + hexagonEdgeLength / 2 + hexagonEdgeLength,
            startYCo + hexagonEdgeLength / 2};

        SingleHexagon hexagon = new SingleHexagon(rowNumber,
                index - countNull, hexagonEdgeLength, indent,
                pile.get(rowNumber).get(index), xPoints, yPoints);
        row.add(hexagon);
        index++;
      }
      hexagons.add(row);
      if (firstHalf) {
        indent--;
      } else {
        indent++;
      }
      if (indent == 0) {
        firstHalf = false;
      }
      rowNumber++;
    }
    return hexagons;
  }
}

