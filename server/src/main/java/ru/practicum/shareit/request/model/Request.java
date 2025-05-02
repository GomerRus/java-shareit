package ru.practicum.shareit.request.model;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.model.User;
import java.time.LocalDateTime;
@Data
@Builder
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @JoinColumn(name = "requestor_id")
    @ManyToOne
    private User requestor;
    private LocalDateTime created;
}