package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    @Mock
    private ItemClient itemClient;

    @InjectMocks
    private ItemController itemController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void createItem() throws Exception {
        ItemDto dto = new ItemDto();
        dto.setName("ITEM");
        dto.setDescription("TEXT");
        dto.setAvailable(true);

        when(itemClient.createItem(anyLong(), any(ItemDto.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(
                post("/items")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        ).andExpect(status().isOk());
    }

    @Test
    void getItem() throws Exception {
        mockMvc.perform(
                get("/items/1")
                        .header("X-Sharer-User-Id", 1L)
        ).andExpect(status().isOk());
    }

    @Test
    void getItems() throws Exception {
        mockMvc.perform(
                get("/items")
                        .header("X-Sharer-User-Id", 1L)
        ).andExpect(status().isOk());
    }

    @Test
    void getSearch() throws Exception {
        mockMvc.perform(
                get("/items/search")
                        .header("X-Sharer-User-Id", 1L)
                        .param("text", "searchText")
        ).andExpect(status().isOk());
    }

    @Test
    void addComment() throws Exception {
        CommentDto dto = new CommentDto();
        dto.setText("COMMENT");

        when(itemClient.addComment(anyLong(), anyLong(), any(CommentDto.class)))
                .thenReturn(null);

        mockMvc.perform(
                post("/items/1/comment")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        ).andExpect(status().isOk());
    }

}