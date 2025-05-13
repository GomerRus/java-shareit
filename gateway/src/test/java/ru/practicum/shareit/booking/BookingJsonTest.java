package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JsonTest
public class BookingJsonTest {

    @Autowired
    private JacksonTester<BookingDto> json;

    @Test
    void testSerializeSuccess() throws Exception {
        BookingDto dto = new BookingDto();
        dto.setStart(LocalDateTime.parse("2025-04-28T00:00:00"));
        dto.setEnd(LocalDateTime.parse("2025-04-30T23:59:59"));
        dto.setItemId(1L);
        String expectedResult = "{\"itemId\":1,\"start\":\"2025-04-28T00:00:00\",\"end\":\"2025-04-30T23:59:59\"}";
        assertThat(json.write(dto).getJson()).isEqualTo(expectedResult);
    }

    @Test
    void testDeserializeSuccess() throws Exception {
        String content = "{\"itemId\":1,\"start\":\"2025-04-28T00:00:00\",\"end\":\"2025-04-30T23:59:59\"}";
        BookingDto dto = this.json.parseObject(content);
        assertThat(dto.getItemId()).isEqualTo(1L);
        assertThat(dto.getStart()).isEqualTo(LocalDateTime.parse("2025-04-28T00:00:00"));
        assertThat(dto.getEnd()).isEqualTo(LocalDateTime.parse("2025-04-30T23:59:59"));
    }

}