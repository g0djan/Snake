package ru.leoltron.snake.game;

import lombok.val;
import ru.leoltron.snake.game.entity.SnakePart;
import ru.leoltron.snake.util.GamePoint;

import java.util.LinkedList;

public class ClassicSnakeController {
    private static final int DEFAULT_SNAKE_LENGTH = 4;
    private final int snakeLength;
    @SuppressWarnings("WeakerAccess")
    protected LinkedList<GamePoint> body;
    private int snakePartsGoingToAdd;
    private Direction currentDirection = Direction.UP;

    public ClassicSnakeController() {
        this(DEFAULT_SNAKE_LENGTH);
    }

    public ClassicSnakeController(int snakeLength) {
        if (snakeLength < 1)
            throw new IllegalArgumentException("Snake length must be positive!");
        this.snakeLength = snakeLength;
    }

    private void respawnSnake(GameField field, GamePoint startGamePoint, int initialLength) {
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

    private void shortenTail(GameField field) {
        field.removeEntityAt(body.removeLast());
        if (!body.isEmpty())
            ((SnakePart) field.getEntityAt(getTailLocation())).setPrevPartDirection(null);
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
        if (isSnakeDead(field)) return;

        if (snakePartsGoingToAdd > 0)
            snakePartsGoingToAdd--;
        else
            shortenTail(field);

        val headLoc = getHeadLocation();
        ((SnakePart) field.getEntityAt(headLoc)).setNextPartDirection(currentDirection);

        addNewHead(field);
    }

    private void addNewHead(GameField field) {
        val newHead = new SnakePart(this);
        newHead.setPrevPartDirection(currentDirection.reversed());

        val location = getHeadLocation().translated(currentDirection);

        body.addFirst(location);
        field.addEntity(location, newHead);
    }

    void respawnSnake(GameField gameField) {
        respawnSnake(
                gameField,
                new GamePoint(3, gameField.getFieldWidth() - 3),
                snakeLength);
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
        return body == null ||
                body.isEmpty() ||
                !(field.getEntityAt(getHeadLocation()) instanceof SnakePart) ||
                field.getEntityAt(getHeadLocation()).isDead();
    }
}
