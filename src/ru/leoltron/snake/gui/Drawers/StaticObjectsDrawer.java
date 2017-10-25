package ru.leoltron.snake.gui.Drawers;

import ru.leoltron.snake.game.entity.Apple;
import ru.leoltron.snake.game.entity.FieldObject;
import ru.leoltron.snake.game.entity.Wall;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class StaticObjectsDrawer implements IDrawer {

    public StaticObjectsDrawer() throws IOException {
        images = new HashMap<>();
        addImage(Apple.class,"resources", "textures", "apple.png");
        addImage(Wall.class,"resources", "textures", "brick.png");
    }

    @Override
    public BufferedImage GetImage(FieldObject fieldObject, int time) {
        return images.get(fieldObject.getClass());
    }

    private HashMap<Class, BufferedImage> images;

    private void addImage(Class cls, String... path) throws IOException {
        images.put(cls, ImageIO.read(new File(String.join(File.separator, path))));
    }
}
