package ru.leoltron.snake.game.entity;

import lombok.Getter;
import ru.leoltron.snake.util.GamePoint;

public abstract class FieldObjectMoving extends FieldObject {

    @Getter
    protected int velX;
    @Getter
    protected int velY;

    public FieldObjectMoving(GamePoint point) {
        super(point);
    }
}
