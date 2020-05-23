package foxcatcher.state;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;


/**
 * Class for representing a coordinate.
 */
@Data
@AllArgsConstructor
public class Coordinate {

    private int x;
    private int y;

    /**
     * Moves the coordinate with one step to the direction specified.
     * @param direction the direction to which the coordinate moved
     * @return the coordinate moved to the direction specified
     */
    public  Coordinate moveToDirection(Direction direction){
        return new Coordinate(this.x + direction.getDx(),this.y + direction.getDy());
    }


}





