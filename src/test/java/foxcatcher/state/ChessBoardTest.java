package foxcatcher.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessBoardTest {

    @Test
    void testIsValidCoordinate() {
        assertTrue(ChessBoard.isValidCoordinate(new Coordinate(1,1)));
        assertTrue(ChessBoard.isValidCoordinate(new Coordinate(7,7)));
        assertTrue(ChessBoard.isValidCoordinate(new Coordinate(0,0)));
        assertFalse(ChessBoard.isValidCoordinate(new Coordinate(-1,-1)));
        assertFalse(ChessBoard.isValidCoordinate(new Coordinate(8,8)));
        assertFalse(ChessBoard.isValidCoordinate(new Coordinate(108,-5)));

    }




}