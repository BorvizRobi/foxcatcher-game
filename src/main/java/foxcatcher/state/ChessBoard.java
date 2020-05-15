package foxcatcher.state;

import lombok.Data;

import java.util.Vector;


public class ChessBoard {

    private final Tile[][] tiles;

    public ChessBoard(){

         tiles= new Tile[8][8];
        for(int i = 0 ; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                tiles[i][j]=new Tile(new Coordinate(i,j),Pawn.EMPTY);

            }
        }
    }

    public ChessBoard(int [][] a){

        tiles= new Tile[8][8];
        for(int i = 0 ; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                tiles[i][j]=new Tile(new Coordinate(i,j),Pawn.of(a[i][j]));
            }
        }
    }

    public static boolean isValidCoordinate(Coordinate coordinate){
        int x = coordinate.getX();
        int y = coordinate.getY();
        return (x >=0 && x<=7) && (y >=0 && y <= 7);

    }

    public void movePawn(Coordinate moveFromCoordinate,Coordinate moveToCoordinate){

        Tile moveFromTile = getTile(moveFromCoordinate);
        Tile moveToTile = getTile(moveToCoordinate);

        Vector<Coordinate> possibleMoveCoordinates = calculatePossibleMoveCoordinates(moveFromTile);

        if(possibleMoveCoordinates.contains(moveToCoordinate)) {

            moveToTile.setPawn(moveFromTile.getPawn());
            moveFromTile.setPawn(Pawn.EMPTY);


        }


    }
    public Vector<Coordinate> calculatePossibleMoveCoordinates(Tile moveFromTile){

        Vector<Coordinate> possibleMoveCoordinates=new Vector<Coordinate>();

        Direction[] possibleMoveDirections= moveFromTile.getPawn().getMoveDirections();

        for(Direction direction : possibleMoveDirections ){

            Coordinate moveCoordinate=moveFromTile.getCoordinate().moveToDirection(direction);
            if(isValidCoordinate(moveCoordinate) && getTile(moveCoordinate).isEmpty()) possibleMoveCoordinates.add(moveCoordinate);

        }

        return possibleMoveCoordinates;

    }


    public Tile[][] getTiles() {
         return tiles;
    }

    public Tile getTile(Coordinate coordinate){

        return tiles[coordinate.getX()][coordinate.getY()];
    }

    public Tile getTile(int x, int y){

        return tiles[x][y];
    }

    public Tile[][] getSquare(){

        return tiles;
    }

}

