package foxcatcher.state;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tile {

    private final Coordinate coordinate;
    private Pawn pawn;

    public boolean isEmpty(){
        if (pawn==Pawn.EMPTY)return true;
        else return false;
    }


}