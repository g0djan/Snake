package ru.leoltron.snake.game;

import lombok.NonNull;
import lombok.val;
import ru.leoltron.snake.game.entity.GameEntity;
import ru.leoltron.snake.game.generators.AppleGenerator;
import ru.leoltron.snake.game.generators.FieldGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private final Random rand = new Random();

    private final int fieldWidth;
    private final int fieldHeight;
    private final AppleGenerator appleGenerator;
    private final FieldGenerator fieldGenerator;

    private GameEntity[][] field;

    public Game(@NonNull AppleGenerator appleGenerator,
                @NonNull FieldGenerator fieldGenerator,
                int fieldWidth, int fieldHeight) {
        this.appleGenerator = appleGenerator;
        this.fieldGenerator = fieldGenerator;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        startNewGame();
    }

    private void startNewGame() {
        initField(fieldGenerator);
        appleGenerator.init();
    }

    private void initField(FieldGenerator generator) {
        field = new GameEntity[fieldWidth][fieldHeight];
        val entities = generator.generateField(fieldWidth, fieldHeight);
        for (val entity : entities)
            setEntityAtItsCoordinates(entity);
    }

    public void setEntityAtItsCoordinates(GameEntity entity) {
        setEntityAt(entity.getX(), entity.getY(), entity);
    }

    public GameEntity getEntityAt(int x, int y) {
        return field[x][y];
    }

    private void clearEntityAt(int x, int y) {
        field[x][y] = null;
    }


    private void setEntityAt(int x, int y, GameEntity entity) {
        if (entity == null)
            clearEntityAt(x, y);
        else if (field[x][y] != null) {
            field[x][y].onCollisionWith(entity);
        }
    }

    private List<GameEntity> getGameEntitiesAndRemoveDead() {
        val entities = new ArrayList<GameEntity>();
        for (int x = 0; x < fieldWidth; x++)
            for (int y = 0; y < fieldHeight; y++) {
                val entity = getEntityAt(x, y);
                if (entity != null) {
                    if (!entity.isDead()) entities.add(entity);
                    else clearEntity(entity);
                }
            }
        return entities;
    }

    private void clearEntity(GameEntity entity) {
        clearEntityAt(entity.getX(), entity.getY());
    }

    public void tick() {
        for (val entity : getGameEntitiesAndRemoveDead()) {
            val prevX = entity.getX();
            val prevY = entity.getY();
            entity.tick();
            if (entity.getX() != prevX || entity.getY() != prevY)
                setEntityAtItsCoordinates(entity);
        }
        appleGenerator.tick();
    }

    public final Point getRandomCoordinates() {
        return new Point(rand.nextInt(fieldWidth), rand.nextInt(fieldHeight));
    }

    public final Point getRandomFreeCoordinates() {
        val freeCoords = getAllFreeCoordinates();
        return freeCoords.get(rand.nextInt(freeCoords.size()));
    }

    private List<Point> getAllFreeCoordinates() {
        val coords = new ArrayList<Point>();
        for (int x = 0; x < fieldWidth; x++)
            for (int y = 0; y < fieldHeight; y++)
                if (isWall(x, y))
                    coords.add(new Point(x, y));
        return coords;
    }

    public boolean isWall(int x, int y) {
        return field[x][y] == null;
    }
}

