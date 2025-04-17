package ru.practicum.shareit.item.dal;

import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemStorage {
    Item createItem(Item item);

    Optional<Item> getItemById(Long itemId);

    List<Item> getAllItemByUser(Long userId);

    Item updateItem(Item item, Long itemId);

    List<Item> searchItem(String text);

    void removeItemById(Long itemId, Long userId);
}
