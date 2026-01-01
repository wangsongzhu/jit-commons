package com.nonisystems.jit.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QrCode implements Serializable {
    private String id;
    private String name;
    private String userId;

    private Integer width;
    private Integer height;
    private Integer margin;
    private String type;

    private Boolean hasUrl;
    private String urlId;
    private String data;
    private Boolean useShortUrl;
    private String originalUrl;
    private String fullShortUrl;

    private String foregroundColor;
    private String backgroundColor;

    private Boolean useBorder;
    private Integer borderWidth;
    private String borderColor;
    private Integer borderRadius;

    private Boolean useLogo;
    private String logoId;
    private Boolean imageOptionsHideBackgroundDots;
    private BigDecimal imageOptionsSize;
    private Integer imageOptionsMargin;
    private String imageOptionsCrossOrigin;

    private Integer typeNumber;
    private String mode;
    private String errorCorrectionLevel;


    private String dotsOptionsColor;
    private String dotsOptionsType;
    private Boolean dotsOptionsGradientEnabled;
    private String dotsOptionsGradientType;
    private Integer dotsOptionsGradientRotation;
    private String dotsOptionsGradientColorFrom;
    private String dotsOptionsGradientColorTo;

    private String cornersSquareOptionsColor;
    private String cornersSquareOptionsType;
    private Boolean cornersSquareOptionsGradientEnabled;
    private String cornersSquareOptionsGradientType;
    private Integer cornersSquareOptionsGradientRotation;
    private String cornersSquareOptionsGradientColorFrom;
    private String cornersSquareOptionsGradientColorTo;

    private String cornersDotOptionsColor;
    private String cornersDotOptionsType;
    private Boolean cornersDotOptionsGradientEnabled;
    private String cornersDotOptionsGradientType;
    private Integer cornersDotOptionsGradientRotation;
    private String cornersDotOptionsGradientColorFrom;
    private String cornersDotOptionsGradientColorTo;

    private String backgroundOptionsColor;
    private Boolean backgroundOptionsGradientEnabled;
    private String backgroundOptionsGradientType;
    private Integer backgroundOptionsGradientRotation;
    private String backgroundOptionsGradientColorFrom;
    private String backgroundOptionsGradientColorTo;

    private String created;
    private String modified;

    private List<Tag> tags = new ArrayList<>();
}