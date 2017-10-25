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
import java.util.HashMap;

import static java.awt.image.AffineTransformOp.TYPE_BILINEAR;
import static ru.leoltron.snake.game.Direction.*;

public class SnakeDrawer implements IDrawer {

    private static final BufferedImage BEND = tryGetSnakeImage("bend.png");
    private static final BufferedImage[] HEADS = {
            tryGetSnakeImage("head.png"),
            tryGetSnakeImage("headTongue.png")
    };

    private static final BufferedImage STRAIGHT = tryGetSnakeImage("straight.png");
    private static final BufferedImage[] TAILS = {
            tryGetSnakeImage("tail.png"),
            tryGetSnakeImage("tailRight.png"),
            tryGetSnakeImage("tailLeft.png")
    };

    @Override
    public BufferedImage getImage(FieldObject fieldObject, int time) {
        val snakePart = (SnakePart) fieldObject;
        val nextDirection = snakePart.getNextPartDirection();
        val prevDirection = snakePart.getPrevPartDirection();
        BufferedImage img;
        if (snakePart.isHead())
            img = HEADS[time % HEADS.length];
        else if (snakePart.isTail())
            img = TAILS[time % TAILS.length];
        else if (nextDirection.reversed() == prevDirection)
            img = STRAIGHT;
        else
            img = BEND;
        return rotateSnakeImage(img, prevDirection, nextDirection);
    }

    private static BufferedImage getImage(String... path) throws IOException {
        return ImageIO.read(new File(String.join(File.separator, path)));
    }

    private static BufferedImage tryGetSnakeImage(String imageName) {
        try {
            return getImage("resources", "textures", "snake", imageName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private BufferedImage rotateSnakeImage(@NonNull BufferedImage image, Direction dirPrev, Direction dirNext) {
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
