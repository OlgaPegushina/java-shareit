package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.user.model.User;

import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String description;

    @Column(name = "is_available", nullable = false)
    Boolean available;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    @ToString.Exclude
    User owner;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "request_id")
    @ToString.Exclude
    Request request;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        if (item.id != null && id != null) {
            return id.equals(item.id);
        }
        return Objects.equals(description, item.description) &&
               Objects.equals(name, item.name) &&
               Objects.equals(owner, item.owner) &&
               Objects.equals(available, item.available) &&
               Objects.equals(request, item.request);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : Objects.hash(description, name, owner, available, request);
    }
}
