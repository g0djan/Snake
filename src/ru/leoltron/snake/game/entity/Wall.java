package ru.leoltron.snake.game.entity;

import ru.leoltron.snake.game.Event;

public class Wall extends FieldObject {
    public Wall() {
    }

    @Override
    public void onCollisionWith(FieldObject object) {
        object.setDead();
    }

    @Override
    public void reactToEvent(Event event) {

    }
}
