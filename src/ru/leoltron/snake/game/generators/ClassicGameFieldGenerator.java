package ru.leoltron.snake.game.generators;

import lombok.val;
import ru.leoltron.snake.game.GameField;
import ru.leoltron.snake.game.entity.Wall;
import ru.leoltron.snake.util.GamePoint;

public class ClassicGameFieldGenerator implements GameFieldGenerator {
    @Override
    public void generateFieldObjects(GameField field) {
        val top = 0;
        val bottom = field.getFieldHeight() - 1;
        val left = 0;
        val right = field.getFieldWidth() - 1;

        for (int y = top; y < field.getFieldHeight(); y++) {
            addWallAt(field, left, y);
            addWallAt(field, right, y);
        }

        for (int x = left + 1; x < right; x++) {
            addWallAt(field, x, top);
            addWallAt(field, x, bottom);
        }
    }

    private static void addWallAt(GameField field, int x, int y) {

        GamePoint location = new GamePoint(x, y);
        field.addEntity(location, new Wall(location));
    }
}
