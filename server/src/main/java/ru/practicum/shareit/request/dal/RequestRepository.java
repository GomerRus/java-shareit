package ru.practicum.shareit.request.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("select r " +
            "from Request r " +
            "where r.requestor = ?1 " +
            "order by r.created desc")
    List<Request> getUserRequests(User requestor);

    List<Request> findAllByRequestorIdNot(Long userId);
}
