package ru.leoltron.snake.game.generators;

import lombok.val;
import ru.leoltron.snake.game.Game;

public class ClassicSnakeSpawner implements SnakeSpawner {

    private static final int DEFAULT_SNAKE_LENGTH = 4;

    @Override
    public void spawnSnake(Game game) {
        val coords = game.getRandomFreeCoordinates();
        game.addEntities(SnakePart.createSnake(coords.x, coords.y, DEFAULT_SNAKE_LENGTH));
    }
}
