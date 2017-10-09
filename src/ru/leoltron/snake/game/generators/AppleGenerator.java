package ru.leoltron.snake.game.generators;

public interface AppleGenerator {
    void tick();

    default void init(){
        tick();
    }
}
