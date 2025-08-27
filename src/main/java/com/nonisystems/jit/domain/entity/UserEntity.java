package com.nonisystems.jit.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@DynamicUpdate
@Table(name = "j_users")
public class UserEntity implements Serializable {

    @Id
    @Size(max = 64)
    @Column(name = "id")
    private String id;

    @NotNull
    @Size(max = 128)
    @Column(name = "email")
    private String email;

    @NotNull
    @Size(max = 256)
    @Column(name = "password_hash")
    private String passwordHash;

    @CreationTimestamp
    @Column(name = "signup_date")
    private Timestamp signupDate;

    @Column(name = "verified")
    private byte verified;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "j_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();
}
