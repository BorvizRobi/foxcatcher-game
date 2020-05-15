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

