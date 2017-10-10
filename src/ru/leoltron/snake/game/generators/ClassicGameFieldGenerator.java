package ru.leoltron.snake.game.generators;

import lombok.val;
import ru.leoltron.snake.game.GameField;
import ru.leoltron.snake.game.entity.Wall;

import java.awt.*;

public class ClassicGameFieldGenerator implements GameFieldGenerator {
    @Override
    public void generateFieldObjects(GameField field) {
        val top = 0;
        val bottom = field.getFieldHeight() - 1;
        val left = 0;
        val right = field.getFieldWidth() - 1;

        for (int y = top; y < field.getFieldHeight(); y++) {
            field.addEntity(new Point(left, y), new Wall());
            field.addEntity(new Point(right, y), new Wall());
        }

        for (int x = left + 1; x < right; x++) {
            field.addEntity(new Point(x, top), new Wall());
            field.addEntity(new Point(x, bottom), new Wall());
        }
    }
}
