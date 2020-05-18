package foxcatcher.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    void testMoveToDirection() {
        assertEquals(new Coordinate(6,1),new Coordinate(7,2).moveToDirection(Direction.TOP_LEFT));
        assertEquals(new Coordinate(1,1),new Coordinate(0,0).moveToDirection(Direction.BOTTOM_RIGHT));
        assertEquals(new Coordinate(0,0),new Coordinate(1,1).moveToDirection(Direction.TOP_LEFT));
        assertEquals(new Coordinate(2,0),new Coordinate(1,1).moveToDirection(Direction.BOTTOM_LEFT));
        assertEquals(new Coordinate(2,4),new Coordinate(3,3).moveToDirection(Direction.TOP_RIGHT));
        assertEquals(new Coordinate(7,2),new Coordinate(7,2).moveToDirection(Direction.TOP_LEFT).moveToDirection(Direction.BOTTOM_RIGHT));
        assertEquals(new Coordinate(0,2),new Coordinate(2,2).moveToDirection(Direction.TOP_LEFT).moveToDirection(Direction.TOP_RIGHT));
    }
}