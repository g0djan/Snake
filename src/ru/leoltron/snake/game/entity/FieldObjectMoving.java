package ru.leoltron.snake.game.entity;

import lombok.Getter;
import ru.leoltron.snake.game.Direction;

import java.util.List;

public abstract class FieldObjectMoving extends FieldObject {

    @Getter
    protected int velX;
    @Getter
    protected int velY;

    protected List<Direction> availableDirections;

    public void renewAvailableDirections(List<Direction> directions){
        availableDirections = directions;
    }
}
