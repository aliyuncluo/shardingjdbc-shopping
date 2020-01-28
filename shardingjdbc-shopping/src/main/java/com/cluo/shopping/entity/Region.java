package com.cluo.shopping.entity;

import lombok.Data;

@Data
public class Region {

    private Long id;

    private String regionCode;

    private String regionName;

    private Integer level;

    private String parentRegionCode;
}
