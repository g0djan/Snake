package ru.leoltron.snake.gui.drawers;

import ru.leoltron.snake.game.entity.FieldObject;

import java.awt.image.BufferedImage;

public interface IDrawer {
    BufferedImage getImage(FieldObject fieldObject, int time);
}
