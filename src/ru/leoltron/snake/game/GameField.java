package ru.leoltron.snake.game;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import ru.leoltron.snake.game.entity.FieldObject;
import ru.leoltron.snake.util.GamePoint;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameField {

    private final Random rand = new Random();

    @Getter
    private int fieldWidth;
    @Getter
    private int fieldHeight;
    private Map<GamePoint, FieldObject> fieldObjects = new HashMap<>();
    private Map<Class, HashSet<GamePoint>> locationOfFieldObjects = new HashMap<>();

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

    FieldObject getObjectAt(GamePoint point) {
        return fieldObjects.getOrDefault(point, null);
    }

    public FieldObject getObjectAt(int x, int y) {
        return getObjectAt(new GamePoint(x, y));
    }

    public boolean isFree(int x, int y) {
        return getObjectAt(x, y) == null;
    }

    public boolean isFree(GamePoint point) {
        return getObjectAt(point) == null;
    }

    public final GamePoint getRandomFreeLocation() {
        val freeLocations = getAllFreeLocations();
        return freeLocations.get(rand.nextInt(freeLocations.size()));
    }

    private List<GamePoint> getAllFreeLocations() {
        val locations = new ArrayList<GamePoint>();
        for (int x = 0; x < fieldWidth; x++)
            for (int y = 0; y < fieldHeight; y++)
                if (isFree(x, y))
                    locations.add(new GamePoint(x, y));
        return locations;
    }

    public void addEntity(int x, int y, FieldObject object) {
        addEntity(new GamePoint(x, y), object);
    }

    public void addEntity(GamePoint coords, FieldObject object) {
        if (new Rectangle(0, 0, fieldWidth, fieldHeight).contains(coords)) {
            fieldObjects.merge(coords, object, GameField::resolveCollision);
            AddToLocationOfFieldObjects(coords, object);
        }
        else
            throw new IndexOutOfBoundsException(String.format("Coords (%d, %d) are out of bounds of the field " +
                    "(width: %d, height:%d)", coords.x, coords.y, fieldWidth, fieldHeight));
    }

    private void AddToLocationOfFieldObjects(GamePoint coords, FieldObject object) {
        if (!locationOfFieldObjects.containsKey(object.getClass())) {
            locationOfFieldObjects.put(object.getClass(), new HashSet<>());
        }
        locationOfFieldObjects.get(object.getClass()).add(coords);
    }

    @SuppressWarnings("UnusedReturnValue")
    public FieldObject removeEntityAt(GamePoint point) {
        locationOfFieldObjects.get(fieldObjects.get(point).getClass()).remove(point);
        return fieldObjects.remove(point);
    }

    @SuppressWarnings("WeakerAccess")
    public Collection<Map.Entry<GamePoint, FieldObject>> getFieldObjects() {
        return fieldObjects.entrySet();
    }

    public void clear() {
        fieldObjects.clear();
    }

    public HashSet<GamePoint> getLocationOfFieldObject(Class clazz) {
        if (!locationOfFieldObjects.containsKey(clazz)) {
            return new HashSet<>();
        }
        return locationOfFieldObjects.get(clazz);
    }

    public List<Direction> getAvailableDirection(GamePoint point) {
        List<Direction> directions = new ArrayList<>();
        for (val dir : Direction.values()) {
            val newPoint = point.add((new GamePoint(dir.dx, dir.dy)));
            if (!fieldObjects.containsKey(newPoint)) {
                directions.add(dir);
            }
        }
        return directions;
    }
}
