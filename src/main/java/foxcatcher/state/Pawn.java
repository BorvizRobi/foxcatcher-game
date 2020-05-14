package foxcatcher.state;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Vector;

public abstract class  Pawn {

          @Getter()
          public Vector<Direction> possibleMoveDirections;

}

