package ru.leoltron.snaketests;

import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import ru.leoltron.snake.game.Direction;

import java.awt.*;

public class DirectionsTests extends Assert {
    @Test
    public void testTranslationUp() {
        val point = new Point(5, 5);
        assertEquals(Direction.UP.translatePoint(point), new Point(5, 4));
    }

    @Test
    public void testTranslationDown() {
        val point = new Point(5, 5);
        assertEquals(Direction.DOWN.translatePoint(point), new Point(5, 6));
    }

    @Test
    public void testTranslationLeft() {
        val point = new Point(5, 5);
        assertEquals(Direction.LEFT.translatePoint(point), new Point(4, 5));
    }

    @Test
    public void testTranslationRight() {
        val point = new Point(5, 5);
        assertEquals(Direction.RIGHT.translatePoint(point), new Point(6, 5));
    }
}
