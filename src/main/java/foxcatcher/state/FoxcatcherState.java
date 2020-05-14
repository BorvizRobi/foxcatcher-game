package foxcatcher.state;

import java.util.Vector;

public class FoxcatcherState {

    public ChessBoard chessBoard;

    public static final int[][] INITIAL = {
            {0, 0, 2, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
    };

    public FoxcatcherState() {
        chessBoard = new ChessBoard(INITIAL);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vector<Square> row : chessBoard.getSquares()) {
            for (Square square : row) {
                sb.append(square).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        FoxcatcherState state = new FoxcatcherState();
        System.out.println(state);
        state.chessBoard.movePawn(new Coordinate(7,1),new Coordinate(6,2));
        System.out.println(state);
        state.chessBoard.movePawn(new Coordinate(0,2),new Coordinate(1,1));
        System.out.println(state);
        state.chessBoard.movePawn(new Coordinate(6,2),new Coordinate(7,1));
        System.out.println(state);
        state.chessBoard.movePawn(new Coordinate(6,2),new Coordinate(5,1));
        System.out.println(state);
        state.chessBoard.movePawn(new Coordinate(5,1),new Coordinate(4,1));
        System.out.println(state);
    }

}
