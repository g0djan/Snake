package ru.leoltron.snake.game.generators;

import lombok.val;
import ru.leoltron.snake.game.Game;

public class ClassicSnakeSpawner implements SnakeSpawner {

    @Override
    public void spawnSnake(Game game) {
        val coords = game.getRandomFreeCoordinates();
        //TODO:...
    }
}
