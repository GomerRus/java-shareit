package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.ShareItServer;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.dto.UserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = ShareItServer.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RequestIntegrationTest {

    @Autowired
    private UserController userController;

    @Autowired
    private ItemController itemController;

    @Autowired
    private RequestController requestController;

    @Test
    void createRequest() {
        UserDto user = new UserDto();
        user.setName("USER");
        user.setEmail("USER@mail.ru");
        user = userController.createUser(user);

        RequestDto request = new RequestDto();
        request.setDescription("TEXT");
        request = requestController.createRequest(user.getId(), request);

        assertNotNull(request.getId(), "Ошибка проверки");
        assertEquals("TEXT", request.getDescription(), "Ошибка проверки");
    }

    @Test
    void getRequest() {
        UserDto user1 = new UserDto();
        user1.setName("USER-1");
        user1.setEmail("USER-1@mail.ru");
        user1 = userController.createUser(user1);

        UserDto user2 = new UserDto();
        user2.setName("USER-2");
        user2.setEmail("USER-2@mail.ru");
        user2 = userController.createUser(user2);

        RequestDto request = new RequestDto();
        request.setDescription("TEXT");
        request = requestController.createRequest(user1.getId(), request);

        ItemDto item = new ItemDto();
        item.setName("ITEM");
        item.setDescription("TEXT");
        item.setAvailable(true);
        item.setRequestId(request.getId());
        itemController.createItem(item, user2.getId());

        RequestDto r2 = requestController.getRequestById(user1.getId(), request.getId());
        assertNotNull(r2, "Ошибка проверки");
        assertEquals(request.getId(), r2.getId(), "Ошибка проверки");
        assertEquals(1, r2.getItems().size(), "Ошибка проверки");
    }

}