package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.ShareItServer;
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.user.dto.UserDto;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ShareItServer.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserIntegrationTest {

    @Autowired
    private UserController userController;

    @Autowired
    private ItemController itemController;

    @Autowired
    private BookingController bookingController;

    @Test
    void createUser() {
        UserDto user = new UserDto();
        user.setName("USER");
        user.setEmail("USER@mail.ru");
        user = userController.createUser(user);

        assertNotNull(user.getId());
    }

    @Test
    void getUser() {
        UserDto user1 = new UserDto();
        user1.setName("USER");
        user1.setEmail("USER@mail.ru");
        user1 = userController.createUser(user1);

        UserDto user2 = userController.getUserById(user1.getId());
        assertEquals(user1, user2);
    }

    @Test
    void updatesUser() {
        UserDto user1 = new UserDto();
        user1.setName("USER-1");
        user1.setEmail("USER-1@mail.ru");
        user1 = userController.createUser(user1);

        user1.setName("USER-2");
        user1.setEmail("USER-2@mail.ru");
        UserDto user2 = userController.updateUser(user1, user1.getId());

        assertEquals(user1, user2);
    }

    @Test
    void deletesUser() {
        UserDto user = new UserDto();
        user.setName("USER");
        user.setEmail("USER@mail.ru");
        user = userController.createUser(user);

        Long id = user.getId();
        userController.removeUserById(id);

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> userController.getUserById(id), "Ошибка проверки");
        assertFalse(thrown.getMessage().contains("Пользователь с идентификатором " + id + " не найден"), "Ошибка проверки");
    }

}
