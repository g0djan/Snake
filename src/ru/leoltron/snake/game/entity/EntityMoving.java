package ru.leoltron.snake.game.entity;

public abstract class EntityMoving extends GameEntity {

    protected int velX;
    protected int velY;

    protected EntityMoving(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
    }
}
