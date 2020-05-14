package foxcatcher.state;

import java.util.Vector;



public class DogPawn extends Pawn {


    public DogPawn(){
        possibleMoveDirections=new Vector<Direction>();
        possibleMoveDirections.add(Direction.TOP_LEFT);
        possibleMoveDirections.add(Direction.TOP_RIGHT);
    }






}
