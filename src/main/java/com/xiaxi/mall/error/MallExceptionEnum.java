package com.xiaxi.mall.error;

public enum MallExceptionEnum {
    NEED_USER_NAME(10001,"用户名不能为空"),
    NEED_PASSWORD(10002,"密码不能为空"),
    DUPLICATE_USERNAME(10003,"用户名重复"),
    SHORT_PASSWORD(10004,"密码长度过短，不能少于8位"),
    REGISTRATION_FAILED(10005,"注册失败，请重试"),
    WRONG_PASSWORD(10006,"用户名或密码错误"),
    NEED_LOGIN(10007,"需要登陆"),
    UPDATE_FAILED(10008,"更新失败"),
    NOT_ADMINISTRATOR(10009,"非管理员用户"),
    PARA_NOT_NULL(10011,"参数不能为空"),
    REQUEST_PARAM_ERROR(10010,"参数错误"),
    NONEXISTENT_PARENT_ID(10012,"不存在的上级目录编号"),
    REPETITIVE_ORDER_NUM(10013,"重复的展示排序"),
    INSERT_FAILED(10014,"新增失败"),
    DELETE_FAILED(10015,"删除失败"),
    INEXISTENT_ID(10016,"不存在的目录编号"),
    INEXISTENT_CATEGORY_ID(10017,"不存在的商品类别编号"),
    REPETITIVE_PRODUCT_NAME(10018,"重复的商品名称"),
    MAKE_DIR_FAILED(10019,"文件夹创建失败"),
    REMOVED_PRODUCT(10020,"商品已下架"),
    INEXISTENT_PRODUCT_ID(10021,"不存在的商品编号"),
    EMPTY_CART(10022,"没有选择商品或购物车为空"),
    INSUFFICIENT_INVENTORY(10023,"库存不足"),
    INEXISTENT_PRODUCT(10024,"不存在的商品"),
    INEXISTENT_ORDER_STATUS(10025,"不存在的订单状态"),
    NON_ORDER_NO(10026,"订单号不能为空"),
    INEXISTENT_ORDER_NO(10027,"不存在的订单号"),
    NOT_PERSONAL_ORDER(10028,"非本人订单"),
    CANCEL_FAILED(10029,"订单无法取消"),
    CANNOT_PAY(10030,"当前状态不支持支付"),
    WRONG_STATUS_CODE(10031,"错误的订单状态"),




    SYSTEM_ERROR(20000,"系统异常"),
    UNEXPECTED_ERROR(20001,"意料之外的错误");
    int code;
    String message;

    MallExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
