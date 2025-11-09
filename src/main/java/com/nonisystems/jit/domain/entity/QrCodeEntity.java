package com.nonisystems.jit.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@DynamicUpdate
@Table(name = "j_qr_codes")
public class QrCodeEntity implements Serializable {

    @Id
    @Size(max = 64)
    @Column(name = "id")
    private String id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private UserEntity user;

    @Column(name = "has_url")
    private Boolean hasUrl;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_id", unique = true, nullable = true)
    private UrlEntity url;

    @Size(max = 4096)
    @Column(name = "original_url")
    private String originalUrl;

    @Size(max = 512)
    @Column(name = "icon_path")
    private String iconPath;

    @Size(max = 1)
    @Column(name = "error_correction_level")
    private String errorCorrectionLevel;

    @Column(name = "image_options_hide_background_dots")
    private Boolean imageOptionsHideBackgroundDots;

    @Column(name = "image_options_size", precision = 2, scale = 1)
    private BigDecimal imageOptionsSize;

    @Column(name = "image_options_margin")
    private Integer imageOptionsMargin;

    @Size(max = 15)
    @Column(name = "image_options_cross_origin")
    private String imageOptionsCrossOrigin;

    @Size(max = 7)
    @Column(name = "dots_options_color")
    private String dotsOptionsColor;

    @Size(max = 20)
    @Column(name = "dots_options_type")
    private String dotsOptionsType;

    @Size(max = 7)
    @Column(name = "corners_square_options_color")
    private String cornersSquareOptionsColor;

    @Size(max = 20)
    @Column(name = "corners_square_options_type")
    private String cornersSquareOptionsType;

    @Size(max = 7)
    @Column(name = "corners_dot_options_color")
    private String cornersDotOptionsColor;

    @Size(max = 20)
    @Column(name = "corners_dot_options_type")
    private String cornersDotOptionsType;

    @Size(max = 7)
    @Column(name = "background_options_color")
    private String backgroundOptionsColor;

    @Size(max = 6)
    @Column(name = "background_options_gradient_type")
    private String backgroundOptionsGradientType;

    @Column(name = "background_options_gradient_rotation")
    private Integer backgroundOptionsGradientRotation;

    @Size(max = 7)
    @Column(name = "background_options_gradient_color_from")
    private String backgroundOptionsGradientColorFrom;

    @Size(max = 7)
    @Column(name = "background_options_gradient_color_to")
    private String backgroundOptionsGradientColorTo;

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
        QrCodeEntity that = (QrCodeEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : Objects.hash(user, url);
    }
}
