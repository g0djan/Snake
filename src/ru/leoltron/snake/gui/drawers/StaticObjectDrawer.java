package ru.leoltron.snake.gui.drawers;

import ru.leoltron.snake.game.entity.FieldObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StaticObjectDrawer implements IDrawer {

    private final BufferedImage image;

    public StaticObjectDrawer(String... path) throws IOException {
        image = ImageIO.read(new File(String.join(File.separator, path)));
    }

    @Override
    public BufferedImage getImage(FieldObject fieldObject, int time) {
        return image;
    }
}
