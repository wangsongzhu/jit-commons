package com.nonisystems.jit.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@DynamicUpdate
@Table(name = "j_qr_code_tag")
public class QrCodeTagEntity implements Serializable {

    @Id
    @Size(max = 64)
    @Column(name = "id")
    private String id;

    /**
     * (FK) QRCode information
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "qr_code_id", nullable = false, updatable = false)
    private QrCodeEntity qrCode;

    /**
     * (FK) Tag information
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "tag_id", nullable = false, updatable = false)
    private TagEntity tag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QrCodeTagEntity that = (QrCodeTagEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : Objects.hash(qrCode, tag);
    }
}
