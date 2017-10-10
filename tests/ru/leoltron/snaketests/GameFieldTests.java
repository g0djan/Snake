package ru.leoltron.snaketests;

import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.leoltron.snake.game.CollisionException;
import ru.leoltron.snake.game.GameField;
import ru.leoltron.snake.game.entity.FieldObject;
import ru.leoltron.snake.game.entity.Wall;

import java.awt.*;

public class GameFieldTests extends Assert {

    private GameField field;

    @Before
    public void createField() {
        field = new GameField(10, 10);
    }

    @Test
    public void addEntityTest() {
        int wallX = 1;
        int wallY = 2;
        Wall wall = new Wall();
        field.addEntity(new Point(wallX, wallY), wall);
        assertSingleFieldAdded(wall, wallX, wallY);

        field = new GameField(field.getFieldWidth(), field.getFieldHeight());
        field.addEntity(wallX, wallY, wall);
        assertSingleFieldAdded(wall, wallX, wallY);
    }

    private void assertSingleFieldAdded(Wall object, int objectX, int objectY) {
        assertTrue(field.getEntityAt(objectX, objectY) == object);

        for (int x = 0; x < field.getFieldWidth(); x++)
            for (int y = 0; y < field.getFieldHeight(); y++)
                if (x != objectX || y != objectY)
                    assertTrue(field.getEntityAt(x, y) == null);
    }

    @Test
    public void addEntityAlmostOutsideTest() {
        Wall wall = new Wall();
        int wallX = field.getFieldWidth() - 1;
        int wallY = field.getFieldHeight() - 1;

        field.addEntity(new Point(wallX, wallY), wall);

        Wall wall2 = new Wall();
        wallX = 0;
        wallY = 0;

        field.addEntity(new Point(wallX, wallY), wall2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addEntityOutsideTest() {
        Wall wall = new Wall();
        int wallX = 100;
        int wallY = 100;
        field.addEntity(new Point(wallX, wallY), wall);
    }

    @Test
    public void TestFree() {

        int wallX = 5;
        int wallY = 8;
        Wall wall = new Wall();
        field.addEntity(new Point(wallX, wallY), wall);

        for (int x = 0; x < field.getFieldWidth(); x++)
            for (int y = 0; y < field.getFieldHeight(); y++)
                if (x != wallX || y != wallY) {
                    assertTrue(field.isFree(x, y));
                    assertTrue(field.isFree(new Point(x, y)));
                }
    }

    @Test
    public void randCoordsTest() {
        field.addEntity(1, 5, new Wall());
        field.addEntity(4, 0, new Wall());
        field.addEntity(0, 4, new Wall());
        field.addEntity(6, 7, new Wall());
        field.addEntity(4, 9, new Wall());
        field.addEntity(0, 8, new Wall());


        for (int i = 0; i < 100; i++) {
            val randLocation = field.getRandomFreeLocation();
            assertTrue(field.isFree(randLocation));
        }
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void addEntityOnLeftBorderTest() {
        Wall wall = new Wall();
        int wallX = -1;
        int wallY = 5;
        field.addEntity(new Point(wallX, wallY), wall);
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void addEntityOnRightBorderTest() {
        Wall wall = new Wall();
        int wallX = field.getFieldWidth();
        int wallY = 2;
        field.addEntity(new Point(wallX, wallY), wall);
    }

    @Test(expected = CollisionException.class)
    public void testCollision() {
        field.addEntity(new Point(4, 5), new Wall() {
            @Override
            public void onCollisionWith(FieldObject object) {

            }
        });
        field.addEntity(new Point(4, 5), new Wall() {
            @Override
            public void onCollisionWith(FieldObject object) {

            }
        });
    }

    @Test
    public void testNoCollisions() {
        for (int x = 0; x < field.getFieldWidth(); x++)
            for (int y = 0; y < field.getFieldHeight(); y++)
                field.addEntity(new Point(x, y), new Wall());
    }

    @Test
    public void testRemove() {
        int wallX = 1;
        int wallY = 2;
        Wall wall = new Wall();
        field.addEntity(new Point(wallX, wallY), wall);
        assertSingleFieldAdded(wall, wallX, wallY);
        field.removeEntityAt(new Point(wallX, wallY));

        for (int x = 0; x < field.getFieldWidth(); x++)
            for (int y = 0; y < field.getFieldHeight(); y++)
                assertTrue(field.isFree(x, y));

    }


}
