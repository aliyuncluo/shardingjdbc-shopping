package com.cluo.shopping.dao;

import com.cluo.shopping.entity.ProductDescript;
import com.cluo.shopping.entity.ProductInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductDao {
    //添加商品基本信息
    @Insert("insert into product_info (product_info_id,store_info_id,product_name,spec,region_code,price,image_url) values " +
            "(#{productInfoId},#{storeInfoId},#{productName},#{spec},#{regionCode},#{price},#{imageUrl})")
    @Options(useGeneratedKeys = true,keyProperty = "productInfoId",keyColumn = "id")
    int insertProductInfo(ProductInfo productInfo);

    @Insert({"insert into product_descript (id,product_info_id,descript,store_info_id) values " +
            "(#{id},#{productInfoId},#{descript},#{storeInfoId})"})
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int insertProductDescript(ProductDescript productDescript);

    @Select("SELECT i.product_info_id as productInfoId," +
            "i.store_info_id as storeInfoId," +
            "i.product_name as productName," +
            "i.spec,i.region_code as regionCode," +
            "i.price,i.image_url as imageUrl," +
            "d.descript,r.`region_name` as placeOfOrigin " +
            "FROM product_info i JOIN product_descript d ON i.product_info_id = d.product_info_id JOIN region r ON r.region_code = i.region_code ORDER BY i.product_info_id DESC " +
            "LIMIT #{start},#{size}")
    List<ProductInfo> selectProductList(@Param("start") int start,@Param("size") int size);

    @Select("select product_info_id as productInfoId," +
            "store_info_id as storeInfoId," +
            "product_name as productName," +
            "spec,region_code as regionCode," +
            "price,image_url as imageUrl from product_info where product_info_id=#{id }")
    ProductInfo selectProductInfo(@Param("id") Long id);

    //总数统计
    @Select("select count(1) from product_info")
    int countProductInfo();

    //分组统计
    @Select("select region_code,count(1) as num from product_info group by region_code having num>1 order by region_code ASC")
    List<Map> selectProductGroupList();


}
