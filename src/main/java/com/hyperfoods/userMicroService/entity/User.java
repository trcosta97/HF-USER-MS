package com.hyperfoods.userMicroService.entity;

import com.hyperfoods.userMicroService.dto.CreateUserDTO;
import com.hyperfoods.userMicroService.dto.UpdateUserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tab_user")
@Entity(name = "user")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private long id;
    private String name;
    private String email;
    private String password;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Address> addresses = new ArrayList<>();
    @Column(name = "is_active")
    private boolean active = true;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created;

    public User(CreateUserDTO data) {
        this.name = data.name();
        this.email = data.email();
        this.password = data.password();
        this.active = true;
        this.created = LocalDateTime.now();
    }

    public User(UpdateUserDTO data) {
        this.name = data.name();
        this.email = data.email();
        this.password = data.password();
    }
}
