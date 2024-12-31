package com.xiaxi.mall.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class AddCategoryReq {
    @NotNull(message = "name不能为空")
    @Size(min = 1, max = 5)
    private String name;

    @NotNull(message = "type不能为空")
    @Min(1)
    @Max(3)
    private Integer type;

    @NotNull(message = "parentId不能为空")
    private Integer parentId;

    @NotNull(message = "orderNum不能为空")
    private Integer orderNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
}
