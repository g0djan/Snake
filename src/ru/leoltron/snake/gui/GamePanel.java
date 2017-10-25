package ru.leoltron.snake.gui;

import lombok.NonNull;
import lombok.val;
import ru.leoltron.snake.game.Direction;
import ru.leoltron.snake.game.Game;
import ru.leoltron.snake.game.entity.Apple;
import ru.leoltron.snake.game.entity.FieldObject;
import ru.leoltron.snake.game.entity.SnakePart;
import ru.leoltron.snake.game.entity.Wall;
import ru.leoltron.snake.gui.Drawers.SnakeDrawer;
import ru.leoltron.snake.gui.Drawers.StaticObjectsDrawer;
import ru.leoltron.snake.util.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.awt.image.AffineTransformOp.TYPE_BILINEAR;
import static ru.leoltron.snake.game.Direction.*;

public class GamePanel extends JPanel {

    private static HashMap<Pair<Direction, Direction>, Double> angleToRotateByDirections = new HashMap<>();


    private int width;
    private int height;
    private Game game;
    private StaticObjectsDrawer staticObjectsDrawer;
    private SnakeDrawer snakeDrawer;
    private HashMap<Class, BiFunction<FieldObject, Integer, BufferedImage>> imageClassToImageGetter;


    public GamePanel(int width, int height, Game game) throws IOException {
        this.game = game;
        this.width = width;
        this.height = height;
        staticObjectsDrawer = new StaticObjectsDrawer();
        snakeDrawer = new SnakeDrawer();
        imageClassToImageGetter = new HashMap<>();
        imageClassToImageGetter.put(Apple.class, (fObject, timer) -> staticObjectsDrawer.GetImage(fObject, timer));
        imageClassToImageGetter.put(Wall.class, (fObject, timer) -> staticObjectsDrawer.GetImage(fObject, timer));
        imageClassToImageGetter.put(SnakePart.class, (fObject, timer) -> snakeDrawer.GetImage(fObject, timer));
    }

    private Image getImageAt(int x, int y) throws IOException {
        String fileName;
        Direction dirNext = null;
        Direction dirPrev = null;
        val fieldObject = game.getObjectAt(x, y);
        if (fieldObject == null)
            return null;
        val imgGetter = imageClassToImageGetter.get(fieldObject.getClass());
        return imgGetter.apply(fieldObject, game.getTimer());
    }

    @Override
    public void paint(Graphics graphics) {
        try {
            for (int x = 0; x < width; x++)
                for (int y = 0; y < height; y++)
                    drawFieldObject(graphics, x, y);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawFieldObject(Graphics graphics, int fieldObjX, int fieldObjY) throws IOException {
        val img = getImageAt(fieldObjX, fieldObjY);
        if (img == null)
            return;
        graphics.drawImage(img,
                fieldObjX * img.getWidth(null),
                fieldObjY * img.getHeight(null),
                null);
    }
}