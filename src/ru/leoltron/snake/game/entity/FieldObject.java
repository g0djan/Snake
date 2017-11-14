package ru.leoltron.snake.game.entity;

import lombok.Getter;
import ru.leoltron.snake.game.Event;

public abstract class FieldObject {

    @Getter
    private boolean isDead;

    @Getter
    private Event occurredEvent = null;

    public abstract void onCollisionWith(FieldObject object);

    @SuppressWarnings("WeakerAccess")
    public void setDead() {
        isDead = true;
    }

    public void processEvent(Event event) {}

    public final void reactToEvent(Event event) {
        occurredEvent = null;
        processEvent(event);
    }

    public final void EventHappend(Event event) {
        occurredEvent = event;
    }

    public void tick() {
    }
}
