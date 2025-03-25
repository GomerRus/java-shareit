package ru.practicum.shareit.item.dal;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class ItemRepositoryInMemory implements ItemRepository {

    private final Map<Long, Item> items = new HashMap<>();

    private long getNextId() {
        long currentMaxId = items.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    @Override
    public Item createItem(Item item) {
        item.setId(getNextId());
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Optional<Item> getItemById(Long itemId) {
        Item item = items.get(itemId);
        if (item == null) {
            return Optional.empty();
        }
        return Optional.of(item);
    }

    @Override
    public List<Item> getAllItemByUser(Long userId) {
        return items.values().stream()
                .filter(item -> item.getOwner().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Item updateItem(Item item, Long itemId) {
        Item oldItem = items.get(itemId);
        if (item.getName() != null) {
            oldItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            oldItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            oldItem.setAvailable(item.getAvailable());
        }
        return oldItem;
    }

    @Override
    public List<Item> searchItem(String text) {
        if (text != null && !text.isBlank()) {
            return items.values().stream()
                    .filter(
                            (((Predicate<Item>) item -> item.getName().toLowerCase().contains(text.toLowerCase()))
                                    .or(item -> item.getDescription().toLowerCase().contains(text.toLowerCase())))
                                    .and(Item::getAvailable))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public void removeItemById(Long itemId, Long userId) {
        items.remove(itemId);
    }
}
