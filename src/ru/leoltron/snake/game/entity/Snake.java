package ru.leoltron.snake.game.entity;

import lombok.Getter;

import java.awt.*;
import java.util.LinkedList;

public class Snake extends FieldObjectMoving {
    public static final int DEFAULT_SNAKE_LENGTH = 3;
    @Getter
    private LinkedList<Point> body;
    private Point tail;

    public Snake(int x, int y) {
        this(x, y, DEFAULT_SNAKE_LENGTH);
    }

    public Snake(int x, int y, int length) {
        super();
        body = new LinkedList<Point>();
        for (int i = 0; i < 3; i++)
            body.add(new Point(x, y - i));
    }

    public Snake(int x, int y, Snake snake) {
        body = snake.body;
        body.addFirst(new Point(x, y));
        tail = body.getLast();
        body.removeLast();
    }

    @Override
    public void onCollisionWith(FieldObject object) {
        if (object instanceof Apple) {
            body.add(tail);
        } else {
            setDead();
        }
    }

    @Override
    public void tick() {

    }
}
