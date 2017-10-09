package ru.leoltron.snake.game.entity;

import lombok.Getter;

public abstract class FieldObjectMoving extends FieldObject {

    @Getter
    protected int velX;
    @Getter
    protected int velY;

}
