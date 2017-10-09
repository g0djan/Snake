package ru.leoltron.snake.game.generators;

import ru.leoltron.snake.game.entity.FieldObject;

import java.awt.*;
import java.util.Map;

public interface FieldGenerator {
    Map<Point, FieldObject> generateField(int fieldWidth, int fieldHeight);
}
