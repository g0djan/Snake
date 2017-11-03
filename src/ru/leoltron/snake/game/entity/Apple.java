package ru.leoltron.snake.game.entity;

import lombok.Getter;
import lombok.Setter;
import ru.leoltron.snake.game.Event;
import ru.leoltron.snake.util.GamePoint;

public class Apple extends FieldObject {

    public Apple(GamePoint point){super(point);}

    @Override
    public void onCollisionWith(FieldObject object) {
        setDead();
    }

    @Override
    public void reactToEvent(Event event) {

    }

}
