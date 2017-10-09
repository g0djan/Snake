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

/*Возможно, придется "снимать" состояние поля перед тиком, создавая GameEntity[][], а сами сущности хранить в массиве,
* после тика каждой убрать из списка, если мертво, после всех тиков повторно сделать снимок поля для
* обнаружения коллизий, отображения и следующего тика*/

public class Game {

    private final Random rand = new Random();

    private final int fieldWidth;
    private final int fieldHeight;
    private final AppleGenerator appleGenerator;
    private final FieldGenerator fieldGenerator;

    private GameEntity[][] fieldState;

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
        fieldState = new GameEntity[fieldWidth][fieldHeight];
        val entities = generator.generateField(fieldWidth, fieldHeight);
    }


    public GameEntity getEntityAt(int x, int y) {
        return fieldState[x][y];
    }

    public boolean isWall(int x, int y) {
        return getEntityAt(x,y) == null;
    }

    /*
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
*/

}

