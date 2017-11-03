package ru.leoltron.snake.game.generators;

import lombok.val;
import ru.leoltron.snake.game.GameField;
import ru.leoltron.snake.game.entity.Apple;

public class ClassicAppleGenerator implements AppleGenerator {
    private Apple apple;

    @Override
    public void onStartNewGame(GameField field) {
        apple = null;
        tick(field);
    }

    @Override
    public void tick(GameField field) {
        if (apple == null || apple.isDead()) {
            val location = field.getRandomFreeLocation();
            field.addEntity(location, apple = new Apple(location));
        }
    }
}
