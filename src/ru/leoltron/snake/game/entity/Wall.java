package ru.leoltron.snake.game.entity;

import ru.leoltron.snake.game.Event;
import ru.leoltron.snake.util.GamePoint;

public class Wall extends FieldObject {

    public Wall(GamePoint point) {
        super(point);
    }

    @Override
    public void onCollisionWith(FieldObject object) {
        object.setDead();
    }

    @Override
    public void reactToEvent(Event event) {

    }
}
