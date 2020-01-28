package com.cluo.shopping;

import com.cluo.shopping.entity.ProductInfo;
import com.cluo.shopping.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingjdbcShoppingApplicationTests {

    @Autowired
    private ProductService productService;

    @Test
    public void contextLoads() {
        for(int i=0;i<100;i++){
            ProductInfo productInfo = new ProductInfo();
            if(i%2==0){
                productInfo.setStoreInfoId(2L);
            }else{
                productInfo.setStoreInfoId(1L);
            }
            // productInfo.setProductInfoId();
            productInfo.setProductName("分布式服务架构"+i);
            productInfo.setSpec("16*25");
            productInfo.setRegionCode("110100");
            productInfo.setPrice(new BigDecimal(99.0));
            productInfo.setDescription("这本书的评价不错,很实用!");
            productService.createProduct(productInfo);
        }

    }

    @Test
    public void testQuery(){
        int pageNum = 1;
        int pageSize = 10;
        int offset = (pageNum-1)*pageSize;
        int limit = pageSize;
        List<ProductInfo> list = productService.queryProduct(offset, pageSize);
        for(ProductInfo productInfo:list){
            System.out.println(productInfo.toString());
        }
    }

    @Test
    public void testDetail(){
        Long id=63670107720777728L;
        ProductInfo productInfo = productService.selectProductInfo(id);
        System.out.println(productInfo.toString());
    }

    @Test
    public void testCount(){
        int num = productService.countProductInfo();
        System.out.println("num="+num);
    }

    @Test
    public void testSelectByGroup(){
        List<Map> maps = productService.selectProductGroupList();
        for(Map map:maps){
            System.out.println(map);
        }

    }
}
