package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dal.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dal.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public ItemDto createItem(Item item, Long userId) {
        User user = userRepository.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("USER not found"));

        item.setOwner(user);
        return ItemMapper.mapToItemDto(itemRepository.createItem(item));
    }

    @Override
    public ItemDto getItemById(Long itemId) {
        Item item = itemRepository.getItemById(itemId)
                .orElseThrow(() -> new NotFoundException("ITEM not found"));
        return ItemMapper.mapToItemDto(item);
    }

    @Override
    public List<ItemDto> getAllItemByUser(Long userId) {
        return itemRepository.getAllItemByUser(userId).stream()
                .map(ItemMapper::mapToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto, Long itemId, Long userId) {
        User user = userRepository.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Item oldItem = itemRepository.getItemById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found"));
        Item item = ItemMapper.mapToItem(itemDto);
        if (!oldItem.getOwner().equals(user)) {
            throw new NotFoundException("Item not found");
        }
        return ItemMapper.mapToItemDto(itemRepository.updateItem(item, itemId));
    }

    @Override
    public List<ItemDto> searchItem(Long userId, String text) {
        userRepository.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("USER not found."));
        return itemRepository.searchItem(text).stream()
                .map(ItemMapper::mapToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public void removeItemById(Long itemId, Long userId) {
        itemRepository.removeItemById(itemId, userId);
    }
}
