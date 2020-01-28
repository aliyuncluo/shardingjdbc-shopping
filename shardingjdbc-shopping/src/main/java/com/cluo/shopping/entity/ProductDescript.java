package com.cluo.shopping.entity;

import lombok.Data;

@Data
public class ProductDescript {

    private Long id;

    private Long productInfoId;

    private String descript;

    private Long storeInfoId;
}
