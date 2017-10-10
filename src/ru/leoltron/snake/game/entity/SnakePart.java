package ru.leoltron.snake.game.entity;

import lombok.Getter;
import lombok.Setter;
import ru.leoltron.snake.game.ClassicSnakeController;

public class SnakePart extends FieldObject {

    private ClassicSnakeController snakeController;

    @Getter
    @Setter
    private boolean isHead = false;
    @Getter
    @Setter
    private boolean isTail = false;

    public SnakePart(ClassicSnakeController snakeController) {
        this.snakeController = snakeController;
    }

    public SnakePart setHead() {
        isHead = true;
        return this;
    }

    public SnakePart setTail() {
        isTail = true;
        return this;
    }

    @Override
    public void onCollisionWith(FieldObject object) {
        if (object instanceof Apple)
            snakeController.onAppleEaten();
        else if (isHead())
            setDead();
    }
}
