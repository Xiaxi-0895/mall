package com.xiaxi.mall.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public class UpdateProductReq {

    @NotNull
    private int id;

    private String name;

    private String image;

    private String detail;

    private Integer categoryId;

    @Min(value = 1,message = "价格不能小于1分")
    private Integer price;

    @Max(value = 10000,message = "库存不能大于10000")
    private Integer stock;

    @Max(1)
    @Min(0)
    private Integer status;

    @NotNull
    public int getId() {
        return id;
    }

    public void setId(@NotNull int id) {
        this.id = id;
    }

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

    public @Min(value = 1, message = "价格不能小于1分") Integer getPrice() {
        return price;
    }

    public void setPrice(@Min(value = 1, message = "价格不能小于1分") Integer price) {
        this.price = price;
    }

    public @Max(value = 10000, message = "库存不能大于10000") Integer getStock() {
        return stock;
    }

    public void setStock(@Max(value = 10000, message = "库存不能大于10000") Integer stock) {
        this.stock = stock;
    }

    public @Max(1) @Min(0) Integer getStatus() {
        return status;
    }

    public void setStatus(@Max(1) @Min(0) Integer status) {
        this.status = status;
    }
}
