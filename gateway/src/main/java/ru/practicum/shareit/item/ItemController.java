package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

@Slf4j
@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
@Validated
public class ItemController {

    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestBody @Valid ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("POST / items: Create ITEM - {}", itemDto.getName());
        return itemClient.createItem(userId, itemDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItemById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable("id") Long itemId) {
        log.info("GET /items: Get ITEM by ID: {}", itemId);
        return itemClient.getItemById(userId, itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllItemsByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET /items: Get list the ALL ITEMS of owner ID: {}", userId);
        return itemClient.getAllItemByUser(userId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateItem(@Valid @RequestBody ItemDto itemDto, @PathVariable("id") Long itemId,
                                             @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("PATH /items: Update ITEM by ID '{}' of owner ID '{}'", itemId, userId);
        return itemClient.updateItem(userId, itemId, itemDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                                             @RequestParam(name = "text")
                                             @NotBlank(message = "Text не может быть пустым или содержать только пробелы")
                                             String text) {
        log.info("GET /items/search: Get list of available ITEMS with text {}", text);
        return itemClient.searchItem(userId, text);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable("id") Long itemId,
                                             @RequestBody @Valid CommentDto commentDto) {
        return itemClient.addComment(userId, itemId, commentDto);
    }
}
