package com.flight.core.domain;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "user",schema="public")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "user_name")
    private String username;

    @Column(nullable = false,name = "password")
    private String password;
    @Column(nullable = false,name = "role_id")
    private int roleId;
}
