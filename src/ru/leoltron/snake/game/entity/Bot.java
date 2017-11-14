package ru.leoltron.snake.game.entity;

import lombok.val;
import ru.leoltron.snake.game.Direction;
import ru.leoltron.snake.game.Event;

import java.util.Random;


public class Bot extends FieldObject implements IReactable{

    private Direction direction;
    private int timeToBeAggressive;

    public Bot(Direction direction){
        this.direction = direction;
        this.timeToBeAggressive = 0;
    }

    @Override
    public void reactToEvent(Event event){
        if (event == Event.EatenApple)
            timeToBeAggressive = 7;
    }

    @Override
    public void onCollisionWith(FieldObject object) {
        if (object instanceof SnakePart && ((SnakePart) object).isHead())
            setDead();
    }

    @Override
    public void tick(){
        direction = timeToBeAggressive > 0 ? HuntStrategy() : RandomStrategy();
        timeToBeAggressive = Math.max(0, timeToBeAggressive - 1);
    }



    private Direction RandomStrategy() {
        /*Random random = new Random();
        val botLocation =
        Direction current = Direction.RIGHT;
        do {

        } while ()*/
        return null;
    }

    private Direction HuntStrategy() {
        return null;
    }
}
