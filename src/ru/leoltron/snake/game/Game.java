package ru.leoltron.snake.game;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;
import ru.leoltron.snake.game.entity.FieldObject;
import ru.leoltron.snake.game.entity.FieldObjectMoving;
import ru.leoltron.snake.game.generators.AppleGenerator;
import ru.leoltron.snake.game.generators.FieldGenerator;
import ru.leoltron.snake.game.generators.SnakeSpawner;
import ru.leoltron.snake.util.Pair;

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

    @SneakyThrows
    private static FieldObject resolveCollision(FieldObject object1, FieldObject object2) {
        object1.onCollisionWith(object2);
        object2.onCollisionWith(object1);
        if (!object1.isDead() && !object2.isDead())
            throw new CollisionException(object1, object2);
        if (object1.isDead() && object2.isDead())
            return null;
        return object1.isDead() ? object2 : object1;
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
        for (val e : fieldObjects.entrySet()) {
            val x = e.getKey().x;
            val y = e.getKey().y;
            fieldState[x][y] = e.getValue();
        }
    }


    public FieldObject getEntityAt(int x, int y) {
        return fieldState[x][y];
    }

    public boolean isFree(int x, int y) {
        return getEntityAt(x, y) == null;
    }

    public void tick() {
        val movedObjects = new ArrayList<Pair<Point, FieldObject>>();

        Iterator<Map.Entry<Point, FieldObject>> iterator = fieldObjects.entrySet().iterator();
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
            fieldObjects.merge(entry.getItem1(), entry.getItem2(), Game::resolveCollision);
        appleGenerator.tick();

        updateFieldState();
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

