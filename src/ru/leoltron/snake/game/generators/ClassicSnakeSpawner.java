package ru.leoltron.snake.game.generators;

import lombok.val;
import ru.leoltron.snake.game.Game;
import ru.leoltron.snake.game.entity.SnakePart;

public class ClassicSnakeSpawner implements SnakeSpawner {

    private static final int DEFAULT_SNAKE_LENGTH = 4;

    @Override
    public void spawnSnake(Game game) {
        val coords = game.getRandomFreeCoords();
        game.addEntity(SnakePart.createSnake());
    }
}
