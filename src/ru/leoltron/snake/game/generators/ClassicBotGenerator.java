package ru.leoltron.snake.game.generators;

import lombok.val;
import ru.leoltron.snake.game.Direction;
import ru.leoltron.snake.game.GameField;
import ru.leoltron.snake.game.entity.Apple;
import ru.leoltron.snake.game.entity.Bot;
import ru.leoltron.snake.game.entity.EventDispatcher;
import ru.leoltron.snake.game.entity.FieldObject;
import ru.leoltron.snake.util.GamePoint;

public class ClassicBotGenerator implements FieldObjectGenerator{

    @Override
    public void onStartNewGame(GameField field, EventDispatcher dispatcher) {
        GamePoint location = field.getRandomFreeLocation();
        location = new GamePoint(18, 10);
        val bot = new Bot(Direction.UP, location);
        field.addEntity(location, bot);
        dispatcher.addListener(bot);
        bot.renewAvailableDirections(field.getAvailableDirection(location));
        tick(field);
    }

    @Override
    public void tick(GameField field) {
    }
}
