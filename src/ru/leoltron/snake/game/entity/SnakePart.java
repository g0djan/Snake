package ru.leoltron.snake.game.entity;

import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class SnakePart extends FieldObject {

    public  boolean isHead;

    public SnakePart(SnakePart snakePart) {
        this(snakePart.x, snakePart.y);
        prevPart = snakePart;
    }

    public SnakePart(int x, int y) {
        this(x, y, false);
    }

    public SnakePart(int x, int y, boolean isHead) {
        super(x, y);
        this.isHead = isHead;
    }

    @Override
    public void onCollisionWith(FieldObject object) {

    }

    public SnakePart createPrevPart() {
        if (hasPreviousPart())
            return prevPart.createPrevPart();

        return new SnakePart(this);
    }

    @Override
    public void tick() {
        if (!isSpawned) return;

        //TODO: move

        if (!prevPart.isSpawned)
            setSpawned();
    }

    public void MoveTo(int x, int y) {
        val prevX = this.x;
        val prevY = this.y;

        this.x = x;
        this.y = y;

        if (hasPreviousPart())
            MoveTo(prevX, prevY);
    }

    public boolean hasPreviousPart() {
        return prevPart != null;
    }

    public SnakePart setSpawned() {
        prevPart.isSpawned = true;
        return this;
    }

    @Override
    public void setDead() {
        super.setDead();
        if (hasPreviousPart())
            prevPart.setDead();
    }

    public static List<SnakePart> createSnake(int x, int y, int length) {
        val parts = new ArrayList<SnakePart>();
        val head = new SnakePart(x, y, true).setSpawned();
        parts.add(head);

        for (int i = 1; i < length; i++)
            parts.add(head.createPrevPart());

        return parts;
    }
}
