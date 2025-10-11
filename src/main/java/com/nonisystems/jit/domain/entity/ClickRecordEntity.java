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

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@DynamicUpdate
@Table(name = "j_click_records")
public class ClickRecordEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "click_time")
    private Timestamp clickTime;

    @Size(max = 45)
    @Column(name = "ip")
    private String ip;

    @Size(max = 256)
    @Column(name = "browser")
    private String browser;

    @Size(max = 256)
    @Column(name = "browser_type")
    private String browserType;

    @Size(max = 256)
    @Column(name = "browser_major_version")
    private String browserMajorVersion;

    @Size(max = 256)
    @Column(name = "device_type")
    private String deviceType;

    @Size(max = 256)
    @Column(name = "platform")
    private String platform;

    @Size(max = 256)
    @Column(name = "platform_version")
    private String platformVersion;

    @Size(max = 256)
    @Column(name = "platform_maker")
    private String platformMaker;

    @Size(max = 256)
    @Column(name = "rendering_engine_maker")
    private String renderingEngineMaker;

    @Size(max = 256)
    @Column(name = "language")
    private String language;

    @Size(max = 512)
    @Column(name = "geolocation")
    private String geolocation;

    /**
     * (FK) URL information
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "url_id", updatable = false)
    private UrlEntity url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClickRecordEntity that = (ClickRecordEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : Objects.hash(ip, clickTime);
    }
}
