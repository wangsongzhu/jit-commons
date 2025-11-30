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
    private String expirationDate;
    private Boolean clickLimited;
    private Long clickLimit;
    private Boolean editable;
    private Boolean edited;
    private Boolean passwordProtected;
    private Boolean showOriginalUrl;
    private Long clickCount;
    private Boolean hasQrCode;
    private String created;
    private String modified;
    private List<Tag> tags = new ArrayList<>();
    private QrCode qrCode;
}
