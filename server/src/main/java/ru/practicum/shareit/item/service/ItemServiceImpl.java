package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dal.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exception.InvalidCommentException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.comments.dal.CommentRepository;
import ru.practicum.shareit.item.comments.dto.CommentDto;
import ru.practicum.shareit.item.comments.dto.CommentMapper;
import ru.practicum.shareit.item.comments.model.Comment;
import ru.practicum.shareit.item.dal.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoOutput;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dal.RequestRepository;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.user.dal.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final RequestRepository requestRepository;

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("USER not found with ID'%d'", userId)));
    }

    private Item getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException(String.format("ITEM not found with ID '%d'.", itemId)));
    }

    @Override
    public ItemDto createItem(ItemDto itemDto, Long userId) {
        User user = getUser(userId);
        Item item = ItemMapper.mapToItem(itemDto);
        item.setOwner(user);
        Long requestId = itemDto.getRequestId();
        if (requestId != null) {
            Request request = requestRepository.findById(requestId)
                    .orElseThrow(() -> new NotFoundException(String.format("Request with ID'%d' not found.", requestId)));
            item.setRequest(request);
        }
        return ItemMapper.mapToItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDtoOutput getItemById(Long itemId, Long userId) {
        getUser(userId);
        Item item = getItem(itemId);
        ItemDtoOutput itemDtoOutput = ItemMapper.mapToItemDtoOutput(item);

        if (userId.equals(item.getOwner().getId())) {
            Sort prevSort = Sort.by("end").descending();
            Booking prevBooking = bookingRepository.findFirstByItemIdAndEndIsBeforeAndStatus(itemDtoOutput.getId(),
                    LocalDateTime.now(), BookingStatus.APPROVED, prevSort);

            if (prevBooking != null) {
                itemDtoOutput.setLastBooking(prevBooking.getEnd());
            }
            Sort nextSort = Sort.by("start").descending();
            Booking nextBooking = bookingRepository.findFirstByItemIdAndStartIsAfterAndStatus(itemId,
                    LocalDateTime.now(), BookingStatus.APPROVED, nextSort);
            if (nextBooking != null) {
                itemDtoOutput.setNextBooking(nextBooking.getStart());
            }
        }
        List<Comment> comments = commentRepository.findAllByItemId(item.getId());
        if (comments != null && !comments.isEmpty()) {
            itemDtoOutput.setComments(comments);
        }
        return itemDtoOutput;
    }

    @Override
    public List<ItemDtoOutput> getAllItemByUser(Long userId) {
        getUser(userId);
        List<ItemDtoOutput> items = itemRepository.findAllByOwnerId(userId).stream()
                .map(ItemMapper::mapToItemDtoOutput)
                .toList();

        List<Long> itemIds = items.stream().map(ItemDtoOutput::getId).toList();
        Sort prevSort = Sort.by("end").descending();
        List<Booking> prevBookingList = bookingRepository.findAllByItemIdInAndEndIsBeforeAndStatus(itemIds, LocalDateTime.now(),
                BookingStatus.APPROVED, prevSort);

        Sort nextSort = Sort.by("start").ascending();
        List<Booking> nextBookingList = bookingRepository.findAllByItemIdInAndStartIsAfterAndStatus(itemIds, LocalDateTime.now(),
                BookingStatus.APPROVED, nextSort);
        List<Comment> commentList = commentRepository.findAllByItemIdIn(itemIds);

        for (ItemDtoOutput itemDtoOutput : items) {
            Optional<Booking> prevBooking = prevBookingList.stream()
                    .filter(booking -> itemDtoOutput.getId().equals(booking.getItem().getId())).findAny();
            prevBooking.ifPresent(booking -> itemDtoOutput.setLastBooking(booking.getEnd()));

            Optional<Booking> nextBooking = nextBookingList.stream()
                    .filter(booking -> itemDtoOutput.getId().equals(booking.getItem().getId())).findFirst();
            nextBooking.ifPresent(booking -> itemDtoOutput.setNextBooking(booking.getStart()));

            List<Comment> comments = commentList.stream()
                    .filter(comment -> itemDtoOutput.getId().equals(comment.getItem().getId()))
                    .toList();
            if (!comments.isEmpty()) {
                itemDtoOutput.setComments(comments);
            }
        }
        return items;
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto, Long itemId, Long userId) {
        User user = getUser(userId);
        Item item = getItem(itemId);

        if (!item.getOwner().equals(user)) {
            throw new ValidationException("Don't have the rights to update the ITEM");
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }

        return ItemMapper.mapToItemDto(itemRepository.save(item));
    }

    @Override
    public List<ItemDto> searchItem(Long userId, String text) {
        getUser(userId);
        if (text.isEmpty() || text.isBlank()) {
            return List.of();
        }
        return itemRepository.findByItemsByNameOrDescription(text).stream()
                .map(ItemMapper::mapToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto addComment(Long userId, Long itemId, CommentDto commentDto) {
        User user = getUser(userId);
        Item item = getItem(itemId);
        Booking booking = bookingRepository.findAllByItemAndBookerPast(user, item, LocalDateTime.now()).stream()
                .findFirst()
                .orElseThrow(() -> new ValidationException(
                        String.format("User with ID'%d' didn't take the ITEM with ID'%d' to rent", userId, itemId)));

        if (!booking.getStatus().equals(BookingStatus.APPROVED) || booking.getEnd().isAfter(LocalDateTime.now())) {
            throw new ValidationException(String.format("User with ID'%d' didn't take the ITEM with ID'%d' to rent", userId, itemId));
        }

        Comment comment = CommentMapper.mapToComment(commentDto, user, item);
        comment.setItem(item);
        comment.setAuthor(user);
        comment.setCreated(LocalDateTime.now());
        return CommentMapper.mapToCommentDto(commentRepository.save(comment));
    }
}