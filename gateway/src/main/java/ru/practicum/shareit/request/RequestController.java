package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestDto;

@Slf4j
@Validated
@Controller
@RequestMapping("/requests")
@RequiredArgsConstructor

public class RequestController {

    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestHeader("X-Sharer-User-Id") Long userId, @Valid @RequestBody RequestDto requestDto) {
        log.info("POST / requests: Create Request - {}, User with ID '{}'.", requestDto.getDescription(), userId);
        return requestClient.createRequest(userId, requestDto);
    }

    @GetMapping
    public ResponseEntity<Object> getUserRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET / requests: Get Requests by User - {}", userId);
        return requestClient.getUserRequests(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Get /requests/all: Get all Requests");
        return requestClient.getAllRequests(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRequestById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable("id") Long requestId) {
        log.info("Get / requests/{requestId}: Get Request by ID'{}'", requestId);
        return requestClient.getRequestById(userId, requestId);
    }
}
