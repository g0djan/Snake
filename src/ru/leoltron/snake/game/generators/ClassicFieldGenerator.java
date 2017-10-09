package ru.leoltron.snake.game.generators;

import lombok.val;
import ru.leoltron.snake.game.entity.GameEntity;
import ru.leoltron.snake.game.entity.Wall;

import java.util.ArrayList;
import java.util.List;

public class ClassicFieldGenerator implements FieldGenerator {
    @Override
    public List<GameEntity> generateField(int fieldWidth, int fieldHeight) {
        val top = 0;
        val bottom = fieldHeight - 1;
        val left = 0;
        val right = fieldWidth - 1;

        val entities = new ArrayList<GameEntity>();
        for (int y = top; y < fieldHeight; y++) {
            entities.add(new Wall(left, y));
            entities.add(new Wall(right, y));
        }

        for (int x = left + 1; x < right; x++) {
            entities.add(new Wall(x, top));
            entities.add(new Wall(x, bottom));
        }

        return entities;
    }
}
