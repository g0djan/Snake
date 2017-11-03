package ru.leoltron.snake.game.entity;

import lombok.Getter;
import lombok.Setter;
import ru.leoltron.snake.game.Event;
import ru.leoltron.snake.util.GamePoint;

import java.awt.*;

public abstract class FieldObject {

    @Getter
    private boolean isDead;

    @Getter @Setter
    private GamePoint location;

    public FieldObject(GamePoint point){
        location = point;
    }

    public abstract void onCollisionWith(FieldObject object);

    @SuppressWarnings("WeakerAccess")
    public void setDead() {
        isDead = true;
    }

    public abstract void reactToEvent(Event event);

    public void tick() {
    }
}
