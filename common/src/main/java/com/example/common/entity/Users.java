package com.example.common.entity;

import lombok.*;

import javax.persistence.*;

@Table(name="users")
@Entity
@Setter
@Getter
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String login;
    @Column(name = "phoneNumber", unique = true)
    private String phoneNumber;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "role", nullable = false)
    private Role role;

    public Users(String login, String phoneNumber, String password, Role role) {
        this.login = login;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
    }
}
