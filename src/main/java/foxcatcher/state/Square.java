package foxcatcher.state;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Square {

    private final Coordinate coordinate;
    private Pawn pawn;


    public boolean isEmpty(){
        if (pawn==null)return true;
        else return false;
    }

    public int getValue(){


        if (!isEmpty() && pawn.getClass().equals(DogPawn.class) ) return 1;
        if (!isEmpty() && pawn.getClass().equals(FoxPawn.class) ) return 2;
        else return 0;


    }
    public String toString(){
        return Integer.toString(getValue());
    }


}