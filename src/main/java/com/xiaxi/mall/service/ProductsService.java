package com.xiaxi.mall.service;

import com.github.pagehelper.PageInfo;
import com.xiaxi.mall.model.request.AddGoodsReq;
import com.xiaxi.mall.model.request.ProductListReq;
import com.xiaxi.mall.model.request.UpdateProductReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductsService {
    void addGoods(AddGoodsReq addGoodsReq);
    void updateGoods(UpdateProductReq updateProductReq);
    void deleteGoods(int id);
    void batchUpdateGoods(List<Integer> ids,int status);
    PageInfo listForAdmin(int pageNo, int pageSize);
    PageInfo listForUser(ProductListReq request);
    String getDetails(int id);
}
