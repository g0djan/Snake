package ru.leoltron.snaketests;

import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import ru.leoltron.snake.game.ClassicSnakeController;
import ru.leoltron.snake.game.Direction;
import ru.leoltron.snake.game.Game;
import ru.leoltron.snake.game.GameField;
import ru.leoltron.snake.game.entity.EventDispatcher;
import ru.leoltron.snake.game.entity.SnakePart;
import ru.leoltron.snake.game.generators.ClassicBotGenerator;
import ru.leoltron.snake.game.generators.FieldObjectGenerator;
import ru.leoltron.snake.game.generators.ClassicGameFieldGenerator;
import ru.leoltron.snake.util.GamePoint;

public class SnakeTests extends Assert {

    private class EmptyAppleGenerator implements FieldObjectGenerator {

        @Override
        public void onStartNewGame(GameField field, EventDispatcher dispatcher) {
        }

        @Override
        public void tick(GameField field) {
        }
    }

    @Test
    public void testSnakeSuicide() {
        val game = new Game(
                new EmptyAppleGenerator(),
                new ClassicBotGenerator(),
                new ClassicGameFieldGenerator(),
                new ClassicSnakeController(5),
                10, 10);
        game.startNewGame();

        twistSnake(game);

        assertTrue(game.isGameOver());
    }

    @Test
    public void testSnakeTailFollowing() {
        val game = new Game(
                new EmptyAppleGenerator(),
                new ClassicBotGenerator(),
                new ClassicGameFieldGenerator(),
                new ClassicSnakeController(4),
                10, 10);
        game.startNewGame();

        twistSnake(game);

        assertFalse(game.isGameOver());
    }

    private static void twistSnake(Game game) {
        game.setCurrentDirection(Direction.UP);
        game.tick();

        game.setCurrentDirection(Direction.RIGHT);
        game.tick();

        game.setCurrentDirection(Direction.DOWN);
        game.tick();

        game.setCurrentDirection(Direction.LEFT);
        game.tick();
    }

    @Test
    public void testSnakeMoving() {
        val width = 10;
        val height = 10;

        val game = new Game(
                new EmptyAppleGenerator(),
                new ClassicBotGenerator(),
                new ClassicGameFieldGenerator(),
                new ClassicSnakeController(4),
                width, height);
        game.startNewGame();

        val headBeforeTickLocation = findSnakeHead(game, 0, 0, width, height);

        game.setCurrentDirection(Direction.UP);
        game.tick();

        val headAfterTickLocation = findSnakeHead(game, 0, 0, width, height);

        assertEquals(new GamePoint(0, -1), headAfterTickLocation.subtract(headBeforeTickLocation));
    }

    private static GamePoint findSnakeHead(Game game, int startX, int startY, int endX, int endY) {
        for (int x = startX; x < endX; x++)
            for (int y = startY; y < endY; y++) {
                val fieldObj = game.getObjectAt(x, y);
                if (fieldObj != null && fieldObj instanceof SnakePart && ((SnakePart) fieldObj).isHead())
                    return new GamePoint(x, y);
            }
        return null;
    }
}
