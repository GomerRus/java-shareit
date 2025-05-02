package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dal.ItemRepository;
import ru.practicum.shareit.request.dal.RequestRepository;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestItemDto;
import ru.practicum.shareit.request.dto.RequestMapper;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.user.dal.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final ItemRepository itemRepository;

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("USER not found with ID '%d'.", userId)));
    }

    private Request getRequest(Long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(String.format("REQUEST not found with ID '%d'.", requestId)));
    }

    @Override
    public RequestDto createRequest(Long userId, RequestDto requestDto) {
        User requestor = getUser(userId);
        Request request = RequestMapper.mapToRequest(requestDto);
        request.setRequestor(requestor);
        request.setCreated(LocalDateTime.now());
        return RequestMapper.mapToRequestDto(requestRepository.save(request));
    }

    @Override
    public List<RequestDto> getUserRequests(Long userId) {
        User requestor = getUser(userId);
        List<RequestDto> requests = requestRepository.getUserRequests(requestor).stream()
                .map(RequestMapper::mapToRequestDto)
                .toList();
        for (RequestDto requestDto : requests) {
            List<RequestItemDto> items = itemRepository.findAllByRequestId(requestDto.getId()).stream()
                    .map(RequestMapper::mapToRequestItemDto)
                    .toList();
            requestDto.setItems(items);
        }
        return requests;
    }

    @Override
    public List<RequestDto> getAllRequests(Long userId) {
        getUser(userId);
        return requestRepository.findAllByRequestorIdNot(userId).stream()
                .map(RequestMapper::mapToRequestDto)
                .toList();
    }

    @Override
    public RequestDto getRequestById(Long userId, Long requestId) {
        getUser(userId);
        Request request = getRequest(requestId);
        RequestDto requestDto = RequestMapper.mapToRequestDto(request);
        List<RequestItemDto> items = itemRepository.findAllByRequestId(requestId).stream()
                .map(RequestMapper::mapToRequestItemDto)
                .toList();
        requestDto.setItems(items);

        return requestDto;
    }
}
