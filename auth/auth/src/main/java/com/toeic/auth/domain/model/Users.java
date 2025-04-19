package com.toeic.auth.domain.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.toeic.auth.domain.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    @Column(length = 30, nullable = false, unique = true)
    private String username;

    @Column(nullable = true)
    private String password;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 30)
    private String firstname;

    @Column(length = 30)
    private String lastname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp created_at;

    @Column(name = "keycloak_id", nullable = true)
    private String keycloakId;

    public Users(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public Users(String username, String email, Role role,String keycloakId) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.keycloakId= keycloakId;
    }

    public Users(String username, String email, String firstName, String lastName, Role role,
            String keycloakId) {
        this.username = username;
        this.email = email;
        this.firstname = firstName;
        this.lastname = lastName;
        this.role = role;
        this.keycloakId = keycloakId;
    }
    
}
