package ru.practicum.shareit.item.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select i " +
            "from Item i " +
            "where i.available = true " +
            "and ( " +
            "lower(i.name) like lower(concat('%', ?1, '%')) " +
            "or lower(i.description) like lower(concat('%', ?1, '%')) " +
            ")")
    List<Item> findByItemsByNameOrDescription(String text);

    List<Item> findAllByOwnerId(Long ownerId);

    List<Item> findAllByRequestId(Long requestId);
}
