package ru.leoltron.snake.game.generators;

import ru.leoltron.snake.game.GameField;
import ru.leoltron.snake.game.entity.EventDispatcher;

public interface FieldObjectGenerator {
    void onStartNewGame(GameField field, EventDispatcher dispatcher);

    void tick(GameField field);
}
