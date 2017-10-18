package ru.leoltron.snake.gui;

import lombok.val;
import ru.leoltron.snake.game.Direction;
import ru.leoltron.snake.game.Game;
import ru.leoltron.snake.game.entity.Apple;
import ru.leoltron.snake.game.entity.SnakePart;
import ru.leoltron.snake.game.entity.Wall;
import ru.leoltron.snake.util.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static java.awt.image.AffineTransformOp.TYPE_BILINEAR;
import static ru.leoltron.snake.game.Direction.*;

public class GamePanel extends JPanel {

    private static HashMap<Pair<Direction, Direction>, Double> angleBetweenDirections = new HashMap<>();

    static {
        angleBetweenDirections.put(Pair.create(DOWN, LEFT), Math.PI);
        angleBetweenDirections.put(Pair.create(LEFT, DOWN), Math.PI);
        angleBetweenDirections.put(Pair.create(DOWN, UP), Math.PI);
        angleBetweenDirections.put(Pair.create(RIGHT, DOWN), Math.PI / 2);
        angleBetweenDirections.put(Pair.create(DOWN, RIGHT), Math.PI / 2);
        angleBetweenDirections.put(Pair.create(RIGHT, LEFT), Math.PI / 2);
        angleBetweenDirections.put(Pair.create(LEFT, UP), 3 * Math.PI / 2);
        angleBetweenDirections.put(Pair.create(UP, LEFT), 3 * Math.PI / 2);
        angleBetweenDirections.put(Pair.create(LEFT, RIGHT), 3 * Math.PI / 2);
    }

    private static double getSnakeImagesRotation(Direction direction1, Direction direction2) {
        return angleBetweenDirections.getOrDefault(Pair.create(direction1, direction2), 0d);
    }

    private int width;
    private int height;
    private Game game;

    public GamePanel(int width, int height, Game game) {
        this.game = game;
        this.width = width;
        this.height = height;
    }

    private static String getSnakePartImageFilename(SnakePart snakePart) {
        String fileName;
        val nextDirection = snakePart.getNextPartDirection();
        val prevDirection = snakePart.getPrevPartDirection();
        if (snakePart.isHead())
            fileName = String.join(File.separator, "resources", "textures", "snake", "head.png");
        else if (snakePart.isTail())
            fileName = String.join(File.separator, "resources", "textures", "snake", "tail.png");
        else if (nextDirection.reversed() == prevDirection)
            fileName = String.join(File.separator, "resources", "textures", "snake", "straight.png");
        else
            fileName = String.join(File.separator, "resources", "textures", "snake", "bend.png");
        return fileName;
    }

    private BufferedImage rotateSnakeImage(BufferedImage image, Direction dirNext, Direction dirPrev) {
        val imgCenterX = image.getWidth(null) / 2;
        val imgCenterY = image.getHeight(null) / 2;
        dirNext = dirNext != null ? dirNext : dirPrev.reversed();
        dirPrev = dirPrev != null ? dirPrev : dirNext.reversed();
        val angle = getSnakeImagesRotation(dirNext, dirPrev);

        val tx = AffineTransform.getRotateInstance(angle, imgCenterX, imgCenterY);
        val op = new AffineTransformOp(tx, TYPE_BILINEAR);
        return op.filter(image, null);
    }

    private Image getImage(int x, int y) throws IOException {
        String fileName;
        Direction dirNext = null;
        Direction dirPrev = null;
        val fieldObject = game.getObjectAt(x, y);
        if (fieldObject == null)
            return null;
        else if (fieldObject instanceof Apple)
            fileName = String.join(File.separator, "resources", "textures", "apple.png");
        else if (fieldObject instanceof Wall)
            fileName = String.join(File.separator, "resources", "textures", "brick.png");
        else if (fieldObject instanceof SnakePart) {
            val snakePart = (SnakePart) fieldObject;
            fileName = getSnakePartImageFilename(snakePart);
            dirNext = snakePart.getNextPartDirection();
            dirPrev = snakePart.getPrevPartDirection();
        } else
            throw new IOException(String.format("Incorrect fieldObject (Object class:%s)", fieldObject.getClass().getName()));
        BufferedImage img = ImageIO.read(new File(fileName));
        if (fieldObject instanceof SnakePart)
            img = rotateSnakeImage(img, dirNext, dirPrev);
        return img;
    }

    @Override
    public void paint(Graphics graphics) {
        try {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    val img = getImage(x, y);
                    if (img == null)
                        continue;
                    graphics.drawImage(img,
                            x * img.getWidth(null),
                            y * img.getHeight(null),
                            null);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}