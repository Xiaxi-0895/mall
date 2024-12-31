package com.xiaxi.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaxi.mall.constant.Constant.ProductListOrderBy;
import com.xiaxi.mall.error.MallException;
import com.xiaxi.mall.error.MallExceptionEnum;
import com.xiaxi.mall.model.dao.CategoryMapper;
import com.xiaxi.mall.model.dao.ProductMapper;
import com.xiaxi.mall.model.pojo.Product;
import com.xiaxi.mall.model.query.ProductListQuery;
import com.xiaxi.mall.model.request.AddGoodsReq;
import com.xiaxi.mall.model.request.ProductListReq;
import com.xiaxi.mall.model.request.UpdateProductReq;
import com.xiaxi.mall.model.vo.CategoryVo;
import com.xiaxi.mall.service.CategoryService;
import com.xiaxi.mall.service.ProductsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void addGoods(AddGoodsReq addGoodsReq) {
        Product product = new Product();
        BeanUtils.copyProperties(addGoodsReq, product);
        if(productMapper.selectByName(product.getName()) != null) throw new MallException(MallExceptionEnum.REPETITIVE_PRODUCT_NAME);
        if(categoryMapper.selectByPrimaryKey(addGoodsReq.getCategoryId())==null) throw new MallException(MallExceptionEnum.INEXISTENT_CATEGORY_ID);
        int count = productMapper.insertSelective(product);
        if(count!=1) throw new MallException(MallExceptionEnum.INSERT_FAILED);
    }

    @Override
    public void updateGoods(UpdateProductReq updateProductReq) {
        Product product = new Product();
        if(ObjectUtils.isEmpty(productMapper.selectByPrimaryKey(updateProductReq.getId()))) throw new MallException(MallExceptionEnum.INEXISTENT_ID);
        if(productMapper.selectByName(updateProductReq.getName()).getId()!=updateProductReq.getId()) throw new MallException(MallExceptionEnum.UPDATE_FAILED);
        BeanUtils.copyProperties(updateProductReq, product);
        int count = productMapper.updateByPrimaryKeySelective(product);
        if(count!=1) throw new MallException(MallExceptionEnum.UPDATE_FAILED);
    }

    @Override
    public void deleteGoods(int id) {
        int count = productMapper.deleteByPrimaryKey(id);
        if(count!=1) throw new MallException(MallExceptionEnum.DELETE_FAILED);
    }

    @Override
    public void batchUpdateGoods(List<Integer> ids, int status) {
        productMapper.batchUpdateStatus(ids,status);
    }

    @Override
    @Cacheable("goodsListForAdmin")
    public PageInfo listForAdmin(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize,"id");
        List<Product> list = productMapper.selectList();
        PageInfo info = new PageInfo(list);
        return info;
    }

    @Override
    public String getDetails(int id) {
        Product product = productMapper.selectByPrimaryKey(id);
        if(product==null) throw new MallException(MallExceptionEnum.INEXISTENT_ID);
        return product.getDetail();
    }

    @Override
    public PageInfo listForUser(ProductListReq request) {
//        if(!StringUtils.hasText(request.getKeyword())) throw new MallException(MallExceptionEnum.PARA_NOT_NULL);
        ProductListQuery productListQuery = new ProductListQuery();
        List<Integer> ids =new ArrayList();
        List<CategoryVo> list = categoryService.ShowCategory(request.getCategoryId());
        ids.add(request.getCategoryId());
        getIds(ids,list);
        productListQuery.setCategoryIds(ids);
        if (request.getKeyword()!=null){
            productListQuery.setKeyword("%" + request.getKeyword() + "%");
        }else{
            productListQuery.setKeyword("%");
        }
        String orderBy = request.getOrderBy();
        if(ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
            PageHelper.startPage(request.getPageNo(), request.getPageSize(),orderBy);
        }else{
            PageHelper.startPage(request.getPageNo(), request.getPageSize());
        }
        List<Product> result = productMapper.selectListForUser(productListQuery);
        PageInfo pageInfo = new PageInfo(result);
        return pageInfo;
    }
    private void getIds(List<Integer> ids,List<CategoryVo> list){
        if (ObjectUtils.isEmpty(list)) return;
        list.forEach(item->{
            ids.add(item.getId());
            if (!ObjectUtils.isEmpty(item.getChildren())) {
                getIds(ids, item.getChildren());
            }
        });
    }


}
