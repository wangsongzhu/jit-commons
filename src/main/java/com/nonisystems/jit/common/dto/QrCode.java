package com.nonisystems.jit.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QrCode implements Serializable {
    private Long id;
    private String userId;
    private String urlId;
    private String fileName;
    private String filePath;
    private String fileType;
    private Integer width;
    private Integer height;
    private String iconPath;
    private Timestamp created;
    private Timestamp modified;
}