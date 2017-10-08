package ru.leoltron.snake.game.generators;

import ru.leoltron.snake.game.entity.GameEntity;

import java.util.List;

public interface FieldGenerator {
    List<GameEntity> generateField(int fieldWidth, int fieldHeight);
}
