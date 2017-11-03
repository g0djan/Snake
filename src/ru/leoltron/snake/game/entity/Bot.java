package ru.leoltron.snake.game.entity;

import ru.leoltron.snake.game.Direction;
import ru.leoltron.snake.game.Event;


public class Bot extends FieldObject{

    private Direction direction;
    private int timeToBeAgressive;

    public Bot(Direction direction){
        this.direction = direction;
        this.timeToBeAgressive = 0;
    }

    @Override
    public void reactToEvent(Event event){
        if (event == Event.EatenApple)
            timeToBeAgressive = 7;
    }

    @Override
    public void onCollisionWith(FieldObject object) {
        if (object instanceof SnakePart && ((SnakePart) object).isHead())
            setDead();
    }

    @Override
    public void tick(){
        direction = timeToBeAgressive > 0 ? HuntStrategy() : RandomStartegy();
        timeToBeAgressive = Math.max(0, timeToBeAgressive - 1);
    }

    private Direction RandomStartegy() {
        return null;
    }

    private Direction HuntStrategy() {
        return null;
    }
}
