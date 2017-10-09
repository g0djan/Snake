package ru.leoltron.snake.game.generators;

import ru.leoltron.snake.game.Game;
import ru.leoltron.snake.game.entity.Apple;

public class ClassicAppleGenerator implements AppleGenerator {
    private final Game game;
    private Apple apple;

    public ClassicAppleGenerator(Game game) {
        this.game = game;
    }

    @Override
    public void tick() {
        if (apple == null || apple.isDead())
            apple = Apple.generateAtRandomFreeCoordinates(game);
    }
}
