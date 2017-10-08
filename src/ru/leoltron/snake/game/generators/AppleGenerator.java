package ru.leoltron.snake.game.generators;

import ru.leoltron.snake.game.Game;

public abstract class AppleGenerator {
    protected final Game game;

    public AppleGenerator(Game game) {
        this.game = game;
    }

    public abstract void tick();

    public void init(){
        tick();
    }
}
