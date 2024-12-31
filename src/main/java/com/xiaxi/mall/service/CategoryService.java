package com.xiaxi.mall.service;

import com.github.pagehelper.PageInfo;
import com.xiaxi.mall.model.pojo.Category;
import com.xiaxi.mall.model.request.AddCategoryReq;
import com.xiaxi.mall.model.vo.CategoryVo;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CategoryService {
    void addCategory(AddCategoryReq category);
    void deleteCategory(int id);
    PageInfo PaginationQueryCategory(int page, int rows);
    List<CategoryVo> ShowCategory(int id);
}
