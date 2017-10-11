package ru.leoltron.snake.game;

import lombok.NonNull;
import lombok.val;
import ru.leoltron.snake.game.entity.FieldObject;
import ru.leoltron.snake.game.entity.FieldObjectMoving;
import ru.leoltron.snake.game.generators.AppleGenerator;
import ru.leoltron.snake.game.generators.GameFieldGenerator;
import ru.leoltron.snake.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Game {

    private final GameFieldGenerator gameFieldGenerator;

    private final AppleGenerator appleGenerator;
    private final ClassicSnakeController classicSnakeController;
    private GameField gameField;


    public Game(@NonNull AppleGenerator appleGenerator,
                @NonNull GameFieldGenerator gameFieldGenerator,
                @NonNull ClassicSnakeController classicSnakeController,
                int fieldWidth, int fieldHeight) {
        this.appleGenerator = appleGenerator;
        this.gameFieldGenerator = gameFieldGenerator;
        this.classicSnakeController = classicSnakeController;
        this.gameField = new GameField(fieldWidth, fieldHeight);
        startNewGame();
    }


    private void startNewGame() {
        gameFieldGenerator.generateFieldObjects(gameField);
        appleGenerator.onStartNewGame(gameField);
        classicSnakeController.respawnSnake(gameField);
    }

    public void tick() {
        val movedObjects = new ArrayList<Pair<Point, FieldObject>>();

        Iterator<Map.Entry<Point, FieldObject>> iterator = gameField.getFieldObjects().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Point, FieldObject> entry = iterator.next();
            val fieldObject = entry.getValue();
            if (fieldObject instanceof FieldObjectMoving) {
                int x = entry.getKey().x;
                int y = entry.getKey().y;

                val movingObject = ((FieldObjectMoving) fieldObject);
                x += movingObject.getVelX();
                y += movingObject.getVelY();

                movedObjects.add(Pair.create(new Point(x, y), fieldObject));
                iterator.remove();
            }
            fieldObject.tick();
        }
        for (val entry : movedObjects)
            gameField.addEntity(entry.getItem1(), entry.getItem2());
        appleGenerator.tick(gameField);
        classicSnakeController.tick(gameField);
    }

    public void setCurrentDirection(Direction direction) {
        classicSnakeController.setCurrentDirection(direction);
    }

    public boolean isGameOver() {
        return classicSnakeController.isSnakeDead(gameField);
    }
}

