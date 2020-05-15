package foxcatcher.state;

import java.util.Vector;

public class FoxcatcherState implements Cloneable{

    public ChessBoard chessBoard;
    public Coordinate FoxPosition;
    public Coordinate Dog1Position;
    public Coordinate Dog2Position;
    public Coordinate Dog3Position;
    public Coordinate Dog4Position;

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
        if(!isValidBoard(a)){
            throw new IllegalArgumentException();
        }
        chessBoard = new ChessBoard(a);
    }

    public boolean isValidBoard(int [][] a){

        if (a == null || a.length != 8) {
            return false;
        }
        boolean foundEmptyTiles = false;
        boolean foundFox = false;
        boolean foundDogs = false;

        int EmptyTiles = 0;
        int Dogs = 0;

        for (int[] row : a) {
            if (row == null || row.length != 8) {
                return false;
            }
            for (int space : row) {
                if (space < 0 || space >= Pawn.values().length) {
                    return false;
                }
                if (space == Pawn.EMPTY.getValue()) {

                    EmptyTiles++;
                }
                if (space == Pawn.DOG.getValue()) {

                    Dogs++;
                }
                if (space == Pawn.FOX.getValue()) {

                    foundFox = true;
                }
            }
        }
        if (EmptyTiles==59) foundEmptyTiles = true;
        if (Dogs==4) foundDogs = true;

        return foundEmptyTiles && foundDogs && foundFox;

    }

    public void movePawn(Coordinate moveFromCoordinate,Coordinate moveToCoordinate){

        Tile moveFromTile = chessBoard.getTile(moveFromCoordinate);
        Tile moveToTile = chessBoard.getTile(moveToCoordinate);

        Vector<Coordinate> possibleMoveCoordinates = calculatePossibleMoveCoordinates(moveFromTile);

        if(possibleMoveCoordinates == null)
            throw new IllegalArgumentException();

        if(possibleMoveCoordinates.contains(moveToCoordinate)) {

            moveToTile.setPawn(moveFromTile.getPawn());
            moveFromTile.setPawn(Pawn.EMPTY);

        }
        else throw new IllegalArgumentException();


    }

    public Vector<Coordinate> calculatePossibleMoveCoordinates(Tile moveFromTile){

        Vector<Coordinate> possibleMoveCoordinates=new Vector<Coordinate>();

        Direction[] possibleMoveDirections= moveFromTile.getPawn().getMoveDirections();


        for(Direction direction : possibleMoveDirections ){

            Coordinate moveCoordinate=moveFromTile.getCoordinate().moveToDirection(direction);
            if(ChessBoard.isValidCoordinate(moveCoordinate) && chessBoard.getTile(moveCoordinate).isEmpty()) possibleMoveCoordinates.add(moveCoordinate);

        }

        return possibleMoveCoordinates;

    }

    public boolean isGameOwer(){
        return calculatePossibleMoveCoordinates(chessBoard.getTile(FoxPosition)).isEmpty();
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
