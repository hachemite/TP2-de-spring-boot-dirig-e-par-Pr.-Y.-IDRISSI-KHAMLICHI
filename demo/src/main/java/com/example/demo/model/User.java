package com.example.demo.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité User (Utilisateur) based on TP2.
 * This entity will be used by Spring Security.
 */
@Entity
@Table(name = "users") // 'user' is often a reserved keyword in SQL
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private boolean enabled = true;

    // Many Users can have Many Roles
    @ManyToMany(fetch = FetchType.EAGER) // EAGER fetch is often best for security roles
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }
}