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
public class ClickRecord implements Serializable {

    private Long id;
    private String urlId;
    private String clickTime;
    private String ip;
    private String browser;
    private String browserType;
    private String browserMajorVersion;
    private String deviceType;
    private String platform;
    private String platformVersion;
    private String platformMaker;
    private String renderingEngineMaker;
    private String language;
    private String geolocation;
}
