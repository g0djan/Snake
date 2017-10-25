package ru.leoltron.snake.gui.Drawers;

import lombok.NonNull;
import lombok.val;
import ru.leoltron.snake.game.Direction;
import ru.leoltron.snake.game.entity.FieldObject;
import ru.leoltron.snake.game.entity.SnakePart;
import ru.leoltron.snake.util.Pair;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static java.awt.image.AffineTransformOp.TYPE_BILINEAR;
import static ru.leoltron.snake.game.Direction.*;

public class SnakeDrawer implements IDrawer {

    public SnakeDrawer() throws IOException {
        images = new ArrayList<>();
        addImage("resources", "textures", "snake", "bend.png");
        addImage("resources", "textures", "snake", "head.png");
        addImage("resources", "textures", "snake", "headTongue.png");
        addImage("resources", "textures", "snake", "straight.png");
        addImage( "resources", "textures", "snake", "tail.png");
        addImage( "resources", "textures", "snake", "tailRight.png");
        addImage( "resources", "textures", "snake", "tailLeft.png");
    }

    @Override
    public BufferedImage GetImage(FieldObject fieldObject, int time) {
        val snakePart = (SnakePart)fieldObject;
        val nextDirection = snakePart.getNextPartDirection();
        val prevDirection = snakePart.getPrevPartDirection();
        BufferedImage img;
        if (snakePart.isHead())
            img = images.get(1 + time % 2);
        else if (snakePart.isTail())
            img = images.get(4 + time % 3);
        else if (nextDirection.reversed() == prevDirection)
            img = images.get(3);
        else
            img = images.get(0);
        return rotateSnakeImage(img, nextDirection, prevDirection);
    }

    private ArrayList<BufferedImage> images;

    private void addImage(String... path) throws IOException {
        images.add(ImageIO.read(new File(String.join(File.separator, path))));
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
}
