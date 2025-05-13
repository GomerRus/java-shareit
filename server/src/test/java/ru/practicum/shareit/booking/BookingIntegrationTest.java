package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.ShareItServer;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoOutput;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ShareItServer.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookingIntegrationTest {

    @Autowired
    private UserController userController;

    @Autowired
    private ItemController itemController;

    @Autowired
    private BookingController bookingController;

    @Test
    void getBooking() {
        UserDto user1 = new UserDto();
        user1.setName("USER-1");

        user1.setEmail("USER-1@mail.ru");
        user1 = userController.createUser(user1);

        UserDto user2 = new UserDto();
        user2.setName("USER-2");
        user2.setEmail("USER-2@mail.ru");
        user2 = userController.createUser(user2);

        ItemDto itemDto = new ItemDto();
        itemDto.setName("ITEM");
        itemDto.setDescription("TEXT");
        itemDto.setAvailable(true);
        itemDto = itemController.createItem(itemDto, user1.getId());

        BookingDtoOutput bookingDtoOutput = new BookingDtoOutput();
        bookingDtoOutput.setItemId(itemDto.getId());
        bookingDtoOutput.setStart(LocalDateTime.now());
        bookingDtoOutput.setEnd(LocalDateTime.now().plusDays(1));

        BookingDto b1 = bookingController.createBooking(user2.getId(), bookingDtoOutput);
        BookingDto b2 = bookingController.getBookingById(user2.getId(), b1.getId());
        assertEquals(b1.getId(), b2.getId(), "Ошибка проверки");
    }

    @Test
    void createBooking() {

        UserDto user1 = new UserDto();
        user1.setName("USER-1");
        user1.setEmail("USER-1@mail.ru");
        user1 = userController.createUser(user1);

        UserDto user2 = new UserDto();
        user2.setName("USER-2");
        user2.setEmail("USER-2@mail.ru");
        user2 = userController.createUser(user2);

        ItemDto itemDto = new ItemDto();
        itemDto.setName("ITEM");
        itemDto.setDescription("TEXT");
        itemDto.setAvailable(true);
        itemDto = itemController.createItem(itemDto, user1.getId());

        BookingDtoOutput bookingDtoOutput = new BookingDtoOutput();
        bookingDtoOutput.setItemId(itemDto.getId());
        bookingDtoOutput.setStart(LocalDateTime.now());
        bookingDtoOutput.setEnd(LocalDateTime.now().plusDays(1));

        BookingDto bookingDto = bookingController.createBooking(user2.getId(), bookingDtoOutput);
        assertEquals(bookingDtoOutput.getItemId(), bookingDto.getItem().getId(), "Ошибка проверки");
        assertEquals(user2.getId(), bookingDto.getBooker().getId(), "Ошибка проверки");
    }


    @Test
    void updateBooking() {
        UserDto user1 = new UserDto();
        user1.setName("USER-1");
        user1.setEmail("USER-1@mail.ru");
        user1 = userController.createUser(user1);

        UserDto user2 = new UserDto();
        user2.setName("USER-2");
        user2.setEmail("USER-2@mail.ru");
        user2 = userController.createUser(user2);

        ItemDto itemDto = new ItemDto();
        itemDto.setName("ITEM");
        itemDto.setDescription("TEXT");
        itemDto.setAvailable(true);
        itemDto = itemController.createItem(itemDto, user1.getId());

        BookingDtoOutput bookingDtoOutput = new BookingDtoOutput();
        bookingDtoOutput.setItemId(itemDto.getId());
        bookingDtoOutput.setStart(LocalDateTime.now());
        bookingDtoOutput.setEnd(LocalDateTime.now().plusDays(1));

        BookingDto b1 = bookingController.createBooking(user2.getId(), bookingDtoOutput);
        BookingDto b2 = bookingController.updateBooking(user1.getId(), b1.getId(), true);
        assertEquals(BookingStatus.APPROVED, b2.getStatus(), "Ошибка проверки");
    }

}