package ru.leoltron.snake.game.entity;


import lombok.val;
import ru.leoltron.snake.game.Event;
import java.util.ArrayList;
import java.util.List;

public final class EventDispatcher {

    private List<FieldObject> listeners;

    public EventDispatcher() {
        listeners = new ArrayList<>();
    }

    public void addListener(FieldObject object) {
        listeners.add(object);
    }

    public void processEvents(List<FieldObject> occurredEvents) {
        for (val object : occurredEvents) {
            sendMessage(object.getOccurredEvent());
        }
    }

    private void sendMessage(Event message) {
        for (FieldObject object : listeners) {
            object.reactToEvent(message);
        }
    }

}
