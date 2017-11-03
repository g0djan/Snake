package ru.leoltron.snake.game.entity;

import lombok.Getter;
import ru.leoltron.snake.game.Event;

public abstract class FieldObject {

    @Getter
    private boolean isDead;

    public abstract void onCollisionWith(FieldObject object);

    @SuppressWarnings("WeakerAccess")
    public void setDead() {
        isDead = true;
    }

    public abstract void reactToEvent(Event event);

    public void tick() {
    }
}
