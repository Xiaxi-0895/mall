package com.xiaxi.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaxi.mall.error.MallException;
import com.xiaxi.mall.error.MallExceptionEnum;
import com.xiaxi.mall.model.dao.CategoryMapper;
import com.xiaxi.mall.model.pojo.Category;
import com.xiaxi.mall.model.pojo.User;
import com.xiaxi.mall.model.request.AddCategoryReq;
import com.xiaxi.mall.model.vo.CategoryVo;
import com.xiaxi.mall.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void addCategory(AddCategoryReq newCategory) {
        Category category = new Category();
        BeanUtils.copyProperties(newCategory, category);
        Map<String,Object> similar = new HashMap<>();
        similar.put("parentId",category.getParentId());
        similar.put("type",category.getType());
        if(categoryMapper.selectByPrimaryKey(category.getParentId())==null&&category.getParentId()!=0) throw new MallException(MallExceptionEnum.NONEXISTENT_PARENT_ID);
        List<Category> list = categoryMapper.selectSimilar(similar);
        if(list!=null){
            list.forEach(item->{
                if(Objects.equals(category.getOrderNum(), item.getOrderNum())) throw new MallException(MallExceptionEnum.REPETITIVE_ORDER_NUM);
            });
        }
        int count = categoryMapper.insertSelective(category);
        if (count!=1) throw new MallException(MallExceptionEnum.INSERT_FAILED);
    }

    @Override
    public void deleteCategory(int id) {
        int count = categoryMapper.deleteByPrimaryKey(id);
        if(count!=1){
            if(count==0){
                throw new MallException(MallExceptionEnum.INEXISTENT_ID);
            }else{
                throw new MallException(MallExceptionEnum.DELETE_FAILED);
            }
        }
    }

    @Override
    public PageInfo PaginationQueryCategory(int page, int rows) {
        PageHelper.startPage(page, rows,"type,order_num");
        List<Category> categories = categoryMapper.selectList();
        PageInfo pageInfo = new PageInfo(categories);
        return pageInfo;
    }

    @Override
    @Cacheable(value = "ShowCategory")
    public List<CategoryVo> ShowCategory(int parentId) {
        List<CategoryVo> categoryVoList = new ArrayList<>();
        recursivelyFindCategories(categoryVoList,parentId);
        return categoryVoList;
    }


    private void recursivelyFindCategories(List<CategoryVo> categories,int parentId) {
        List<Category> categoriesList = categoryMapper.selectByParentId(parentId);
        if(categoriesList==null || categoriesList.size()==0) return;
        categoriesList.forEach(item->{
            CategoryVo categoryVo = new CategoryVo();
            BeanUtils.copyProperties(item,categoryVo);
            categories.add(categoryVo);
            recursivelyFindCategories(categoryVo.getChildren(),item.getId());
        });
    }

}
