package com.cluo.shopping.service;

import com.cluo.shopping.entity.ProductInfo;

import java.util.List;
import java.util.Map;

public interface ProductService {

    public void createProduct(ProductInfo productInfo);

    public List<ProductInfo> queryProduct(int start, int size);

    public ProductInfo selectProductInfo(Long id);

    public int countProductInfo();

    public List<Map> selectProductGroupList();
}
