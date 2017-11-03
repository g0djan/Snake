package ru.leoltron.snake.game.entity;


import java.util.ArrayList;
import java.util.List;

public final class EventDispatcher {

    private static List<FieldObject> listeners = new ArrayList<>();
    private EventDispatcher() {}

    public static void addListener(FieldObject object) {
        listeners.add(object);
    }

    public static void sendMessege() {
        for (FieldObject object : listeners) {
            object.reactToEvent();
        }
    }

}
