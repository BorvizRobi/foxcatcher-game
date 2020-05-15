package foxcatcher.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    @Test
    void testOf() {
        assertEquals(Pawn.EMPTY, Pawn.of(0));
        assertEquals(Pawn.DOG,Pawn.of(1));
        assertEquals(Pawn.FOX,Pawn.of(2));
        assertThrows(IllegalArgumentException.class,()->Pawn.of(-1));
        assertThrows(IllegalArgumentException.class,()->Pawn.of(3));
    }
}