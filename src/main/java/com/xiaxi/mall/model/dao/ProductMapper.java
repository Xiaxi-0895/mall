package com.xiaxi.mall.model.dao;

import com.xiaxi.mall.model.pojo.Product;
import com.xiaxi.mall.model.query.ProductListQuery;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product row);

    int insertSelective(Product row);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product row);

    int updateByPrimaryKey(Product row);

    Product selectByName(String name);

    void batchUpdateStatus(List<Integer>ids,int status);

    List<Product> selectList();

    List<Product> selectListForUser(ProductListQuery query);



}