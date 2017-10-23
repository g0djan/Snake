package ru.leoltron.snake.gui;

import lombok.NonNull;
import lombok.val;
import ru.leoltron.snake.game.Direction;
import ru.leoltron.snake.game.Game;
import ru.leoltron.snake.game.entity.Apple;
import ru.leoltron.snake.game.entity.FieldObject;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import static java.awt.image.AffineTransformOp.TYPE_BILINEAR;
import static ru.leoltron.snake.game.Direction.*;

public class GamePanel extends JPanel {

    private static HashMap<Pair<Direction, Direction>, Double> angleToRotateByDirections = new HashMap<>();

    static {
        angleToRotateByDirections.put(Pair.create(DOWN, LEFT), Math.PI);
        angleToRotateByDirections.put(Pair.create(LEFT, DOWN), Math.PI);
        angleToRotateByDirections.put(Pair.create(DOWN, UP), Math.PI);
        angleToRotateByDirections.put(Pair.create(RIGHT, DOWN), Math.PI / 2);
        angleToRotateByDirections.put(Pair.create(DOWN, RIGHT), Math.PI / 2);
        angleToRotateByDirections.put(Pair.create(RIGHT, LEFT), Math.PI / 2);
        angleToRotateByDirections.put(Pair.create(LEFT, UP), 3 * Math.PI / 2);
        angleToRotateByDirections.put(Pair.create(UP, LEFT), 3 * Math.PI / 2);
        angleToRotateByDirections.put(Pair.create(LEFT, RIGHT), 3 * Math.PI / 2);
    }

    private static double getSnakeImagesRotation(Direction direction1, Direction direction2) {
        return angleToRotateByDirections.getOrDefault(Pair.create(direction1, direction2), 0d);
    }

    private BufferedImage getSnakePartImage(SnakePart snakePart) {
        val nextDirection = snakePart.getNextPartDirection();
        val prevDirection = snakePart.getPrevPartDirection();
        BufferedImage img;
        if (snakePart.isHead())
            img = images.get(SnakePart.class).get(1 + game.getTimer() % 2);
        else if (snakePart.isTail())
            img = images.get(SnakePart.class).get(4 + game.getTimer() % 3);
        else if (nextDirection.reversed() == prevDirection)
            img = images.get(SnakePart.class).get(3);
        else
            img = images.get(SnakePart.class).get(0);
        return rotateSnakeImage(img, nextDirection, prevDirection);
    }

    private int width;
    private int height;
    private Game game;
    private HashMap<Class, ArrayList<BufferedImage>> images;
    private HashMap<Class, Function<FieldObject, BufferedImage>> imageClassToImageGetter;

    private void addImage(Class cls, String... path) throws IOException {
        images.get(cls).add(ImageIO.read(new File(String.join(File.separator, path))));
    }

    private void readImages() throws IOException {
        images = new HashMap<>();
        images.put(Apple.class, new ArrayList<>());
        images.put(Wall.class, new ArrayList<>());
        images.put(SnakePart.class, new ArrayList<>());
        addImage(Apple.class,"resources", "textures", "apple.png");
        addImage(Wall.class,"resources", "textures", "brick.png");
        addImage(SnakePart.class,"resources", "textures", "snake", "bend.png");
        addImage(SnakePart.class,"resources", "textures", "snake", "head.png");
        addImage(SnakePart.class,"resources", "textures", "snake", "headTongue.png");
        addImage(SnakePart.class,"resources", "textures", "snake", "straight.png");
        addImage(SnakePart.class, "resources", "textures", "snake", "tail.png");
        addImage(SnakePart.class, "resources", "textures", "snake", "tailRight.png");
        addImage(SnakePart.class, "resources", "textures", "snake", "tailLeft.png");
    }

    public GamePanel(int width, int height, Game game) throws IOException {
        this.game = game;
        this.width = width;
        this.height = height;
        readImages();
        imageClassToImageGetter = new HashMap<>();
        imageClassToImageGetter.put(Apple.class, (fObject) -> images.get(Apple.class).get(0));
        imageClassToImageGetter.put(Wall.class, (fObject) -> images.get(Wall.class).get(0));
        imageClassToImageGetter.put(SnakePart.class, (fObject) -> getSnakePartImage((SnakePart)fObject));
    }

    private BufferedImage rotateSnakeImage(@NonNull BufferedImage image, Direction dirNext, Direction dirPrev) {
        if (dirNext == null && dirPrev == null)
            return image;

        val imgCenterX = image.getWidth(null) / 2;
        val imgCenterY = image.getHeight(null) / 2;
        dirNext = dirNext != null ? dirNext : dirPrev.reversed();
        dirPrev = dirPrev != null ? dirPrev : dirNext.reversed();
        val angle = getSnakeImagesRotation(dirNext, dirPrev);

        val tx = AffineTransform.getRotateInstance(angle, imgCenterX, imgCenterY);
        val op = new AffineTransformOp(tx, TYPE_BILINEAR);
        return op.filter(image, null);
    }

    private Image getImageAt(int x, int y) throws IOException {
        String fileName;
        Direction dirNext = null;
        Direction dirPrev = null;
        val fieldObject = game.getObjectAt(x, y);
        if (fieldObject == null)
            return null;
        val imgGetter = imageClassToImageGetter.get(fieldObject.getClass());
        return imgGetter.apply(fieldObject);
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