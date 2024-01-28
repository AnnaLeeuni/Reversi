package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * lies to the strategy about the model so only one inputted position is valid.
 */
public class MockManipulativeReversiModel extends MockReadonlyReversiModel
        implements ReadonlyReversiModel {

  IBoard model;
  BoardLocation singleLegalLocation;

  public MockManipulativeReversiModel(StringBuilder out, IBoard model) {
    super(out, model);
  }

  public MockManipulativeReversiModel(StringBuilder out, BoardLocation singleLegalLocation,
                                      IBoard model) {
    super(out, model);
    this.singleLegalLocation = singleLegalLocation;
  }

  @Override
  public List<BoardLocation> legalMoves() {
    return new ArrayList<>(Collections.singletonList(singleLegalLocation));
  }

  @Override
  public List<BoardLocation> getCorners() {
    return List.of(singleLegalLocation);
  }


}
