package ru.leoltron.snake.gui.Drawers;

import ru.leoltron.snake.game.entity.FieldObject;

import java.awt.image.BufferedImage;

interface IDrawer {
    BufferedImage GetImage(FieldObject fieldObject, int time);
}
