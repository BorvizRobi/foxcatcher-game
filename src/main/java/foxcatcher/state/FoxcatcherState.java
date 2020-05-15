package foxcatcher.state;

import java.util.Vector;
/**
 * Class representing the state of the game.
 */
public class FoxcatcherState implements Cloneable{

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

    public FoxcatcherState(int [][] a) {
        chessBoard = new ChessBoard(a);
    }

    public void movePawn(Coordinate moveFromCoordinate,Coordinate moveToCoordinate){

        if(!canMovePawn(moveFromCoordinate,moveToCoordinate)) throw new IllegalArgumentException();

        Tile moveFromTile = chessBoard.getTile(moveFromCoordinate);
        Tile moveToTile = chessBoard.getTile(moveToCoordinate);

        Vector<Coordinate> possibleMoveCoordinates = calculatePossibleMoveCoordinates(moveFromTile);

            moveToTile.setPawn(moveFromTile.getPawn());
            moveFromTile.setPawn(Pawn.EMPTY);



    }

    public Vector<Coordinate> calculatePossibleMoveCoordinates(Tile moveFromTile){

        Vector<Coordinate> possibleMoveCoordinates=new Vector<Coordinate>();

        Direction[] possibleMoveDirections= moveFromTile.getPawn().getMoveDirections();

        if(possibleMoveDirections!=null)
        for(Direction direction : possibleMoveDirections ){

            Coordinate moveCoordinate=moveFromTile.getCoordinate().moveToDirection(direction);
            if(ChessBoard.isValidCoordinate(moveCoordinate) && chessBoard.getTile(moveCoordinate).isEmpty()) possibleMoveCoordinates.add(moveCoordinate);

        }

        return possibleMoveCoordinates;

    }
    public boolean canMovePawn(Coordinate moveFromCoordinate,Coordinate moveToCoordinate){
        return calculatePossibleMoveCoordinates(chessBoard.getTile(moveFromCoordinate)).contains(moveToCoordinate);
    }

    public boolean isGameOwer(){
        return calculatePossibleMoveCoordinates(chessBoard.getTile( chessBoard.getFoxPosition())).isEmpty();
    }

    public FoxcatcherState clone() {
        FoxcatcherState copy = null;
        try {
            copy = (FoxcatcherState) super.clone();
        } catch (CloneNotSupportedException e) {
        }
        copy.chessBoard = new ChessBoard();
        for (int i = 0; i < chessBoard.getTiles().length; ++i) {
            copy.chessBoard.getTiles()[i] = chessBoard.getTiles()[i].clone();
        }

        return copy;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Tile[] row : chessBoard.getTiles()) {
            for (Tile tile : row) {
                sb.append(tile.getPawn()).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        FoxcatcherState state = new FoxcatcherState();
        System.out.println(state);

        state.movePawn(new Coordinate(7,1),new Coordinate(6,2));
        System.out.println(state);
        state.movePawn(new Coordinate(0,2),new Coordinate(1,1));
        System.out.println(state);


    }

}
