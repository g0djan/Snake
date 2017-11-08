package ru.leoltron.snake.game.entity;


import org.omg.CORBA.IRObject;
import ru.leoltron.snake.game.Event;
import ru.leoltron.snake.game.GameField;

import java.util.ArrayList;
import java.util.List;

public final class EventDispatcher {

    private List<IReactable> listeners;

    public EventDispatcher() {
        listeners = new ArrayList<>();
    }

    public void addListener(IReactable object) {
        listeners.add(object);
    }

    public void sendMessage(Event message) {
        for (IReactable object : listeners) {
            object.reactToEvent(message);
        }
    }

}
