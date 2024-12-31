package com.xiaxi.mall.model.dao;

import com.xiaxi.mall.model.pojo.Category;
import com.xiaxi.mall.model.vo.CategoryVo;

import java.util.List;
import java.util.Map;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category row);

    int insertSelective(Category row);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category row);

    int updateByPrimaryKey(Category row);

    List<Category> selectSimilar(Map<String, Object> map);

    List<Category> selectList();

    List<Category> selectByParentId(Integer parentId);

    List<Integer> selectIdsByParentId(Integer id);
}