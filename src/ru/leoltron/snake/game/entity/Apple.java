package ru.leoltron.snake.game.entity;

import ru.leoltron.snake.game.Game;

import java.awt.*;

public class Apple extends GameEntity {

    public Apple(int x, int y) {
        super(x, y);
    }

    public Apple(Game game, Point point) {
        this(game, point.x, point.y);
    }

    public Apple(Game game, int x, int y) {
        this(x, y);
        game.setEntityAtItsCoordinates(this);
    }

    @Override
    public void onCollisionWith(GameEntity entity) {
        setDead();
    }

    public static Apple generateAtRandomFreeCoordinates(Game game){
        return new Apple(game, game.getRandomFreeCoordinates());
    }
}
