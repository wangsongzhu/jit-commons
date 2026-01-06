package com.nonisystems.jit.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Url implements Serializable {

    private String id;
    private String userId;
    private String title;
    private String originalUrl;
    private String originalDomain;
    private String domainUrlId;
    private String domainUrl;
    private String shortUrl;
    private String fullShortUrl;
    private Boolean isShadow;
    private Long clickCount;
    private String created;
    private String modified;

    /** Features */
    private String expirationDate;
    private String expirationUpdateTime;

    private Boolean isProtected;
    private String password;
    private String protectionUpdateTime;

    private Boolean isHidden;
    private String hiddenUpdateTime;

    private List<Tag> tags = new ArrayList<>();

    private Boolean hasQrCode;
    private QrCode qrCode;
}
