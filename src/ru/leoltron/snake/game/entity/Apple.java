package ru.leoltron.snake.game.entity;

import ru.leoltron.snake.game.Game;

import java.awt.*;

public class Apple extends FieldObject {

    @Override
    public void onCollisionWith(FieldObject object) {
        setDead();
    }

    public static Apple generateAtRandomFreeCoordinates(Game game){
        return new Apple(game, game.getRandomFreeCoordinates());
    }
}
