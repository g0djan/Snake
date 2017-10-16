package ru.leoltron.snake.gui;//package ru.leoltron.snake.gui;
import lombok.val;
import ru.leoltron.snake.game.Direction;
import ru.leoltron.snake.game.Game;
import ru.leoltron.snake.game.entity.Apple;
import ru.leoltron.snake.game.entity.SnakePart;
import ru.leoltron.snake.game.entity.Wall;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.AffineTransformOp.TYPE_BILINEAR;

public class GUI extends JPanel {
    private int width;
    private int height;
    private Game game;

    public GUI(int width, int height, Game game){
        this.game = game;
        this.width = width;
        this.height = height;
    }

    public void tick(){
        game.tick();
    }

    private String getSnakePartImageFilename(SnakePart snakePart){
        String fileName;
        val nextDirection = snakePart.getNextDirection();
        val prevDirection = snakePart.getPrevDirection();
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

    private BufferedImage rotateImage(BufferedImage img, Direction dirNext, Direction dirPrev){
        val x = img.getWidth(null) / 2;
        val y = img.getHeight(null) / 2;
        dirNext = dirNext != null ? dirNext : dirPrev.reversed();
        dirPrev = dirPrev != null ? dirPrev : dirNext.reversed();
        double angle;
        angle = Direction.getSnakeImagesRotation(dirNext, dirPrev);
        val tx = AffineTransform.getRotateInstance(angle, x, y);
        val op = new AffineTransformOp(tx, TYPE_BILINEAR);
        return op.filter(img, null);
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
            dirNext = snakePart.getNextDirection();
            dirPrev = snakePart.getPrevDirection();
        }
        else
            throw new IOException("Incorrect fieldObject");
        BufferedImage img = ImageIO.read(new File(fileName));
        if (fieldObject instanceof SnakePart)
            img = rotateImage(img,dirNext,dirPrev);
        return img;
    }

    public void paint(Graphics g) {
        try {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    val img = getImage(x, y);
                    if (img == null)
                        continue;
                    g.drawImage(img,
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