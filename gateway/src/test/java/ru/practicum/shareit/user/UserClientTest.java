package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserClientTest {

    @Mock
    private RestTemplate restTemplate;

    private UserClient userClient;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        RestTemplateBuilder builder = mock(RestTemplateBuilder.class);
        when(builder.build()).thenReturn(restTemplate);
        when(builder.uriTemplateHandler(any())).thenReturn(builder);
        when(builder.requestFactory(any(Supplier.class))).thenReturn(builder);

        String serverUrl = "http://localhost:8080";
        userClient = new UserClient(serverUrl, builder);

        userDto = new UserDto();
        userDto.setId(1L);
    }

    @Test
    void getBookingByID() {
        when(restTemplate.exchange(
                eq("/1"),
                eq(HttpMethod.GET),
                argThat(entity -> true),
                eq(Object.class)
        )).thenReturn(ResponseEntity.ok().build());
        ResponseEntity<Object> actualResponse = userClient.getUserById(1L);
        assertEquals(ResponseEntity.ok().build(), actualResponse);
    }

}