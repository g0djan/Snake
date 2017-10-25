package ru.leoltron.snake.gui.Drawers;

import ru.leoltron.snake.game.entity.FieldObject;

import java.awt.image.BufferedImage;

public interface IDrawer {
    BufferedImage getImage(FieldObject fieldObject, int time);
}
