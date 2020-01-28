package com.cluo.shopping.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfo {

    private Long productInfoId;

    private Long storeInfoId;

    private String productName;

    private String spec;

    private String regionCode;

    private BigDecimal price;

    private String imageUrl;

    private String description;

    private String placeOfOrigin;
}
