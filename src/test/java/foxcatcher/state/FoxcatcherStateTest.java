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

    private void assertDogPositions(Vector<Coordinate> expectedCoordinate, FoxcatcherState actual) {
        assertAll(
                () -> assertTrue(expectedCoordinate.containsAll(actual.getDogPositions()))
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

        FoxcatcherState probaState = new FoxcatcherState(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        },2);
        probaState.movePawn(new Coordinate(2,2),new Coordinate(1,1));
        assertFoxPosition(new Coordinate(1, 1), probaState);
        assertEquals(probaState.getActivePlayer(),1);

        probaState.movePawn(new Coordinate(6,2),new Coordinate(5,3));
        assertEquals(probaState.getActivePlayer(),2);

        Vector<Coordinate> expectedDogPositions = new Vector<Coordinate>();
        expectedDogPositions.add(new Coordinate(5,3));
        expectedDogPositions.add(new Coordinate(5,1));
        expectedDogPositions.add(new Coordinate(3,3));
        expectedDogPositions.add(new Coordinate(4,4));
        assertDogPositions(expectedDogPositions,probaState);

        probaState.movePawn(new Coordinate(1,1),new Coordinate(0,2));
        assertFoxPosition(new Coordinate(0, 2), probaState);
        assertEquals(probaState.getActivePlayer(),1);

        FoxcatcherState probaState2 = new FoxcatcherState(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        },2);
        probaState2.movePawn(new Coordinate(2,2),new Coordinate(3,1));
        assertFoxPosition(new Coordinate(3, 1), probaState2);
        assertEquals(probaState2.getActivePlayer(),1);

        probaState2.movePawn(new Coordinate(6,2),new Coordinate(5,1));
        assertEquals(probaState2.getActivePlayer(),2);

        Vector<Coordinate> expectedDogPositions2 = new Vector<Coordinate>();
        expectedDogPositions2.add(new Coordinate(6,0));
        expectedDogPositions2.add(new Coordinate(5,1));
        expectedDogPositions2.add(new Coordinate(6,4));
        expectedDogPositions2.add(new Coordinate(6,6));
        assertDogPositions(expectedDogPositions2,probaState2);

        probaState2.movePawn(new Coordinate(3,1),new Coordinate(4,0));
        assertFoxPosition(new Coordinate(4, 0), probaState2);
        assertEquals(probaState2.getActivePlayer(),1);

    }

    @Test
    void testCalculatePossibleMoveCoordinates() {
        FoxcatcherState probaState = new FoxcatcherState(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        },1);
        assertFoxPosition(new Coordinate(2, 2), probaState);

        Vector<Coordinate> expectedDogPositions = new Vector<Coordinate>();
        expectedDogPositions.add(new Coordinate(1,1));
        expectedDogPositions.add(new Coordinate(3,3));
        expectedDogPositions.add(new Coordinate(3,1));
        expectedDogPositions.add(new Coordinate(1,3));
        assertDogPositions(expectedDogPositions,probaState);

        assertTrue(probaState.calculatePossibleMoveCoordinates(new Coordinate(2, 2)).isEmpty());
        assertFalse(probaState.calculatePossibleMoveCoordinates(new Coordinate(1, 1)).isEmpty());
        assertFalse(probaState.calculatePossibleMoveCoordinates(new Coordinate(3, 3)).isEmpty());
        assertEquals(probaState.calculatePossibleMoveCoordinates(new Coordinate(1, 1)), new Vector<Coordinate>(Arrays.asList(new Coordinate(0, 0), new Coordinate(0, 2))));
        assertEquals(probaState.calculatePossibleMoveCoordinates(new Coordinate(1, 3)), new Vector<Coordinate>(Arrays.asList(new Coordinate(0, 2), new Coordinate(0, 4))));
        assertEquals(probaState.calculatePossibleMoveCoordinates(new Coordinate(3, 3)), new Vector<Coordinate>(Collections.singletonList(new Coordinate(2, 4))));

        FoxcatcherState probaState2 = new FoxcatcherState(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 1},
                {0, 0, 0, 0, 2, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        },1);
        assertFoxPosition(new Coordinate(4, 4), probaState2);
        assertFalse(probaState2.calculatePossibleMoveCoordinates(new Coordinate(3, 5)).isEmpty());
        assertFalse(probaState2.calculatePossibleMoveCoordinates(new Coordinate(3, 7)).isEmpty());
        assertFalse(probaState2.calculatePossibleMoveCoordinates(new Coordinate(6, 4)).isEmpty());
        assertTrue(probaState2.calculatePossibleMoveCoordinates(new Coordinate(4, 6)).isEmpty());
        assertEquals(probaState2.calculatePossibleMoveCoordinates(new Coordinate(3, 5)), new Vector<Coordinate>(Arrays.asList(new Coordinate(2, 4), new Coordinate(2, 6))));
        assertEquals(probaState2.calculatePossibleMoveCoordinates(new Coordinate(6, 4)), new Vector<Coordinate>(Arrays.asList(new Coordinate(5, 3), new Coordinate(5, 5))));
        assertEquals(probaState2.calculatePossibleMoveCoordinates(new Coordinate(4, 4)), new Vector<Coordinate>(Arrays.asList(new Coordinate(3, 3), new Coordinate(5, 3),new Coordinate(5, 5))));
        assertEquals(probaState2.calculatePossibleMoveCoordinates(new Coordinate(3, 7)), new Vector<Coordinate>(Collections.singletonList(new Coordinate(2, 6))));

        Vector<Coordinate> expectedDogPositions2 = new Vector<Coordinate>();
        expectedDogPositions.add(new Coordinate(3,5));
        expectedDogPositions.add(new Coordinate(4,6));
        expectedDogPositions.add(new Coordinate(3,7));
        expectedDogPositions.add(new Coordinate(6,4));
        assertDogPositions(expectedDogPositions,probaState2);

    }

    @Test
    void testIsGameOwer() {
        assertFalse(new FoxcatcherState().isGameOwer());
        assertTrue(new FoxcatcherState(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        },1).isGameOwer());
        assertTrue(new FoxcatcherState(new int[][]{
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 1},

        },1).isGameOwer());

        assertTrue(new FoxcatcherState(new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 1, 0, 1},
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        },1).isGameOwer());
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