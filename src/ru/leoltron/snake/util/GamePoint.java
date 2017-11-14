package ru.leoltron.snake.util;

import ru.leoltron.snake.game.Direction;
import ru.leoltron.snake.game.Game;

import java.awt.*;

public class GamePoint extends Point {

    public GamePoint(Point p) {
        super(p);
    }

    public GamePoint(int x, int y) {
        super(x, y);
    }

    public GamePoint reversed() {
        return new GamePoint(-x, -y);
    }

    public GamePoint normalized() {
        return new GamePoint(sign(x), sign(y));
    }

    private static int sign(int a) {
        return Integer.compare(a, 0);
    }

    public GamePoint translated(Direction direction) {
        return new GamePoint(x + direction.dx, y + direction.dy);
    }

    public GamePoint add(GamePoint p) {
        return new GamePoint(x + p.x, y + p.y);
    }

    public GamePoint subtract(Point point) {
        return new GamePoint(x - point.x, y - point.y);
    }
}
