package foxcatcher.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    @Test
    void testOf() {
        assertEquals(Pawn.EMPTY, Pawn.of(0));
        assertEquals(Pawn.DOG,Pawn.of(1));
        assertEquals(Pawn.FOX,Pawn.of(2));
        assertThrows(IllegalArgumentException.class,()->Pawn.of(-1));
        assertThrows(IllegalArgumentException.class,()->Pawn.of(3));
    }

    @Test
    void testGetMovedirections() {

        assertNull(Pawn.of(0).getMoveDirections());
        assertArrayEquals(new Direction[]{Direction.TOP_LEFT, Direction.TOP_RIGHT}, Pawn.of(1).getMoveDirections());
        assertArrayEquals(new Direction[]{Direction.TOP_LEFT,Direction.TOP_RIGHT,Direction.BOTTOM_LEFT,Direction.BOTTOM_RIGHT}, Pawn.of(2).getMoveDirections());

    }
}