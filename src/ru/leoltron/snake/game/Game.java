package ru.leoltron.snake.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import ru.leoltron.snake.game.entity.Bot;
import ru.leoltron.snake.game.entity.EventDispatcher;
import ru.leoltron.snake.game.entity.FieldObject;
import ru.leoltron.snake.game.entity.FieldObjectMoving;
import ru.leoltron.snake.game.generators.AppleGenerator;
import ru.leoltron.snake.game.generators.GameFieldGenerator;
import ru.leoltron.snake.util.GamePoint;
import ru.leoltron.snake.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Game {

    @Getter
    private int time;

    private boolean isPaused;

    private final GameFieldGenerator gameFieldGenerator;

    private final AppleGenerator appleGenerator;
    private final ClassicSnakeController classicSnakeController;
    private GameField gameField;
    public final EventDispatcher eventDispatcher;

    public Game(@NonNull AppleGenerator appleGenerator,
                @NonNull GameFieldGenerator gameFieldGenerator,
                @NonNull ClassicSnakeController classicSnakeController,
                int fieldWidth, int fieldHeight) {
        this.appleGenerator = appleGenerator;
        this.gameFieldGenerator = gameFieldGenerator;
        this.classicSnakeController = classicSnakeController;
        gameField = new GameField(fieldWidth, fieldHeight);
        eventDispatcher = new EventDispatcher();
    }


    public void startNewGame() {
        gameField.clear();
        gameFieldGenerator.generateFieldObjects(gameField);
        appleGenerator.onStartNewGame(gameField);
        classicSnakeController.respawnSnake(gameField);
        time = 0;
        isPaused = false;
    }

    public void tick() {
        if (isGameOver()) return;

        if (!isPaused) {
            val movedObjects = new ArrayList<Pair<GamePoint, FieldObject>>();

            Iterator<Map.Entry<GamePoint, FieldObject>> iterator = gameField.getFieldObjects().iterator();
            while (iterator.hasNext()) {
                Map.Entry<GamePoint, FieldObject> entry = iterator.next();
                val fieldObject = entry.getValue();
                if (fieldObject instanceof Bot)
                    ((Bot)fieldObject).renewSnakeLocation(classicSnakeController.GetSnakePartsCoordinates());
               /* if (fieldObject instanceof FieldObjectMoving) {
                    int x = entry.getKey().x;
                    int y = entry.getKey().y;

                    val movingObject = ((FieldObjectMoving) fieldObject);
                    movingObject.renewAvailableDirections(gameField.getAvailableDirection(new GamePoint(x, y)));
                    x += movingObject.getVelX();
                    y += movingObject.getVelY();

                    movedObjects.add(Pair.create(new GamePoint(x, y), fieldObject));
                    iterator.remove();
                }*/
                fieldObject.tick();
            }
            for (val entry : movedObjects)
                gameField.addEntity(entry.getItem1(), entry.getItem2());
            appleGenerator.tick(gameField);
            classicSnakeController.tick(gameField);
        }
        time++;
    }

    public void setCurrentDirection(Direction direction) {
        classicSnakeController.setCurrentDirection(direction);
    }

    public boolean isGameOver() {
        return classicSnakeController.isSnakeDead(gameField);
    }

    public FieldObject getObjectAt(int x, int y) {
        return gameField.getObjectAt(x, y);
    }

    public void switchPaused() {
        isPaused = !isPaused;
    }
}

