package com.xiaxi.mall.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreatOrderReq {
    @NotNull(message = "收货人不能为空")
    private String receiverName;

    @NotNull(message = "收件人手机号码不能为空")
    @Size(max = 11,min = 11,message = "请输入正确的11位手机号码")
    private String receiverMobile;

    @NotNull(message = "收货地址不能为空")
    private String receiverAddress;

    private Integer postage = 0;

    private Integer paymentType = 1;

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public Integer getPostage() {
        return postage;
    }

    public void setPostage(Integer postage) {
        this.postage = postage;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }
}
