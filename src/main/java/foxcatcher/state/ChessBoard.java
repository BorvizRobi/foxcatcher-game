package foxcatcher.state;

import lombok.Data;

import java.util.Vector;


public class ChessBoard {

    private Vector<Vector<Square>> squares;

    public ChessBoard(){

         squares= new Vector<Vector<Square>>();
        for(int i = 0 ; i < 8; i++) {
            Vector<Square> row = new Vector<Square>();
            for (int j = 0; j < 8; j++) {
                Coordinate coordinate = new Coordinate(i , j);
                Square square = new Square(coordinate,null);
                row.add(square);
            }
            squares.add(row);
        }
    }

    public ChessBoard(int [][] a){

         squares= new Vector<Vector<Square>>();
        for(int i = 0 ; i < 8; i++) {
            Vector<Square> row = new Vector<Square>();
            for (int j = 0; j < 8; j++) {
                Coordinate coordinate = new Coordinate(i , j);
                Square square = new Square(coordinate, a[i][j]==2?new FoxPawn():(a[i][j]==1?new DogPawn():null));
                row.add(square);
            }
            squares.add(row);
        }
    }

    public static boolean isValidCoordinate(Coordinate coordinate){
        int x = coordinate.getX();
        int y = coordinate.getY();
        return (x >=0 && x<=7) && (y >=0 && y <= 7);

    }

    public void movePawn(Coordinate moveFromCoordinate,Coordinate moveToCoordinate){

        Square moveFromSquare = getSquare(moveFromCoordinate);
        Square moveToSquare = getSquare(moveToCoordinate);

        Vector<Coordinate> possibleMoveCoordinates = calculatePossibleMoveCoordinates(moveFromSquare);

        if(possibleMoveCoordinates.contains(moveToCoordinate)) {

            moveToSquare.setPawn(moveFromSquare.getPawn());
            moveFromSquare.setPawn(null);


        }


    }
    public Vector<Coordinate> calculatePossibleMoveCoordinates(Square moveFromSquare){

        Vector<Coordinate> possibleMoveCoordinates=new Vector<Coordinate>();
        Vector<Direction> possibleMoveDirections= moveFromSquare.getPawn().getPossibleMoveDirections();

        for(Direction direction : possibleMoveDirections ){
            Coordinate moveCoordinate=moveFromSquare.getCoordinate().moveToDirection(direction);
            if(isValidCoordinate(moveCoordinate) && getSquare(moveCoordinate).isEmpty()) possibleMoveCoordinates.add(moveCoordinate);
        }



        return possibleMoveCoordinates;
    }

    public void setSquare(Coordinate coordinate, Pawn pawn) {
        Square square = getSquare(coordinate);
        square.setPawn(pawn);
    }

    public Vector<Vector<Square>> getSquares() {
        return squares;
    }

    public Square getSquare(Coordinate coordinate){

        return squares.get(coordinate.getX()).get(coordinate.getY());
    }

    public Square getSquare(int x, int y){

        return squares.get(x).get(y);
    }
    public Vector<Vector<Square>> getSquare(){

        return squares;
    }

}

