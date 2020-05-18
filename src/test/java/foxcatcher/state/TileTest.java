package foxcatcher.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    @Test
    void testIsEmpty() {
       assertTrue(new Tile(new Coordinate(1,1),Pawn.EMPTY).isEmpty());
       assertTrue(new Tile(new Coordinate(0,0),Pawn.EMPTY).isEmpty());
       assertFalse(new Tile(new Coordinate(0,0),Pawn.DOG).isEmpty());
       assertFalse(new Tile(new Coordinate(0,0),Pawn.FOX).isEmpty());
    }
}