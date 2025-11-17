package com.nonisystems.jit.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@DynamicUpdate
@Table(name = "j_logo")
public class LogoEntity implements Serializable {

    @Id
    @Size(max = 64)
    @Column(name = "id")
    private String id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private UserEntity user;

    @NotNull
    @Column(name = "logo_option")
    private byte logoOption;

    @Size(max = 4096)
    @Column(name = "url")
    private String url;

    @Size(max = 64)
    @Column(name = "file_key")
    private String fileKey;

    @CreationTimestamp
    @Column(name = "created")
    private Timestamp created;

    @UpdateTimestamp
    @Column(name = "modified")
    private Timestamp modified;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogoEntity that = (LogoEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : Objects.hash(user);
    }
}
