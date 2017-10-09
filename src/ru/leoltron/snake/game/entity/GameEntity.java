package ru.leoltron.snake.game.entity;

import lombok.Getter;

public abstract class GameEntity {
    @Getter
    protected int x = 0;
    @Getter
    protected int y = 0;

    @Getter
    private boolean isDead = false;

    protected GameEntity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void onCollisionWith(GameEntity entity);

    public void setDead() {
        isDead = true;
    }

    public void tick() {
    }
}
