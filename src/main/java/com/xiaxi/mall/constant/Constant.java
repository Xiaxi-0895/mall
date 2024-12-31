package com.xiaxi.mall.constant;

import com.google.common.collect.Sets;
import com.xiaxi.mall.error.MallException;
import com.xiaxi.mall.error.MallExceptionEnum;

import java.util.Set;

public class Constant {

    public interface UserRole{
        int user=1;
        int admin=2;
    }

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price desc", "price asc");
    }
    public interface ProductStatus{
        int SALE=1;
        int TAKEN_OFF=0;
    }
    public interface Cart{
        int CHECKED=1;
        int UNCHECKED=0;
    }
    public enum OrderStatus{
         CANCEL(0,"已取消"),
         UNPAID(10,"未支付"),
         PAID(20,"已支付"),
         SHIPPED(30,"已发货"),
         COMPLETED(40,"已完成");
         private int code;
         private String value;
         OrderStatus(int code, String value){
             this.code = code;
             this.value = value;
         }

         public static OrderStatus codeOf(int code){
             for(OrderStatus orderStatus : OrderStatus.values()){
                 if(orderStatus.code == code){
                     return orderStatus;
                 }
             }
             throw new MallException(MallExceptionEnum.INEXISTENT_ORDER_STATUS);
         }

         public int getCode() {
             return code;
         }
         public String getValue() {
             return value;
         }

        public void setCode(int code) {
            this.code = code;
        }
        public void setValue(String value) {
             this.value = value;
        }
    }

}
