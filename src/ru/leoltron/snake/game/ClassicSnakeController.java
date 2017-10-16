package ru.leoltron.snake.game;

import lombok.val;
import ru.leoltron.snake.game.entity.SnakePart;
import ru.leoltron.snake.util.GamePoint;

import java.util.LinkedList;

public class ClassicSnakeController {
    private static final int DEFAULT_SNAKE_LENGTH = 4;
    @SuppressWarnings("WeakerAccess")
    protected LinkedList<GamePoint> body;
    private int snakePartsGoingToAdd;
    private Direction currentDirection = Direction.UP;

    public ClassicSnakeController() {
    }

    private void respawnSnake(GameField field, GamePoint startGamePoint, int initialLength) {
        if (initialLength < 1)
            throw new IllegalArgumentException("SnakePart length must be positive!");
        clearFieldFromSnake(field);

        body = new LinkedList<>();
        body.addFirst(startGamePoint);
        field.addEntity(startGamePoint, new SnakePart(this));
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
            ((SnakePart) field.getEntityAt(getTailLocation())).setPrevDirection(null);
    }

    private GamePoint getTailLocation() {
        return body.getLast();
    }

    private int getSnakeSize() {
        return body.size();
    }

    public void onAppleEaten() {
        snakePartsGoingToAdd++;
    }

    void tick(GameField field) {
        if(isSnakeDead(field)) return;

        val headLoc = getHeadLocation();
        ((SnakePart) field.getEntityAt(headLoc)).setNextDirection(currentDirection);

        addNewHead(field);

        if (snakePartsGoingToAdd > 0)
            snakePartsGoingToAdd--;
        else
            shortenTail(field);
    }

    private void addNewHead(GameField field) {
        val newHead = new SnakePart(this);
        newHead.setPrevDirection(currentDirection.reversed());

        val location = getHeadLocation().translated(currentDirection);

        body.addFirst(location);
        field.addEntity(location, newHead);
    }

    void respawnSnake(GameField gameField) {
        respawnSnake(gameField, gameField.getRandomFreeLocation(), DEFAULT_SNAKE_LENGTH);
    }

    void setCurrentDirection(Direction direction) {
        if (getSnakeSize() > 1 && body.get(1).equals(getHeadLocation().translated(direction)))
            return;
        currentDirection = direction;
    }

    private GamePoint getHeadLocation() {
        return body.getFirst();
    }

    boolean isSnakeDead(GameField field) {
        return body == null || body.isEmpty() || field.getEntityAt(getHeadLocation()).isDead();
    }
}
