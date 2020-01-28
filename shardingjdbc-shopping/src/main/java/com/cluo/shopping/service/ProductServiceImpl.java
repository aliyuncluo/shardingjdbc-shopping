package com.cluo.shopping.service;

import com.cluo.shopping.dao.ProductDao;
import com.cluo.shopping.entity.ProductDescript;
import com.cluo.shopping.entity.ProductInfo;
import com.cluo.shopping.util.SnowflakeIdWorker;
import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import com.relops.snowflake.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService{
     @Autowired
     private ProductDao productDao;
    @Autowired
    private IdGenerator idGenerator;

    @Override
    public List<ProductInfo> queryProduct(int start, int size) {
        return productDao.selectProductList(start,size);
    }

    @Override
    public ProductInfo selectProductInfo(Long id) {
        return productDao.selectProductInfo(id);
    }

    @Override
    public int countProductInfo() {
        return productDao.countProductInfo();
    }

    @Override
    public List<Map> selectProductGroupList() {
        return productDao.selectProductGroupList();
    }

    @Transactional(readOnly = false)
    @Override
    public void createProduct(ProductInfo productInfo) {
         //SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 1);
         Snowflake idWorker = new Snowflake(1);
         //long product_info_id = idGenerator.generateId().longValue();
         long product_info_id = idWorker.next();
         productInfo.setProductInfoId(product_info_id);
         productDao.insertProductInfo(productInfo);
         ProductDescript productDescript = new ProductDescript();
         productDescript.setProductInfoId(productInfo.getProductInfoId());
         productDescript.setStoreInfoId(productInfo.getStoreInfoId());
         productDescript.setDescript(productInfo.getDescription());
         long id = idGenerator.generateId().longValue();
         productDescript.setId(id);
         productDao.insertProductDescript(productDescript);
    }
}
