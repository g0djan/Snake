package ru.leoltron.snake.game.entity;

import lombok.val;
import ru.leoltron.snake.game.Direction;
import ru.leoltron.snake.game.Event;
import ru.leoltron.snake.game.Game;
import ru.leoltron.snake.util.GamePoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Bot extends FieldObjectMoving implements IReactable{

    private Direction direction;
    private int timeToBeAggressive;
    private GamePoint location;
    private ArrayList<GamePoint> snakeLocation;

    public Bot(Direction direction, GamePoint location){
        this.direction = direction;
        this.timeToBeAggressive = 0;
        this.location = location;
        this.velX = 1;
        this.velY = 1;
    }

    public void renewSnakeLocation(ArrayList<GamePoint> locations){
        snakeLocation = locations;
    }

    @Override
    public void processEvent(Event event){
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
        //velX = direction.dx * Math.abs(velX);
        //velY = direction.dy * Math.abs(velY);
        timeToBeAggressive = Math.max(0, timeToBeAggressive - 1);
    }

    private Direction RandomStrategy() {
        return availableDirections.get(new Random().nextInt(availableDirections.size()));
    }

    private Direction HuntStrategy() {
        Direction bestDirection = Direction.UP;
        int distance = 1000 * 1000 * 1000;
        for (val dir : availableDirections) {
            for (val location : snakeLocation) {
                val newLocation = location.add(new GamePoint(dir.dx, dir.dy));
                if (distance > newLocation.manhattanDistance(location)) {
                    bestDirection = dir;
                    distance = newLocation.manhattanDistance(location);
                }
            }
        }
        return bestDirection;
    }
}
