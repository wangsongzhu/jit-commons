package com.nonisystems.jit.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QrCode implements Serializable {
    private String id;
    private String userId;
    private Boolean hasUrl;
    private String urlId;
    private String originalUrl;
    private String iconPath;
    private String errorCorrectionLevel;
    private Boolean imageOptionsHideBackgroundDots;
    private BigDecimal imageOptionsSize;
    private Integer imageOptionsMargin;
    private String imageOptionsCrossOrigin;
    private String dotsOptionsColor;
    private String dotsOptionsType;
    private String cornersSquareOptionsColor;
    private String cornersSquareOptionsType;
    private String cornersDotOptionsColor;
    private String cornersDotOptionsType;
    private String backgroundOptionsColor;
    private String backgroundOptionsGradientType;
    private Integer backgroundOptionsGradientRotation;
    private String backgroundOptionsGradientColorFrom;
    private String backgroundOptionsGradientColorTo;
    private String created;
    private String modified;
}