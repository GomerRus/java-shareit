package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.Request;

public class RequestMapper {
    public static RequestDto mapToRequestDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .description(request.getDescription())
                .requestor(request.getRequestor() != null ? request.getRequestor() : null)
                .created(request.getCreated()).build();
    }

    public static Request mapToRequest(RequestDto requestDto) {
        return Request.builder()
                .id(requestDto.getId())
                .description(requestDto.getDescription())
                .requestor(requestDto.getRequestor() != null ? requestDto.getRequestor() : null)
                .created(requestDto.getCreated())
                .build();
    }

    public static RequestItemDto mapToRequestItemDto(Item item) {
        return RequestItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .owner(item.getOwner().getId())
                .build();
    }
}