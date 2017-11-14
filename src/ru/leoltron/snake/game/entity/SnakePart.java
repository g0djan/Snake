package ru.leoltron.snake.game.entity;

import lombok.Getter;
import lombok.Setter;
import ru.leoltron.snake.game.ClassicSnakeController;
import ru.leoltron.snake.game.Direction;
import ru.leoltron.snake.game.Event;

public class SnakePart extends FieldObject {

    private ClassicSnakeController snakeController;

    @Getter
    @Setter
    private Direction prevPartDirection;
    @Getter
    @Setter
    private Direction nextPartDirection;

    public SnakePart(ClassicSnakeController snakeController) {
        this.snakeController = snakeController;
    }

    public boolean isHead(){
        return nextPartDirection == null;
    }

    public boolean isTail(){
        return prevPartDirection == null;
    }

    @Override
    public void onCollisionWith(FieldObject object) {
        if (object instanceof Apple)
            snakeController.onAppleEaten();
        else setDead();
    }

    @Override
    public void reactToEvent(Event event) {
    }
}
