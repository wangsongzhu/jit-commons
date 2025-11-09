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
import java.util.*;

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
    @Size(max = 4096)
    @Column(name = "original_url")
    private String originalUrl;

    @NotNull
    @Column(name = "domain_url_id")
    private Long domainUrlId;

    @NotNull
    @Size(max = 10)
    @Column(name = "short_url", unique = true)
    private String shortUrl;

    @NotNull
    @Size(max = 138)
    @Column(name = "full_short_url", unique = true)
    private String fullShortUrl;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

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
    @OneToMany(mappedBy = "url", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UrlTagEntity> urlTags = new HashSet<>();

    /**
     * QR code
     */
    @OneToOne(mappedBy = "url", fetch = FetchType.LAZY)
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
