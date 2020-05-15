package foxcatcher.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void testOf() {
        assertEquals(Direction.TOP_LEFT,Direction.of(-1,-1));
        assertEquals(Direction.TOP_RIGHT,Direction.of(-1,1));
        assertEquals(Direction.BOTTOM_LEFT,Direction.of(1,-1));
        assertEquals(Direction.BOTTOM_RIGHT,Direction.of(1,1));
        assertThrows(IllegalArgumentException.class,()->Direction.of(-1,0));
    }
}