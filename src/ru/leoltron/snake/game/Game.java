package ru.leoltron.snake.game;

import lombok.NonNull;
import lombok.val;
import ru.leoltron.snake.game.entity.FieldObject;
import ru.leoltron.snake.game.entity.FieldObjectMoving;
import ru.leoltron.snake.game.generators.AppleGenerator;
import ru.leoltron.snake.game.generators.FieldGenerator;
import ru.leoltron.snake.game.generators.SnakeSpawner;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Game {

    private final Random rand = new Random();

    private final int fieldWidth;
    private final int fieldHeight;
    private final AppleGenerator appleGenerator;
    private final FieldGenerator fieldGenerator;
    private final SnakeSpawner snakeSpawner;

    private FieldObject[][] fieldState;
    private Map<Point, FieldObject> fieldObjects = new HashMap<>();

    public Game(@NonNull AppleGenerator appleGenerator,
                @NonNull FieldGenerator fieldGenerator,
                @NonNull SnakeSpawner snakeSpawner,
                int fieldWidth, int fieldHeight) {
        this.appleGenerator = appleGenerator;
        this.fieldGenerator = fieldGenerator;
        this.snakeSpawner = snakeSpawner;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        startNewGame();
    }

    private void startNewGame() {
        initField(fieldGenerator);
        appleGenerator.init();
        snakeSpawner.spawnSnake(this);
    }

    private void initField(FieldGenerator generator) {
        fieldObjects = generator.generateField(fieldWidth, fieldHeight);
    }


    private void updateFieldState() {
        fieldState = new FieldObject[fieldWidth][fieldHeight];
        for (val e : fieldObjects.entrySet())
    }


    public FieldObject getEntityAt(int x, int y) {
        return fieldState[x][y];
    }

    public boolean isFree(int x, int y) {
        return getEntityAt(x, y) == null;
    }


    public void tick() {
        for (val entry : fieldObjects.entrySet()) {
            val fieldObject = entry.getValue();
            if(fieldObject instanceof FieldObjectMoving) {
                int x = entry.getKey().x;
                int y = entry.getKey().y;

                val movingObject = ((FieldObjectMoving) fieldObject);
                x += movingObject.getVelX();
                y += movingObject.getVelY();

                fieldObjects.remove()
            }
            fieldObject.tick();
        }
        //snake.tick();
        appleGenerator.tick();
    }

    public final Point getRandomFreeCoordinates() {
        val freeCoords = getAllFreeCoordinates();
        return freeCoords.get(rand.nextInt(freeCoords.size()));
    }

    private List<Point> getAllFreeCoordinates() {
        val coords = new ArrayList<Point>();
        for (int x = 0; x < fieldWidth; x++)
            for (int y = 0; y < fieldHeight; y++)
                if (isFree(x, y))
                    coords.add(new Point(x, y));
        return coords;
    }


    public void addEntities(Map<Point, FieldObject> objects) {
        this.fieldObjects.putAll(objects);
    }

    public void addEntity(int x, int y, FieldObject object) {
        addEntity(new Point(x, y), object);
    }

    public void addEntity(Point coords, FieldObject object) {
        fieldObjects.put(coords, object);
    }
}

