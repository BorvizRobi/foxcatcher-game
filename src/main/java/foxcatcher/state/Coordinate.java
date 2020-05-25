package foxcatcher.state;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * Class for representing a coordinate.
 */
@Data
@AllArgsConstructor
public class Coordinate {

    private int x;
    private int y;

    /**
     * Moves the coordinate with one step in the direction specified.
     * @param direction The direction in which the coordinate moved.
     * @return The coordinate moved to the direction specified.
     */
    public  Coordinate moveToDirection(Direction direction){
        return new Coordinate(this.x + direction.getDx(),this.y + direction.getDy());
    }


}





