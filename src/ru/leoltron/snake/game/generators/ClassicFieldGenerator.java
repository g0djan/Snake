package ru.leoltron.snake.game.generators;

import lombok.val;
import ru.leoltron.snake.game.entity.FieldObject;
import ru.leoltron.snake.game.entity.Wall;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClassicFieldGenerator implements FieldGenerator {
    @Override
    public Map<Point, FieldObject> generateField(int fieldWidth, int fieldHeight) {
        val top = 0;
        val bottom = fieldHeight - 1;
        val left = 0;
        val right = fieldWidth - 1;

        val entities = new HashMap<Point, FieldObject>();
        for (int y = top; y < fieldHeight; y++) {
            entities.put(new Point(left, y), new Wall());
            entities.put(new Point(right, y),new Wall());
        }

        for (int x = left + 1; x < right; x++) {
            entities.put(new Point(x, top),new Wall());
            entities.put(new Point(x, bottom),new Wall());
        }

        return entities;
    }
}
