package foxcatcher.state;

import lombok.AccessLevel;
import lombok.Data;
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
     * The active player.
     */
    @Setter(AccessLevel.NONE)
    private int activePlayer;

    /**
     * Advancing the active player.
     */
    private void advanceActivePlayer(){
        if (activePlayer == 1){
            activePlayer = 2;
        }
        else if(activePlayer==2){
            activePlayer=1;
        }
    }

    /**
     * Returns the next active player.
     * @return The next active player number.
     */
    public int getNextActivePlayer(){
        if (activePlayer == 1){
            return 2;
        }
        else return 1;

    }

    /**
     * Returns the pawn which can be found in the given coordinates of the chessboard.
     * @param  coordinate Coordinate of the chessboard.
     * @return The pawn which can be found in the given coordinate.
     * @throws IllegalArgumentException If the coordinate does not represent a valid coordinate of the chessboard.
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
        this(INITIAL,1);
    }

    /**
     * Creates a {@code FoxcatcherState} object that is initialized it with
     * the specified array.
     *
     * @param a An array of size 8&#xd7;8 representing the initial configuration
     *          of the chessboard.
     * @param activePlayer The active player.
     * @throws IllegalArgumentException If the array does not represent a valid
     *                                  configuration of the chessboard
     *                                  or the active player is not valid.
     */
    public FoxcatcherState(int [][] a,int activePlayer) {

        if (!isValidChessBoard(a) || !isValidActivePlayer(activePlayer)) {
            throw new IllegalArgumentException();
        }

        initChessBoard(a);
        this.activePlayer=activePlayer;
    }

    /**
     * Checks whether the active player is valid.
     *
     * @return {@code true} if the active player is valid, {@code false} otherwise
     */
    private boolean isValidActivePlayer(int activePlayer){
        return activePlayer == 1 || activePlayer == 2;
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
     * Checks whether the pawn can be moved from a specific coordinate to another coordinate on the chessboard.
     *
     * @param moveFromCoordinate A coordinate to move from.
     * @param moveToCoordinate A coordinate to move to.
     *
     * @return {@code true} if the move is possible, {@code false} otherwise.
     */
    public boolean canMovePawn(Coordinate moveFromCoordinate,Coordinate moveToCoordinate){

        return calculatePossibleMoveCoordinates(moveFromCoordinate).contains(moveToCoordinate) && getPawn(moveFromCoordinate).getValue()==activePlayer;

    }

    /**
     * Calculate all of the possible move coordinates, from the given coordinate on the chessboard.
     *
     * @param moveFromCoordinate A coordinate to move from.
     *
     *
     * @return {@code Vector<Coordinate>} which is containing all of the possible move coordinates.
     */
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

    /**
     * Moves the pawn from a specific coordinate to another coordinate on the chessboard.
     *
     * @param moveFromCoordinate A coordinate to move from.
     * @param moveToCoordinate A coordinate to move to.
     * @throws IllegalArgumentException If the move is not possible.
     */
    public void movePawn(Coordinate moveFromCoordinate, Coordinate moveToCoordinate){

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

        advanceActivePlayer();

    }
    /**
     * Checks whether the game is over.
     *
     * @return {@code true} if the game is over, {@code false} otherwise
     */
    public boolean isGameOwer(){

        if(calculatePossibleMoveCoordinates(foxPosition).isEmpty())return true;

        return getDogPositions().stream().allMatch(coordinate -> coordinate.getX()<foxPosition.getX());
    }

    /**
     * Returns the number of the winner player.
     *
     * @return {@code 1} if the winner is {@code activePlayer1},
     *         {@code 2} if the winner is {@code activePlayer2},
     *         {@code 0} otherwise.
     */
    public int whoIsTheWinner(){

        if(calculatePossibleMoveCoordinates(foxPosition).isEmpty()) return 1;

        if(getDogPositions().stream().allMatch(coordinate -> coordinate.getX()<foxPosition.getX())) return 2;

        return 0;

    }

    /**
     * Checks whether the coordinate is valid coordinate of the chessboard.
     *
     * @return {@code true} if the coordinate is valid, {@code false} otherwise
     */
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
