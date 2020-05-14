package foxcatcher.state;

import java.util.Vector;

public class FoxPawn extends Pawn{

    public FoxPawn(){
        possibleMoveDirections=new Vector<Direction>();
        possibleMoveDirections.add(Direction.TOP_LEFT);
        possibleMoveDirections.add(Direction.TOP_RIGHT);
        possibleMoveDirections.add(Direction.BOTTOM_LEFT);
        possibleMoveDirections.add(Direction.BOTTOM_RIGHT);
    }

}
