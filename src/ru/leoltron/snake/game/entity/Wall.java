package ru.leoltron.snake.game.entity;

public class Wall extends GameEntity {

    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public void onCollisionWith(GameEntity entity) {
        entity.setDead();
    }
}
