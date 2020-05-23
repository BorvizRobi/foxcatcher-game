package foxcatcher.state;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.util.Vector;

/**
 * Class representing the state of the game.
 */
@Data
@Slf4j
public class FoxcatcherState implements Cloneable{

    /**
     * The array representing the initial configuration of the chessboard.
     */
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

    /**
     * The array storing the current configuration of the chessboard.
     */
    @Setter(AccessLevel.NONE)
    private  Pawn[][] chessBoard;

    /**
     * The Coordinate of the fox.
     */
    @Setter(AccessLevel.NONE)
    private Coordinate foxPosition;

    /**
     * The Coordinates of the dogs.
     */
    @Setter(AccessLevel.NONE)
    private Vector<Coordinate> dogPositions;

    /**
     * Returns the pawn which can be found in the given coordinates of the chessboard.
     * @param  coordinate coordinate of the chessboard.
     * @return The pawn which can be found in the given coordinate.
     * @throws IllegalArgumentException if the coordinate does not represent a valid coordinate of the chessboard.
     */
    public Pawn getPawn(Coordinate coordinate){
        if(!isValidCoordinate(coordinate)){
            throw new IllegalArgumentException();
        }
        return chessBoard[coordinate.getX()][coordinate.getY()];

    }


    /**
     * Creates a {@code FoxcatcherState} object representing the (original)
     * initial state of the game.
     */
    public FoxcatcherState() {
        this(INITIAL);
    }

    /**
     * Creates a {@code FoxcatcherState} object that is initialized it with
     * the specified array.
     *
     * @param a an array of size 3&#xd7;3 representing the initial configuration
     *          of the chessboard.
     * @throws IllegalArgumentException if the array does not represent a valid
     *                                  configuration of the chessboard.
     */
    public FoxcatcherState(int [][] a) {

        if (!isValidChessBoard(a)) {
            throw new IllegalArgumentException();
        }

        initChessBoard(a);
    }
    /**
     * Checks whether the chessboard is valid.
     *
     * @return {@code true} if the chessboard is valid, {@code false} otherwise
     */

    private boolean isValidChessBoard(int [][] a){

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

    public void initChessBoard(int [][] a){

        dogPositions= new Vector<Coordinate>();
        chessBoard= new Pawn[8][8];

        for(int i = 0 ; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                chessBoard[i][j]= Pawn.of(a[i][j]);
                if (Pawn.of(a[i][j]) == Pawn.FOX) foxPosition = new Coordinate(i,j);
                if (Pawn.of(a[i][j]) == Pawn.DOG) dogPositions.add(new Coordinate(i,j));
            }
        }
    }


    /**
     * Checks whether the game is over.
     *
     * @return {@code true} if the game is over, {@code false} otherwise
     */
    public boolean isGameOwer(){
        return calculatePossibleMoveCoordinates(foxPosition).isEmpty();
    }

    public boolean canMovePawn(Coordinate moveFromCoordinate,Coordinate moveToCoordinate){
        return calculatePossibleMoveCoordinates(moveFromCoordinate).contains(moveToCoordinate);
    }

    public Vector<Coordinate> calculatePossibleMoveCoordinates(Coordinate moveFromCoordinate){



        Vector<Coordinate> possibleMoveCoordinates=new Vector<Coordinate>();

        Direction[] possibleMoveDirections= getPawn(moveFromCoordinate).getMoveDirections();

        if(possibleMoveDirections!=null)
        for(Direction direction : possibleMoveDirections ){

            Coordinate moveCoordinate=moveFromCoordinate.moveToDirection(direction);
            if(isValidCoordinate(moveCoordinate) && getPawn(moveCoordinate) == Pawn.EMPTY) possibleMoveCoordinates.add(moveCoordinate);

        }

        return possibleMoveCoordinates;

    }

    public void movePawn(Coordinate moveFromCoordinate,Coordinate moveToCoordinate){

        if(!canMovePawn(moveFromCoordinate,moveToCoordinate)) throw new IllegalArgumentException();


        Vector<Coordinate> possibleMoveCoordinates = calculatePossibleMoveCoordinates(moveFromCoordinate);

        if(getPawn(moveFromCoordinate).equals(Pawn.FOX)){
            foxPosition=moveToCoordinate;
        }

        if(getPawn(moveFromCoordinate).equals(Pawn.DOG)){

            for (int i=0;i<dogPositions.size();i++){
                if(dogPositions.get(i).equals(moveFromCoordinate)){
                    dogPositions.set(i,moveToCoordinate);
                }
            }
        }

        log.info("Pawn at ({},{}) is moved to ({},{})",moveFromCoordinate.getX(),moveFromCoordinate.getY(),moveToCoordinate.getX(),moveToCoordinate.getY());
        chessBoard[moveToCoordinate.getX()][moveToCoordinate.getY()] = getPawn(moveFromCoordinate);
        chessBoard[moveFromCoordinate.getX()][moveFromCoordinate.getY()]=Pawn.EMPTY;



    }


    public static boolean isValidCoordinate(Coordinate coordinate){
        int x = coordinate.getX();
        int y = coordinate.getY();
        return (x >=0 && x<=7) && (y >=0 && y <= 7);

    }



    public FoxcatcherState clone() {
        FoxcatcherState copy = null;
        try {
            copy = (FoxcatcherState) super.clone();
        } catch (CloneNotSupportedException e) {
        }
        copy.chessBoard = new Pawn[chessBoard.length][];
        for (int i = 0; i < chessBoard.length; ++i) {
            copy.chessBoard[i] = chessBoard[i].clone();
        }

        return copy;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Pawn[] row : chessBoard) {
            for (Pawn pawn : row) {
                sb.append(pawn).append(' ');
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
