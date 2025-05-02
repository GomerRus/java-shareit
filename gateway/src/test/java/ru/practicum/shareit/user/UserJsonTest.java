package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.user.dto.UserDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JsonTest
public class UserJsonTest {

    @Autowired
    private JacksonTester<UserDto> json;

    @Test
    void testSerializeSuccess() throws Exception {
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setName("USER");
        dto.setEmail("USER@mail.ru");
        String expectedResult = "{\"id\":1,\"name\":\"USER\",\"email\":\"USER@mail.ru\"}";
        assertThat(json.write(dto).getJson()).isEqualTo(expectedResult);
    }

    @Test
    void testSerializeError() throws Exception {
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setName("USER");
        dto.setEmail("USER@mail.ru");
        String expectedResult = "{\"id\":1L,\"name\":\"USER\",\"email\":\"USER@mail.ru\"}";
        assertThat(json.write(dto).getJson()).isNotEqualTo(expectedResult);
    }

    @Test
    void testDeserializeSuccess() throws Exception {
        String content = "{\"id\":1,\"name\":\"USER\",\"email\":\"USER@mail.ru\"}";
        UserDto dto = this.json.parseObject(content);
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("USER");
        assertThat(dto.getEmail()).isEqualTo("USER@mail.ru");
    }

    @Test
    void testDeserializeError() throws Exception {
        String content = "{}";
        UserDto dto = this.json.parseObject(content);
        assertThat(dto).isNotNull();
        assertThat(dto.getEmail()).isNull();
    }

}