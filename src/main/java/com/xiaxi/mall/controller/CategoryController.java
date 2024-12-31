package com.xiaxi.mall.controller;

import com.github.pagehelper.PageInfo;
import com.xiaxi.mall.common.ApiRestResponse;
import com.xiaxi.mall.model.request.AddCategoryReq;
import com.xiaxi.mall.model.vo.CategoryVo;
import com.xiaxi.mall.service.impl.CategoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @Operation(summary = "添加商品类别", description = "利用AddCategoryReq获取Http传入的参数后添加进数据库")
    @PostMapping("/admin/category/add")
    public ApiRestResponse addCategory(@Valid AddCategoryReq req) {
        categoryService.addCategory(req);
        return ApiRestResponse.success();
    }

    @Operation(summary = "删除商品分类")
    @PostMapping("/admin/category/delete")
    public ApiRestResponse deleteCategory(Integer id) {
        categoryService.deleteCategory(id);
        return ApiRestResponse.success();
    }

    @Operation(summary = "分页查询目录")
    @GetMapping("/admin/category/list")
    public ApiRestResponse listCategoryForAdmin(Integer pageNo, Integer pageSize) {
        PageInfo pageInfo = categoryService.PaginationQueryCategory(pageNo, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @Operation(summary = "分页查询目录")
    @GetMapping("/user/category/list")
    public ApiRestResponse listCategory(@RequestParam(defaultValue = "0") int categoryId) {
        List<CategoryVo> list = categoryService.ShowCategory(categoryId);
        return ApiRestResponse.success(list);
    }
}
