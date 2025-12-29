package com.nonisystems.jit.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

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
    @Size(max = 64)
    @Column(name = "sub")
    private String sub;

    @NotNull
    @Size(max = 128)
    @Column(name = "email")
    private String email;
//
//    @NotNull
//    @Size(max = 256)
//    @JsonIgnore
//    @Column(name = "password_hash")
//    private String passwordHash;
//
//    @CreationTimestamp
//    @Column(name = "signup_date", updatable = false)
//    private Timestamp signupDate;
//
//    @Column(name = "verified")
//    private byte verified;
//
//    @Column(name = "last_login")
//    private Timestamp lastLogin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "j_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private RoleEntity role;

//    @JsonIgnore
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
//    private List<UrlEntity> urls = new ArrayList<>();

//    @JsonIgnore
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
//    private List<QrCodeEntity> qrCodes = new ArrayList<>();

//    @JsonIgnore
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<TagEntity> tags;
}
