package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemDto createItem(Item item, Long userId);

    ItemDto getItemById(Long itemId);

    List<ItemDto> getAllItemByUser(Long userId);

    ItemDto updateItem(ItemDto itemDto, Long itemId, Long userId);

    List<ItemDto> searchItem(Long userId, String text);

    void removeItemById(Long itemId, Long userId);
}
