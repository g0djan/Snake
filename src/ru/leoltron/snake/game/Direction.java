package ru.leoltron.snake.game;

import lombok.val;

import java.awt.*;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Point translatePoint(Point point) {
        val newPoint = new Point(point);
        newPoint.translate(dx, dy);
        return newPoint;
    }
}
