package ru.leoltron.snake.game.entity;

import lombok.Getter;

public abstract class FieldObject {

    @Getter
    private boolean isDead = false;

    public abstract void onCollisionWith(FieldObject object);

    public void setDead() {
        isDead = true;
    }

    public void tick() {
    }
}
