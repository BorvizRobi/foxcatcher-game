package foxcatcher.state;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FoxcatcherStateTest {

    private void assertFoxPosition(Coordinate expectedCoordinate, FoxcatcherState actual) {
        assertAll(
                () -> assertEquals(expectedCoordinate, actual.getFoxPosition())
        );
    }

    private void assertDogPositions(Vector<Coordinate> expectedCoordinates, FoxcatcherState actual) {
        assertAll(
                () -> assertTrue(expectedCoordinates.containsAll(actual.getDogPositions()))
        );
    }

    @Test
    void testOneArgConstructor_InvalidArg() {
        assertThrows(IllegalArgumentException.class, () -> new FoxcatcherState(null,1));
        assertThrows(IllegalArgumentException.class, () -> new FoxcatcherState(new int[][]{
                {1, 1},
                {1, 0}},1)
        );
        assertThrows(IllegalArgumentException.class, () -> new FoxcatcherState(new int[][]{
                {0},
                {1, 2},
                {3, 4, 5}},1)
        );
        assertThrows(IllegalArgumentException.class, () -> new FoxcatcherState(new int[][]{
                        {0, 0, 2, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 1, 1, 1, 0, 1},

                },1)
        );
        assertThrows(IllegalArgumentException.class, () -> new FoxcatcherState(new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},

                },1)
        );
        assertThrows(IllegalArgumentException.class, () -> new FoxcatcherState(new int[][]{
                        {0, 0, 2, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 1, 0, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},

                },1)
        );
    }

    @Test
    void testOneArgConstructor_ValidArg() {
        int[][] a = new int[][]{
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 1, 0, 1}
        };

        FoxcatcherState state = new FoxcatcherState(a,1);
        assertArrayEquals(new Pawn[][]{
                {Pawn.EMPTY, Pawn.EMPTY, Pawn.FOX, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY},
                {Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY},
                {Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY},
                {Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY},
                {Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY},
                {Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY},
                {Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY, Pawn.EMPTY},
                {Pawn.EMPTY, Pawn.DOG, Pawn.EMPTY, Pawn.DOG, Pawn.EMPTY, Pawn.DOG, Pawn.EMPTY, Pawn.DOG}
        }, state.getChessboard());

        assertFoxPosition(new Coordinate(0, 2), state);

        Vector<Coordinate> expectedDogPositions = new Vector<Coordinate>();
        expectedDogPositions.add(new Coordinate(7,7));
        expectedDogPositions.add(new Coordinate(7,5));
        expectedDogPositions.add(new Coordinate(7,3));
        expectedDogPositions.add(new Coordinate(7,1));
        assertDogPositions(expectedDogPositions,state);

    }

    @Test
    void testIsValidCoordinate() {
        assertTrue(FoxcatcherState.isValidCoordinate(new Coordinate(1, 1)));
        assertTrue(FoxcatcherState.isValidCoordinate(new Coordinate(7, 7)));
        assertTrue(FoxcatcherState.isValidCoordinate(new Coordinate(0, 0)));
        assertFalse(FoxcatcherState.isValidCoordinate(new Coordinate(-1, -1)));
        assertFalse(FoxcatcherState.isValidCoordinate(new Coordinate(8, 8)));
        assertFalse(FoxcatcherState.isValidCoordinate(new Coordinate(108, -5)));

    }

    @Test
    void testMovePawn() {

        FoxcatcherState state = new FoxcatcherState(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        },2);
        state.movePawn(new Coordinate(2,2),new Coordinate(1,1));
        assertFoxPosition(new Coordinate(1, 1), state);
        assertEquals(state.getActivePlayer(),1);

        state.movePawn(new Coordinate(6,2),new Coordinate(5,3));
        assertEquals(state.getActivePlayer(),2);

        Vector<Coordinate> expectedDogPositions = new Vector<Coordinate>();
        expectedDogPositions.add(new Coordinate(5,3));
        expectedDogPositions.add(new Coordinate(5,1));
        expectedDogPositions.add(new Coordinate(3,3));
        expectedDogPositions.add(new Coordinate(4,4));
        assertDogPositions(expectedDogPositions,state);

        state.movePawn(new Coordinate(1,1),new Coordinate(0,2));
        assertFoxPosition(new Coordinate(0, 2), state);
        assertEquals(state.getActivePlayer(),1);

        FoxcatcherState state2 = new FoxcatcherState(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        },2);
        state2.movePawn(new Coordinate(2,2),new Coordinate(3,1));
        assertFoxPosition(new Coordinate(3, 1), state2);
        assertEquals(state2.getActivePlayer(),1);

        state2.movePawn(new Coordinate(6,2),new Coordinate(5,1));
        assertEquals(state2.getActivePlayer(),2);

        Vector<Coordinate> expectedDogPositions2 = new Vector<Coordinate>();
        expectedDogPositions2.add(new Coordinate(6,0));
        expectedDogPositions2.add(new Coordinate(5,1));
        expectedDogPositions2.add(new Coordinate(6,4));
        expectedDogPositions2.add(new Coordinate(6,6));
        assertDogPositions(expectedDogPositions2,state2);

        state2.movePawn(new Coordinate(3,1),new Coordinate(4,0));
        assertFoxPosition(new Coordinate(4, 0), state2);
        assertEquals(state2.getActivePlayer(),1);

    }

    @Test
    void testCalculatePossibleMoveCoordinates() {
        FoxcatcherState state = new FoxcatcherState(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        },1);
        assertFoxPosition(new Coordinate(2, 2), state);

        Vector<Coordinate> expectedDogPositions = new Vector<Coordinate>();
        expectedDogPositions.add(new Coordinate(1,1));
        expectedDogPositions.add(new Coordinate(3,3));
        expectedDogPositions.add(new Coordinate(3,1));
        expectedDogPositions.add(new Coordinate(1,3));
        assertDogPositions(expectedDogPositions,state);

        assertTrue(state.calculatePossibleMoveCoordinates(new Coordinate(2, 2)).isEmpty());
        assertFalse(state.calculatePossibleMoveCoordinates(new Coordinate(1, 1)).isEmpty());
        assertFalse(state.calculatePossibleMoveCoordinates(new Coordinate(3, 3)).isEmpty());
        assertEquals(state.calculatePossibleMoveCoordinates(new Coordinate(1, 1)), new Vector<Coordinate>(Arrays.asList(new Coordinate(0, 0), new Coordinate(0, 2))));
        assertEquals(state.calculatePossibleMoveCoordinates(new Coordinate(1, 3)), new Vector<Coordinate>(Arrays.asList(new Coordinate(0, 2), new Coordinate(0, 4))));
        assertEquals(state.calculatePossibleMoveCoordinates(new Coordinate(3, 3)), new Vector<Coordinate>(Collections.singletonList(new Coordinate(2, 4))));

        FoxcatcherState state2 = new FoxcatcherState(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 1},
                {0, 0, 0, 0, 2, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        },1);
        assertFoxPosition(new Coordinate(4, 4), state2);
        assertFalse(state2.calculatePossibleMoveCoordinates(new Coordinate(3, 5)).isEmpty());
        assertFalse(state2.calculatePossibleMoveCoordinates(new Coordinate(3, 7)).isEmpty());
        assertFalse(state2.calculatePossibleMoveCoordinates(new Coordinate(6, 4)).isEmpty());
        assertTrue(state2.calculatePossibleMoveCoordinates(new Coordinate(4, 6)).isEmpty());
        assertEquals(state2.calculatePossibleMoveCoordinates(new Coordinate(3, 5)), new Vector<Coordinate>(Arrays.asList(new Coordinate(2, 4), new Coordinate(2, 6))));
        assertEquals(state2.calculatePossibleMoveCoordinates(new Coordinate(6, 4)), new Vector<Coordinate>(Arrays.asList(new Coordinate(5, 3), new Coordinate(5, 5))));
        assertEquals(state2.calculatePossibleMoveCoordinates(new Coordinate(4, 4)), new Vector<Coordinate>(Arrays.asList(new Coordinate(3, 3), new Coordinate(5, 3),new Coordinate(5, 5))));
        assertEquals(state2.calculatePossibleMoveCoordinates(new Coordinate(3, 7)), new Vector<Coordinate>(Collections.singletonList(new Coordinate(2, 6))));

        Vector<Coordinate> expectedDogPositions2 = new Vector<Coordinate>();
        expectedDogPositions.add(new Coordinate(3,5));
        expectedDogPositions.add(new Coordinate(4,6));
        expectedDogPositions.add(new Coordinate(3,7));
        expectedDogPositions.add(new Coordinate(6,4));
        assertDogPositions(expectedDogPositions,state2);

    }

    @Test
    void testIsGameOwer() {
        assertFalse(new FoxcatcherState().isGameOver());
        assertTrue(new FoxcatcherState(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        },1).isGameOver());
        assertTrue(new FoxcatcherState(new int[][]{
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 1},

        },1).isGameOver());

        assertTrue(new FoxcatcherState(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 1, 0, 1},
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        },1).isGameOver());
    }

    @Test
    void testCanMovePawn() {
        FoxcatcherState state = new FoxcatcherState();
        assertFalse(state.canMovePawn(new Coordinate(7, 1), new Coordinate(6, 1)));
        assertFalse(state.canMovePawn(new Coordinate(0, 2), new Coordinate(1, 2)));
        assertFalse(state.canMovePawn(new Coordinate(0, 0), new Coordinate(1, 1)));
        assertFalse(state.canMovePawn(new Coordinate(7, 7), new Coordinate(6, 7)));
        assertTrue(state.canMovePawn(new Coordinate(7, 1), new Coordinate(6, 2)));
        assertTrue(state.canMovePawn(new Coordinate(7, 1), new Coordinate(6, 0)));
        assertTrue(state.canMovePawn(new Coordinate(7, 7), new Coordinate(6, 6)));
        assertFalse(state.canMovePawn(new Coordinate(0, 2), new Coordinate(1, 1)));
        assertFalse(state.canMovePawn(new Coordinate(7, 1), new Coordinate(5, 1)));
    }

    @Test
    void testToString() {

        FoxcatcherState state = new FoxcatcherState();
        assertEquals("0 0 2 0 0 0 0 0 \n"
                + "0 0 0 0 0 0 0 0 \n"
                + "0 0 0 0 0 0 0 0 \n"
                + "0 0 0 0 0 0 0 0 \n"
                + "0 0 0 0 0 0 0 0 \n"
                + "0 0 0 0 0 0 0 0 \n"
                + "0 0 0 0 0 0 0 0 \n"
                + "0 1 0 1 0 1 0 1 \n", state.toString());

        FoxcatcherState state2 = new FoxcatcherState(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        },2);

        assertEquals("0 0 0 0 0 0 0 0 \n"
                + "0 1 0 1 0 0 0 0 \n"
                + "0 0 2 0 0 0 0 0 \n"
                + "0 1 0 1 0 0 0 0 \n"
                + "0 0 0 0 0 0 0 0 \n"
                + "0 0 0 0 0 0 0 0 \n"
                + "0 0 0 0 0 0 0 0 \n"
                + "0 0 0 0 0 0 0 0 \n", state2.toString());

    }

    @Test
    void testGetNextActivePlayer(){

        FoxcatcherState state = new FoxcatcherState();
        assertEquals(2,state.getNextActivePlayer());
        state.movePawn(new Coordinate(7,1),new Coordinate(6,0));
        assertEquals(1,state.getNextActivePlayer());

    }

    @Test
    void testGetPawn(){

        FoxcatcherState state = new FoxcatcherState(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        },2);
        assertEquals(Pawn.FOX,state.getPawn(new Coordinate(2,2)));
        assertEquals(Pawn.DOG,state.getPawn(new Coordinate(3,1)));
        assertEquals(Pawn.DOG,state.getPawn(new Coordinate(3,3)));
        assertEquals(Pawn.DOG,state.getPawn(new Coordinate(1,1)));
        assertEquals(Pawn.DOG,state.getPawn(new Coordinate(1,3)));
        assertEquals(Pawn.EMPTY,state.getPawn(new Coordinate(7,7)));
        assertEquals(Pawn.EMPTY,state.getPawn(new Coordinate(6,7)));

    }
    @Test
    void testWhoIsTheWinner(){
        FoxcatcherState state = new FoxcatcherState(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        },2);

        assertEquals(1,state.whoIsTheWinner());

        FoxcatcherState state2 = new FoxcatcherState(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 1},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        },2);

        assertEquals(2,state2.whoIsTheWinner());


    }
}