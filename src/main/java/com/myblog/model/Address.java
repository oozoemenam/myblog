package com.myblog.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@ToString(exclude = {"user"})
@EqualsAndHashCode(exclude = {"user"})
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    private User user;
}
