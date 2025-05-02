package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.service.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {

    private RequestService requestService;

    @PostMapping
    public RequestDto createRequest(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody RequestDto requestDto) {
        log.info("POST / requests: Create Request - {}, User with ID '{}'.", requestDto.getDescription(), userId);
        return requestService.createRequest(userId, requestDto);
    }

    @GetMapping
    public List<RequestDto> getUserRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET / requests: Get Requests by User - {}", userId);
        return null;
    }

    @GetMapping("/all")
    public List<RequestDto> getAllRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Get /requests/all: Get all Requests");
        return null;
    }

    @GetMapping("/{id}")
    public RequestDto getRequestById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable("id") Long requestId) {
        log.info("Get / requests/{requestId}: Get Request by ID'{}'", requestId);
        return null;
    }
}
