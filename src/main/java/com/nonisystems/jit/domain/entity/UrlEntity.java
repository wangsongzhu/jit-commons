package com.nonisystems.jit.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@DynamicUpdate
@Table(name = "j_urls")
@SecondaryTables({
        @SecondaryTable(name = "j_urls_expiration", pkJoinColumns = @PrimaryKeyJoinColumn(name = "url_id", referencedColumnName = "id")),
        @SecondaryTable(name = "j_urls_protection", pkJoinColumns = @PrimaryKeyJoinColumn(name = "url_id", referencedColumnName = "id")),
        @SecondaryTable(name = "j_urls_hidden", pkJoinColumns = @PrimaryKeyJoinColumn(name = "url_id", referencedColumnName = "id"))
})
public class UrlEntity implements Serializable {

    @Id
    @Size(max = 64)
    @Column(name = "id")
    private String id;

    @Column(name = "title")
    private String title;

    @NotNull
    @Size(max = 4096)
    @Column(name = "original_url")
    private String originalUrl;

    @NotNull
    @Column(name = "domain_url_id")
    private String domainUrlId;

    @NotNull
    @Size(max = 10)
    @Column(name = "short_url", unique = true)
    private String shortUrl;

    @NotNull
    @Size(max = 138)
    @Column(name = "full_short_url", unique = true)
    private String fullShortUrl;

    @Column(name = "is_shadow")
    private Boolean isShadow;

    @Column(name = "click_count")
    private Long clickCount;

    @Column(table = "j_urls_expiration", name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(table = "j_urls_expiration", name = "update_time")
    private LocalDateTime expirationUpdateTime;

    @Column(table = "j_urls_protection", name = "is_protected")
    private Boolean isProtected;

    @Column(table = "j_urls_protection", name = "password_hash")
    private String passwordHash;

    @Column(table = "j_urls_protection", name = "update_time")
    private LocalDateTime protectionUpdateTime;

    @Column(table = "j_urls_hidden", name = "is_hidden")
    private Boolean isHidden;

    @Column(table = "j_urls_hidden", name = "update_time")
    private LocalDateTime hiddenUpdateTime;

    /*
    @PrePersist
    public void setExpirationDateIfNull() {
        if (this.expirationDate == null) {
            this.expirationDate = LocalDateTime.of(9999, 12, 31, 23, 59, 59);
        }
    }

    @Column(name = "is_click_limited")
    private Boolean clickLimited;

    @Column(name = "click_limit")
    private Long clickLimit;

    @Column(name = "editable")
    private Boolean editable;

    @Column(name = "is_edited")
    private Boolean edited;
    */

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
    @OneToMany(mappedBy = "url", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClickRecordEntity> clickRecords = new ArrayList<>();

    /**
     * URL Tags information
     */
    @BatchSize(size = 50)
    @OneToMany(mappedBy = "url", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UrlTagEntity> urlTags = new HashSet<>();

    /**
     * QR code
     */
    @OneToOne(mappedBy = "url", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private QrCodeEntity qrCode;

    /**
     * Set QR code
     *
     * @param qrCode QrCodeEntity
     */
    public void setQrCode(QrCodeEntity qrCode) {
        if (this.qrCode != null) {
            this.qrCode.setUrl(null);
        }
        this.qrCode = qrCode;
        if (qrCode != null) {
            qrCode.setUrl(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlEntity that = (UrlEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : Objects.hash(originalUrl, domainUrlId, shortUrl);
    }
}
