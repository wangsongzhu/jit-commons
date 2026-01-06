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
public class Tag implements Serializable {

    private String id;
    private String name;
    private int taggedUrlCount;
    private int taggedQrCodeCount;
    private String created;
    private String modified;
}
