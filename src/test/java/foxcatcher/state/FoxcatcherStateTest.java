package foxcatcher.state;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class FoxcatcherStateTest {

    private void assertFoxPosition(Coordinate expectedCoordinate,FoxcatcherState actual){
        assertAll(
                () ->assertEquals(expectedCoordinate,actual.getFoxPosition())
        );
    }

    @Test
    void testIsValidCoordinate() {
        assertTrue(FoxcatcherState.isValidCoordinate(new Coordinate(1,1)));
        assertTrue(FoxcatcherState.isValidCoordinate(new Coordinate(7,7)));
        assertTrue(FoxcatcherState.isValidCoordinate(new Coordinate(0,0)));
        assertFalse(FoxcatcherState.isValidCoordinate(new Coordinate(-1,-1)));
        assertFalse(FoxcatcherState.isValidCoordinate(new Coordinate(8,8)));
        assertFalse(FoxcatcherState.isValidCoordinate(new Coordinate(108,-5)));

    }

    @Test
    void testMovePawn() {
/*
        assertArrayEquals((new FoxcatcherState (new int[][] {
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 1, 0, 1},

        }).movePawn(new Coordinate(1,1),new Coordinate(2,2))),

                new FoxcatcherState (new int[][] {
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 2, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 1, 0, 1, 0, 1},

                }).chessBoard.getTiles());*/
    }

    @Test
    void testCalculatePossibleMoveCoordinates() {
        FoxcatcherState probaState = new FoxcatcherState (new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        });
        assertTrue(probaState.calculatePossibleMoveCoordinates(new Coordinate(2,2)).isEmpty());
        assertFalse(probaState.calculatePossibleMoveCoordinates(new Coordinate(1,1)).isEmpty());
        assertFalse(probaState.calculatePossibleMoveCoordinates(new Coordinate(3,3)).isEmpty());
        assertEquals(probaState.calculatePossibleMoveCoordinates(new Coordinate(1,1)),new Vector<Coordinate>(Arrays.asList(new Coordinate(0,0),new Coordinate(0,2))));
        assertEquals(probaState.calculatePossibleMoveCoordinates(new Coordinate(1,3)),new Vector<Coordinate>(Arrays.asList(new Coordinate(0,2),new Coordinate(0,4))));
        assertEquals(probaState.calculatePossibleMoveCoordinates(new Coordinate(3,3)),new Vector<Coordinate>(Collections.singletonList(new Coordinate(2,4))));

        FoxcatcherState probaState2 = new FoxcatcherState (new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 2, 0, 0, 0},
                {0, 0, 0, 1, 0, 1, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        });

        assertFalse(probaState2.calculatePossibleMoveCoordinates(new Coordinate(3,4)).isEmpty());
        assertFalse(probaState2.calculatePossibleMoveCoordinates(new Coordinate(4,7)).isEmpty());
        assertFalse(probaState2.calculatePossibleMoveCoordinates(new Coordinate(6,4)).isEmpty());
        assertEquals(probaState2.calculatePossibleMoveCoordinates(new Coordinate(3,4)),new Vector<Coordinate>(Arrays.asList(new Coordinate(2,3),new Coordinate(2,5))));
        assertEquals(probaState2.calculatePossibleMoveCoordinates(new Coordinate(6,4)),new Vector<Coordinate>(Arrays.asList(new Coordinate(5,3),new Coordinate(5,5))));
        assertEquals(probaState2.calculatePossibleMoveCoordinates(new Coordinate(4,7)),new Vector<Coordinate>(Collections.singletonList(new Coordinate(3,6))));

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