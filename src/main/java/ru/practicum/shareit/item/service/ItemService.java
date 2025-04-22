package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.comments.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoOutput;

import java.util.List;

public interface ItemService {
    ItemDto createItem(ItemDto itemDto, Long userId);

    ItemDtoOutput getItemById(Long itemId, Long userId);

    List<ItemDtoOutput> getAllItemByUser(Long userId);

    ItemDto updateItem(ItemDto itemDto, Long itemId, Long userId);

    List<ItemDto> searchItem(Long userId, String text);

    CommentDto addComment(Long userId, Long itemId, CommentDto commentDto);
}
