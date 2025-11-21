package com.nonisystems.jit.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Logo implements Serializable {
    private String id;
    private String userId;
    private byte logoOption;
    private String name;
    private String url;
    private String fileName;
    private String fileKey;
    private String created;
    private String modified;
}