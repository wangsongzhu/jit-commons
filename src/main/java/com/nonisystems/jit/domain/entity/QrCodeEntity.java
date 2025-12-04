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
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @NotNull
    @Size(max = 64)
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private UserEntity user;

    @Column(name = "width")
    private Integer width = 300;

    @Column(name = "height")
    private Integer height = 300;

    @Column(name = "margin")
    private Integer margin = 0;

    @Size(max = 10)
    @Column(name = "type")
    private String type = "svg";

    @NotNull
    @Column(name = "has_url")
    private Boolean hasUrl = false;

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
    private String foregroundColor = "#000000";

    @Size(max = 7)
    @Column(name = "background_color")
    private String backgroundColor = "#FFFFFF";

    @NotNull
    @Column(name = "use_border")
    private Boolean useBorder = false;

    @Column(name = "border_width")
    private Integer borderWidth = 0;

    @Size(max = 7)
    @Column(name = "border_color")
    private String borderColor = "#000000";

    @Column(name = "border_radius")
    private Integer borderRadius = 0;

    @NotNull
    @Column(name = "use_logo")
    private Boolean useLogo = false;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logo_id", updatable = false)
    private LogoEntity logo;

    @Column(name = "image_options_hide_background_dots")
    private Boolean imageOptionsHideBackgroundDots = true;

    @Column(name = "image_options_size", precision = 2, scale = 1)
    private BigDecimal imageOptionsSize = new BigDecimal("0.4");

    @Column(name = "image_options_margin")
    private Integer imageOptionsMargin = 10;

    @Size(max = 15)
    @Column(name = "image_options_cross_origin")
    private String imageOptionsCrossOrigin = "anonymous";

    @Column(name = "type_number")
    private Integer typeNumber = 0;

    @Size(max = 12)
    @Column(name = "mode")
    private String mode = "Byte";

    @Size(max = 1)
    @Column(name = "error_correction_level")
    private String errorCorrectionLevel = "H";

    @Size(max = 7)
    @Column(name = "dots_options_color")
    private String dotsOptionsColor = "#000000";

    @Size(max = 20)
    @Column(name = "dots_options_type")
    private String dotsOptionsType = "square";

    @NotNull
    @Column(name = "dots_options_gradient_enabled")
    private Boolean dotsOptionsGradientEnabled = false;

    @Size(max = 6)
    @Column(name = "dots_options_gradient_type")
    private String dotsOptionsGradientType = "square";

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
    private String cornersSquareOptionsColor = "#000000";

    @Size(max = 20)
    @Column(name = "corners_square_options_type")
    private String cornersSquareOptionsType = "square";

    @NotNull
    @Column(name = "corners_square_options_gradient_enabled")
    private Boolean cornersSquareOptionsGradientEnabled = false;

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
    private String cornersDotOptionsColor = "#000000";

    @Size(max = 20)
    @Column(name = "corners_dot_options_type")
    private String cornersDotOptionsType = "square";

    @NotNull
    @Column(name = "corners_dot_options_gradient_enabled")
    private Boolean cornersDotOptionsGradientEnabled = false;

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
    private String backgroundOptionsColor = "#FFFFFF";

    @NotNull
    @Column(name = "background_options_gradient_enabled")
    private Boolean backgroundOptionsGradientEnabled = false;

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

    /**
     * QRCode Tags information
     */
    @OneToMany(mappedBy = "qrCode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<QrCodeTagEntity> qrCodeTags = new HashSet<>();

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
