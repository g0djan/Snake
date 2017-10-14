package ru.leoltron.snake.game.entity;

import lombok.Getter;
import lombok.Setter;
import ru.leoltron.snake.game.ClassicSnakeController;
import ru.leoltron.snake.game.Direction;

public class SnakePart extends FieldObject {

    private ClassicSnakeController snakeController;

    @Getter
    @Setter
    private Direction prevDirection;
    @Getter
    @Setter
    private Direction nextDirection;

    public SnakePart(ClassicSnakeController snakeController) {
        this.snakeController = snakeController;
    }

    public boolean isHead(){
        return nextDirection == null;
    }

    public boolean isTail(){
        return prevDirection == null;
    }

    @Override
    public void onCollisionWith(FieldObject object) {
        if (object instanceof Apple)
            snakeController.onAppleEaten();
        else
            setDead();
    }
}
