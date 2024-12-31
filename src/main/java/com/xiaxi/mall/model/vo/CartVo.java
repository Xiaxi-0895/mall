package com.xiaxi.mall.model.vo;

public class CartVo {
    private int id;
    private int productId;
    private String productName;
    private String productImage;
    private Integer quantity;
    private int selected;
    private int price;
    private int totalPrice;

    @Override
    public String toString() {
        return "CartVo{" +
                "id=" + id +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productImg='" + productImage + '\'' +
                ", quantity=" + quantity +
                ", selected=" + selected +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImg) {
        this.productImage = productImg;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        if(this.price!=0) this.totalPrice=this.price*quantity;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
        if(this.quantity!=0) this.totalPrice=this.price*this.quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

}
