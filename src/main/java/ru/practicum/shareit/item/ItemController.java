package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ItemDto createItem(@Valid @RequestBody Item item, @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("POST / items: Create ITEM - {}", item.getName());
        return itemService.createItem(item, userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable("itemId") Long itemId) {
        log.info("GET /items: Get ITEM by ID: {}", itemId);
        return itemService.getItemById(itemId);
    }

    @GetMapping
    public List<ItemDto> getAllItemsByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET /items: Get list the ALL ITEMS of owner ID: {}", userId);
        return itemService.getAllItemByUser(userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@Valid @RequestBody ItemDto item, @PathVariable("itemId") Long itemId,
                              @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("PATH /items: Update ITEM by ID '{}' of owner ID '{}'", itemId, userId);
        return itemService.updateItem(item, itemId, userId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                                    @RequestParam(name = "text") String text) {
        log.info("GET /items/search: Get list of available ITEMS with text {}", text);
        return itemService.searchItem(userId, text);
    }

    @DeleteMapping("/{itemId}")
    public void removeItemById(@PathVariable("itemId") Long itemId, @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("DELETE / items: Delete ITEM by ID '{}' of owner ID '{}'", itemId, userId);
        itemService.removeItemById(itemId, userId);
    }
}
