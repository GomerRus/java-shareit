package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.request.dto.RequestDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JsonTest
public class RequestJsonTest {

    @Autowired
    private JacksonTester<RequestDto> json;

    @Test
    void testSerializeSuccess() throws Exception {
        RequestDto dto = new RequestDto();
        dto.setId(1L);
        dto.setDescription("TEXT");
        String expectedResult = "{\"id\":1,\"requestorName\":null,\"created\":null,\"description\":\"TEXT\"}";
        assertThat(json.write(dto).getJson()).isEqualTo(expectedResult);
    }

    @Test
    void testDeserializeSuccess() throws Exception {
        String content = "{\"id\":1,\"description\":\"TEXT\"}";
        RequestDto dto = this.json.parseObject(content);
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getDescription()).isEqualTo("TEXT");
    }

}