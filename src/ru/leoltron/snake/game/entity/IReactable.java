package ru.leoltron.snake.game.entity;

import ru.leoltron.snake.game.Event;

public interface IReactable {
    void reactToEvent(Event message);
}
