package foxcatcher.state;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Vector;

/**
 * Class for representing a chessboard.
 */
public class ChessBoard {

    @Getter
    private final Tile[][] tiles;
    @Setter
    @Getter
    private Coordinate foxPosition;
    @Setter
    @Getter
    private Vector<Coordinate> dogPositions;




    public ChessBoard(){

        tiles= new Tile[8][8];

        for(int i = 0 ; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                tiles[i][j]=new Tile(new Coordinate(i,j),Pawn.EMPTY);
            }
        }
    }

    public ChessBoard(int [][] a){

        if(!isValidBoard(a)) {
            throw new IllegalArgumentException();
        }

        dogPositions= new Vector<Coordinate>();
        tiles= new Tile[8][8];

        for(int i = 0 ; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                tiles[i][j]=new Tile(new Coordinate(i,j),Pawn.of(a[i][j]));
                if (Pawn.of(a[i][j]) == Pawn.FOX) foxPosition = new Coordinate(i,j);
                if (Pawn.of(a[i][j]) == Pawn.DOG) dogPositions.add(new Coordinate(i,j));
            }
        }
    }

    public static boolean isValidCoordinate(Coordinate coordinate){
        int x = coordinate.getX();
        int y = coordinate.getY();
        return (x >=0 && x<=7) && (y >=0 && y <= 7);

    }


    public Tile getTile(Coordinate coordinate){

        return tiles[coordinate.getX()][coordinate.getY()];
    }

    public Tile getTile(int x, int y){

        return tiles[x][y];
    }

    private boolean isValidBoard(int [][] a){

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


}

