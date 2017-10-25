package ru.leoltron.snake.gui;

import lombok.val;
import ru.leoltron.snake.game.Game;
import ru.leoltron.snake.game.entity.Apple;
import ru.leoltron.snake.game.entity.SnakePart;
import ru.leoltron.snake.game.entity.Wall;
import ru.leoltron.snake.gui.Drawers.IDrawer;
import ru.leoltron.snake.gui.Drawers.SnakeDrawer;
import ru.leoltron.snake.gui.Drawers.StaticObjectDrawer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class GamePanel extends JPanel {

    private int width;
    private int height;
    private Game game;
    private HashMap<Class, IDrawer> drawers = new HashMap<>();


    public GamePanel(int width, int height, Game game) throws IOException {
        this.game = game;
        this.width = width;
        this.height = height;
        drawers.put(Apple.class, new StaticObjectDrawer("resources", "textures", "apple.png"));
        drawers.put(Wall.class, new StaticObjectDrawer("resources", "textures", "brick.png"));
        drawers.put(SnakePart.class, new SnakeDrawer());
    }

    private Image getImageAt(int x, int y) throws IOException {
        val fieldObject = game.getObjectAt(x, y);
        if (fieldObject == null)
            return null;
        val imgGetter = drawers.get(fieldObject.getClass());
        return imgGetter.getImage(fieldObject, game.getTime());
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