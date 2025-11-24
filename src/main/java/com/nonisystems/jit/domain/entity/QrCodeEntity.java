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

    @NotNull
    @Column(name = "width")
    private Integer width;

    @NotNull
    @Column(name = "height")
    private Integer height;

    @NotNull
    @Column(name = "margin")
    private Integer margin;

    @NotNull
    @Size(max = 10)
    @Column(name = "type")
    private String type;

    @NotNull
    @Column(name = "has_url")
    private Boolean hasUrl;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_id", unique = true, nullable = true)
    private UrlEntity url;

    @NotNull
    @Size(max = 4096)
    @Column(name = "data")
    private String data;

    @Size(max = 7)
    @Column(name = "foreground_color")
    private String foregroundColor;

    @Size(max = 7)
    @Column(name = "background_color")
    private String backgroundColor;

    @Column(name = "use_border")
    private Boolean useBorder;

    @Column(name = "border_width")
    private Integer borderWidth;

    @Size(max = 7)
    @Column(name = "border_color")
    private String borderColor;

    @Column(name = "border_radius")
    private Integer borderRadius;

    @NotNull
    @Column(name = "use_logo")
    private Boolean useLogo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logo_id", updatable = false)
    private LogoEntity logo;

    @Column(name = "image_options_hide_background_dots")
    private Boolean imageOptionsHideBackgroundDots;

    @Column(name = "image_options_size", precision = 2, scale = 1)
    private BigDecimal imageOptionsSize;

    @Column(name = "image_options_margin")
    private Integer imageOptionsMargin;

    @Size(max = 15)
    @Column(name = "image_options_cross_origin")
    private String imageOptionsCrossOrigin;

    @Column(name = "type_number")
    private Integer typeNumber;

    @Size(max = 12)
    @Column(name = "mode")
    private String mode;

    @Size(max = 1)
    @Column(name = "error_correction_level")
    private String errorCorrectionLevel;

    @Size(max = 7)
    @Column(name = "dots_options_color")
    private String dotsOptionsColor;

    @Size(max = 20)
    @Column(name = "dots_options_type")
    private String dotsOptionsType;

    @NotNull
    @Column(name = "dots_options_gradient_enabled")
    private Boolean dotsOptionsGradientEnabled;

    @Size(max = 6)
    @Column(name = "dots_options_gradient_type")
    private String dotsOptionsGradientType;

    @Column(name = "dots_options_gradient_rotation")
    private Integer dotsOptionsGradientRotation;

    @Size(max = 7)
    @Column(name = "dots_options_gradient_color_from")
    private String dotsOptionsGradientColorFrom;

    @Size(max = 7)
    @Column(name = "dots_options_gradient_color_to")
    private String dotsOptionsGradientColorTo;

    @Size(max = 7)
    @Column(name = "corners_square_options_color")
    private String cornersSquareOptionsColor;

    @Size(max = 20)
    @Column(name = "corners_square_options_type")
    private String cornersSquareOptionsType;

    @NotNull
    @Column(name = "corners_square_options_gradient_enabled")
    private Boolean cornersSquareOptionsGradientEnabled;

    @Size(max = 6)
    @Column(name = "corners_square_options_gradient_type")
    private String cornersSquareOptionsGradientType;

    @Column(name = "corners_square_options_gradient_rotation")
    private Integer cornersSquareOptionsGradientRotation;

    @Size(max = 7)
    @Column(name = "corners_square_options_gradient_color_from")
    private String cornersSquareOptionsGradientColorFrom;

    @Size(max = 7)
    @Column(name = "corners_square_options_gradient_color_to")
    private String cornersSquareOptionsGradientColorTo;

    @Size(max = 7)
    @Column(name = "corners_dot_options_color")
    private String cornersDotOptionsColor;

    @Size(max = 20)
    @Column(name = "corners_dot_options_type")
    private String cornersDotOptionsType;

    @NotNull
    @Column(name = "corners_dot_options_gradient_enabled")
    private Boolean cornersDotOptionsGradientEnabled;

    @Size(max = 6)
    @Column(name = "corners_dot_options_gradient_type")
    private String cornersDotOptionsGradientType;

    @Column(name = "corners_dot_options_gradient_rotation")
    private Integer cornersDotOptionsGradientRotation;

    @Size(max = 7)
    @Column(name = "corners_dot_options_gradient_color_from")
    private String cornersDotOptionsGradientColorFrom;

    @Size(max = 7)
    @Column(name = "corners_dot_options_gradient_color_to")
    private String cornersDotOptionsGradientColorTo;

    @Size(max = 7)
    @Column(name = "background_options_color")
    private String backgroundOptionsColor;

    @NotNull
    @Column(name = "background_options_gradient_enabled")
    private Boolean backgroundOptionsGradientEnabled;

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
