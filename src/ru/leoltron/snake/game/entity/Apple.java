package ru.leoltron.snake.game.entity;

import ru.leoltron.snake.game.Event;

public class Apple extends FieldObject {

    @Override
    public void onCollisionWith(FieldObject object) {
        setDead();
    }

    @Override
    public void reactToEvent(Event event) {

    }

}
