package foxcatcher.state;

/**
 * Enum representing the four diagonal directions.
 */
public enum Direction {

    TOP_LEFT(-1, -1),
    TOP_RIGHT(-1, 1),
    BOTTOM_LEFT(1, -1),
    BOTTOM_RIGHT(1, 1);

    private int dx;
    private int dy;

    private Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Returns the change in the x-coordinate when moving a step in this
     * direction.
     *
     * @return the change in the x-coordinate when moving a step in this
     * direction
     */
    public int getDx() {
        return dx;
    }

    /**
     * Returns the change in the y-coordinate when moving a step in this
     * direction.
     *
     * @return the change in the y-coordinate when moving a step in this
     * direction
     */
    public int getDy() {
        return dy;
    }

    /**
     * Returns the direction that corresponds to the changes in the x-coordinate
     * and the y-coordinate specified.
     *
     * @param dx the change in the x-coordinate
     * @param dy the change in the y-coordinate
     * @return the direction that corresponds to the changes in the x-coordinate
     * and the y-coordinate specified
     */
    public static foxcatcher.state.Direction of(int dx, int dy) {
        for (foxcatcher.state.Direction direction : values()) {
            if (direction.dx == dx && direction.dy == dy) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }



}
