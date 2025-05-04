package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.item.dto.ItemDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JsonTest
public class ItemJsonTest {

    @Autowired
    private JacksonTester<ItemDto> json;

    @Test
    void testSerializeSuccess() throws Exception {
        ItemDto dto = new ItemDto();
        dto.setId(1L);
        dto.setName("ITEM");
        dto.setDescription("TEXT");
        dto.setAvailable(true);
        String expectedResult = "{\"id\":1,\"requestId\":null,\"name\":\"ITEM\",\"description\":\"TEXT\",\"available\":true}";
        assertThat(json.write(dto).getJson()).isEqualTo(expectedResult);
    }

    @Test
    void testDeserializeSuccess() throws Exception {
        String content = "{\"id\":1,\"name\":\"ITEM\",\"description\":\"TEXT\"}";
        ItemDto dto = this.json.parseObject(content);
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("ITEM");
        assertThat(dto.getDescription()).isEqualTo("TEXT");
    }

}
