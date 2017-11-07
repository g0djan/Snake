package ru.leoltron.snake.game.entity;


import ru.leoltron.snake.game.Event;
import ru.leoltron.snake.game.GameField;

import java.util.ArrayList;
import java.util.List;

public final class EventDispatcher {

    private static List<FieldObject> listeners = new ArrayList<>();
    private GameField field;
    public EventDispatcher(GameField field) {
        this.field = field;
    }

    public static void addListener(FieldObject object) {
        listeners.add(object);
    }

    public static void sendMessage(Event message) {
        for (FieldObject object : listeners) {
            object.reactToEvent(message);
        }
    }

}
