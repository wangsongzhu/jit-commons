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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@DynamicUpdate
@Table(name = "j_urls")
public class UrlEntity implements Serializable {

    @Id
    @Size(max = 64)
    @Column(name = "id")
    private String id;

    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "original_url")
    private String originalUrl;

    @NotNull
    @Size(max = 25)
    @Column(name = "short_url", unique = true)
    private String shortUrl;

    @NotNull
    @Size(max = 10)
    @Column(name = "short_part")
    private String shortPart;

    @Column(name = "expiration_date", columnDefinition = "DATETIME  DEFAULT '9999-12-31 23:59:59'")
    private LocalDateTime expirationDate;

    @Column(name = "is_click_limited")
    private Boolean clickLimited;

    @Column(name = "click_limit")
    private Long clickLimit;

    @Column(name = "editable")
    private Boolean editable;

    @Column(name = "is_edited")
    private Boolean edited;

    @Column(name = "is_password_protected")
    private Boolean passwordProtected;

    @Column(name = "show_original_url")
    private Boolean showOriginalUrl;

    @Column(name = "click_count")
    private Long clickCount;

    @Column(name = "has_qr_code")
    private Boolean hasQrCode;

    @CreationTimestamp
    @Column(name = "created")
    private Timestamp created;

    @UpdateTimestamp
    @Column(name = "modified")
    private Timestamp modified;

    /**
     * (FK) user information
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id", updatable = false)
    private UserEntity user;

    /**
     * Click Records information
     */
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "url")
    private List<ClickRecordEntity> clickRecords = new ArrayList<>();

    /**
     * URL Tags information
     */
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "j_url_tag",
            joinColumns = @JoinColumn(name = "url_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<TagEntity> tags;

    /**
     * QR code
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "url")
    private List<QrCodeEntity> qrCodes = new ArrayList<>();
}
