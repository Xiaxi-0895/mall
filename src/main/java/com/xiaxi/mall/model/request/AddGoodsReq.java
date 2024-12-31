package com.xiaxi.mall.model.request;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AddGoodsReq {

    @NotNull(message = "商品名不能为空")
    private String name;

    @NotNull(message = "商品图片不能为空")
    private String image;

    @NotNull(message = "商品详情不能为空")
    private String detail;

    @NotNull(message = "商品分类不能为空")
    private Integer categoryId;

    @NotNull(message = "价格不能为空")
    @Min(value = 1,message = "价格不能小于1分")
    private Integer price;

    @NotNull(message = "库存不能为空")
    @Max(value = 10000,message = "库存不能大于10000")
    private Integer stock;

    @NotNull(message = "状态不能为空")
    @Max(1)
    @Min(0)
    private Integer status;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
