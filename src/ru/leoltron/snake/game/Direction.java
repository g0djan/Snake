package ru.leoltron.snake.game;

import lombok.val;
import ru.leoltron.snake.util.GamePoint;

import java.util.HashMap;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private static HashMap<GamePoint, Direction> pointToDirection = new HashMap<>();

    static {
        for (val dir : Direction.values())
            pointToDirection.put(new GamePoint(dir.dx, dir.dy).normalized(), dir);
    }

    public final int dx;
    public final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Direction reversed() {
        return fromGamePoint(new GamePoint(dx, dy).reversed());
    }

    public static Direction fromGamePoint(GamePoint point) {
        return pointToDirection.getOrDefault(point.normalized(), null);
    }
}
