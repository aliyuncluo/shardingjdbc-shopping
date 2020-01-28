package com.cluo.shopping.entity;

import lombok.Data;

@Data
public class StoreInfo {

    private Long id;

    private String storeName;

    private Integer reputation;

    private String regionCode;
}
