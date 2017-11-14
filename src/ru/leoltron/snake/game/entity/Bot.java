package ru.leoltron.snake.game.entity;

import lombok.val;
import ru.leoltron.snake.game.Direction;
import ru.leoltron.snake.game.Event;
import ru.leoltron.snake.game.Game;
import ru.leoltron.snake.util.GamePoint;


public class Bot extends FieldObjectMoving{

    private Direction direction;
    private int timeToBeAggressive;
    private GamePoint location;

    public Bot(Direction direction, GamePoint location){
        this.direction = direction;
        this.timeToBeAggressive = 0;
        this.location = location;
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

    private int manhattanDistance(GamePoint a, GamePoint b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private Direction RandomStrategy() {
        Direction currentDirection = Direction.getRandomDirection();
        GamePoint newLocatiion;
        do {
            newLocatiion = new GamePoint(location.x + currentDirection.dx,
                    location.y + currentDirection.dy);
        } while (false);
        return null;
    }

    private Direction HuntStrategy() {
        return null;
    }
}
