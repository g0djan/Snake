package ru.leoltron.snake.game;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import ru.leoltron.snake.game.entity.FieldObject;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameField {

    private final Random rand = new Random();

    @Getter
    private int fieldWidth;
    @Getter
    private int fieldHeight;
    private Map<Point, FieldObject> fieldObjects = new HashMap<>();

    public GameField(int fieldWidth, int fieldHeight) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
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

    public FieldObject getEntityAt(Point point) {
        return fieldObjects.getOrDefault(point, null);
    }

    public FieldObject getEntityAt(int x, int y) {
        return getEntityAt(new Point(x, y));
    }

    public boolean isFree(Point point) {
        return getEntityAt(point) == null;
    }

    public boolean isFree(int x, int y) {
        return getEntityAt(x, y) == null;
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
        for (val entry : objects.entrySet())
            addEntity(entry);
    }

    public void addEntity(Map.Entry<Point, FieldObject> entry) {
        addEntity(entry.getKey(), entry.getValue());
    }

    public void addEntity(int x, int y, FieldObject object) {
        addEntity(new Point(x, y), object);
    }

    public void addEntity(Point coords, FieldObject object) {
        fieldObjects.merge(coords, object, GameField::resolveCollision);
    }

    public FieldObject removeEntityAt(Point point) {
        return fieldObjects.remove(point);
    }

    public Collection<Map.Entry<Point, FieldObject>> getFieldObjects() {
        return fieldObjects.entrySet();
    }
}
