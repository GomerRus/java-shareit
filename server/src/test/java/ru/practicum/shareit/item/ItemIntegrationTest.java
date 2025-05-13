package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.ShareItServer;
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoOutput;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.dto.UserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = ShareItServer.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ItemIntegrationTest {

    @Autowired
    private UserController userController;

    @Autowired
    private ItemController itemController;

    @Autowired
    private BookingController bookingController;

    @Test
    void createItem() {
        UserDto user = new UserDto();
        user.setName("USER");
        user.setEmail("USER@mail.ru");
        user = userController.createUser(user);

        ItemDto item = new ItemDto();
        item.setName("ITEM");
        item.setDescription("TEXT");
        item.setAvailable(true);
        item = itemController.createItem(item, user.getId());

        assertNotNull(item.getId(), "Ошибка проверки");
    }

    @Test
    void getItem() {
        UserDto user = new UserDto();
        user.setName("USER");
        user.setEmail("USER@mail.ru");
        user = userController.createUser(user);

        ItemDto item = new ItemDto();
        item.setName("ITEM");
        item.setDescription("TEXT");
        item.setAvailable(true);
        item = itemController.createItem(item, user.getId());

        ItemDtoOutput itemDto = itemController.getItemById(user.getId(), item.getId());
        assertEquals(item.getId(), itemDto.getId(), "Ошибка проверки");
    }

}