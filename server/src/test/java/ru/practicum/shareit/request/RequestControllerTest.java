package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.exception.errorHandler.ErrorHandler;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RequestControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Mock
    private RequestService itemRequestService;

    @Mock
    private UserService userService;

    @InjectMocks
    private RequestController itemRequestController;

    private RequestDto requestDto;
    private ItemDto itemDto;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(itemRequestController)
                .setControllerAdvice(new ErrorHandler())
                .build();

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("USER");
        userDto.setEmail("USER@email.ru");

        requestDto = new RequestDto();
        requestDto.setId(1L);
        requestDto.setDescription("TEXT");
        requestDto.setCreated(LocalDateTime.now());

        itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("ITEM");
        itemDto.setDescription("DESCRIPTION");
        itemDto.setAvailable(true);
        itemDto.setRequestId(1L);
    }

    @Test
    void createNewRequest() throws Exception {
        when(itemRequestService.createRequest(anyLong(), any(RequestDto.class)))
                .thenReturn(requestDto);

        mockMvc.perform(post("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto))
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(requestDto.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(requestDto.getDescription())));
    }

    @Test
    void findAllUserRequests() throws Exception {
        when(itemRequestService.getUserRequests(anyLong()))
                .thenReturn(List.of(requestDto));
        when(userService.getUserById(anyLong())).thenReturn(userDto);

        mockMvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk());
    }

}