package ru.leoltron.snake.game;

import lombok.val;
import ru.leoltron.snake.util.GamePoint;
import ru.leoltron.snake.util.Pair;

import java.util.HashMap;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private static HashMap<GamePoint, Direction> pointToDirection = new HashMap<>();
    private static HashMap<Pair<Direction,Direction>, Double> angleBetweenDirections = new HashMap<>();

    static {
        for (val dir : Direction.values())
            pointToDirection.put(new GamePoint(dir.dx, dir.dy).normalized(), dir);


        angleBetweenDirections.put(Pair.create(DOWN, LEFT), Math.PI);
        angleBetweenDirections.put(Pair.create(LEFT, DOWN), Math.PI);
        angleBetweenDirections.put(Pair.create(DOWN, UP), Math.PI);
        angleBetweenDirections.put(Pair.create(RIGHT, DOWN), Math.PI / 2);
        angleBetweenDirections.put(Pair.create(DOWN, RIGHT), Math.PI / 2);
        angleBetweenDirections.put(Pair.create(RIGHT, LEFT), Math.PI / 2);
        angleBetweenDirections.put(Pair.create(LEFT, UP), 3 *Math.PI / 2);
        angleBetweenDirections.put(Pair.create(UP, LEFT), 3 * Math.PI / 2);
        angleBetweenDirections.put(Pair.create(LEFT, RIGHT), 3 * Math.PI / 2);
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

    public static double getSnakeImagesRotation(Direction direction1, Direction direction2){
        return angleBetweenDirections.getOrDefault(Pair.create(direction1,direction2), 0d);
    }
}
