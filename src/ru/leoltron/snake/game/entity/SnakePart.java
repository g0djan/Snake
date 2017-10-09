package ru.leoltron.snake.game.entity;

import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class SnakePart extends GameEntity {

    public final boolean isHead;
    private boolean isSpawned = false;
    private SnakePart prevPart;

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
    public void onCollisionWith(GameEntity entity) {

    }

    public SnakePart createPrevPart() {
        if (prevPart != null)
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

    public SnakePart setSpawned() {
        prevPart.isSpawned = true;
        return this;
    }

    @Override
    public void setDead() {
        super.setDead();
        if (prevPart != null)
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
