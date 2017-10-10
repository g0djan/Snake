package ru.leoltron.snake.game;

import lombok.val;
import ru.leoltron.snake.game.entity.SnakePart;

import java.awt.*;
import java.util.LinkedList;

public class ClassicSnakeController {
    private static final int DEFAULT_SNAKE_LENGTH = 4;
    protected LinkedList<Point> body;
    private int snakePartsGoingToAdd;
    private Direction currentDirection = Direction.UP;

    public ClassicSnakeController() {
    }

    private void respawnSnake(GameField field, Point startPoint, int initialLength) {
        if (initialLength < 1)
            throw new IllegalArgumentException("SnakePart length must be positive!");
        clearFieldFromSnake(field);

        body = new LinkedList<>();
        addNewHead(field, startPoint, createSnakePart().setHead().setTail());
        snakePartsGoingToAdd = initialLength - 1;

    }

    private void clearFieldFromSnake(GameField field) {
        if (body != null)
            for (val point : body)
                field.removeEntityAt(point);
    }

    private SnakePart createSnakePart() {
        return new SnakePart(this);
    }

    private void shortenTail(GameField field) {
        field.removeEntityAt(body.removeLast());
        if (!body.isEmpty())
            ((SnakePart) field.getEntityAt(getTailLocation())).setTail();
    }

    private Point getTailLocation() {
        return body.getLast();
    }

    public int getSnakeSize() {
        return body.size();
    }

    public void onAppleEaten() {
        snakePartsGoingToAdd++;
    }

    public void tick(GameField field) {
        val headLoc = getHeadLocation();
        ((SnakePart) field.getEntityAt(headLoc)).setHead(false);

        addNewHead(field, currentDirection.translatePoint(headLoc), createSnakePart().setHead());

        if (snakePartsGoingToAdd > 0)
            snakePartsGoingToAdd--;
        else
            shortenTail(field);
    }

    public void addNewHead(GameField field, Point location, SnakePart part) {
        body.addFirst(location);
        field.addEntity(location, part);
    }

    public void respawnSnake(GameField gameField) {
        respawnSnake(gameField, gameField.getRandomFreeCoordinates(), DEFAULT_SNAKE_LENGTH);
    }

    public void setCurrentDirection(Direction direction) {
        if (getSnakeSize() > 1 && body.get(1).equals(direction.translatePoint(getHeadLocation())))
            return;
        currentDirection = direction;
    }

    public Point getHeadLocation() {
        return body.getFirst();
    }
}
