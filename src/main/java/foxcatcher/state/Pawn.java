package foxcatcher.state;

import lombok.Data;

/**
 * Class representing the empty pawn and the possible pawns of the game.
 */

public enum Pawn {

    EMPTY(null),
    DOG(new Direction[]{Direction.TOP_LEFT,Direction.TOP_RIGHT}),
    FOX(new Direction[]{Direction.TOP_LEFT,Direction.TOP_RIGHT,Direction.BOTTOM_LEFT,Direction.BOTTOM_RIGHT});

    private final Direction[] moveDirections;


    private Pawn(Direction[] moveDirections) {
        this.moveDirections = moveDirections;
    }



    /**
     * Returns the instance represented by the value specified.
     *
     * @param value the value representing an instance
     * @return the instance represented by the value specified
     * @throws IllegalArgumentException if the value specified does not
     * represent an instance
     */
    public static foxcatcher.state.Pawn of(int value) {
        if (value < 0 || value >= values().length) {
            throw new IllegalArgumentException();
        }
        return values()[value];
    }

    /**
     * Returns the integer value that represents this instance.
     *
     * @return the integer value that represents this instance
     */
    public int getValue() {
        return ordinal();
    }


    public String toString() {
        return Integer.toString(ordinal());
    }

    public Direction[] getMoveDirections() {
        return moveDirections;
    }

}

