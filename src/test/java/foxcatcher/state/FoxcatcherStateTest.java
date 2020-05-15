package foxcatcher.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoxcatcherStateTest {

    @Test
    void testIsValidBoard() {
    }

    @Test
    void testMovePawn() {
    }

    @Test
    void testCalculatePossibleMoveCoordinates() {
    }

    @Test
    void testIsGameOwer() {
        assertFalse(new FoxcatcherState().isGameOwer());
        assertTrue(new FoxcatcherState (new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        }).isGameOwer());
        assertTrue(new FoxcatcherState (new int[][] {
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 1},

        }).isGameOwer());
    }
    @Test
    void testCanMovePawn() {
        FoxcatcherState state = new FoxcatcherState();
        assertFalse(state.canMovePawn(new Coordinate(7,1),new Coordinate(6,1)));
        assertFalse(state.canMovePawn(new Coordinate(0,2),new Coordinate(1,2)));
        assertFalse(state.canMovePawn(new Coordinate(0,0),new Coordinate(1,1)));
        assertFalse(state.canMovePawn(new Coordinate(7,7),new Coordinate(6,7)));
        assertTrue(state.canMovePawn(new Coordinate(7,1),new Coordinate(6,2)));
        assertTrue(state.canMovePawn(new Coordinate(7,1),new Coordinate(6,0)));
        assertTrue(state.canMovePawn(new Coordinate(7,7),new Coordinate(6,6)));
        assertTrue(state.canMovePawn(new Coordinate(0,2),new Coordinate(1,1)));
        assertFalse(state.canMovePawn(new Coordinate(7,1),new Coordinate(5,1)));
    }

    @Test
    void testClone() {
    }

    @Test
    void testToString() {
    }
}