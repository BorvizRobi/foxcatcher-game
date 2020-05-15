package foxcatcher.state;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coordinate {

    private int x;
    private int y;

    public  Coordinate moveToDirection(Direction direction){
        return new Coordinate(this.x + direction.getDx(),this.y + direction.getDy());
    }

}
