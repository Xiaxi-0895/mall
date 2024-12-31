package com.xiaxi.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;
import com.xiaxi.mall.constant.Constant;
import com.xiaxi.mall.error.MallException;
import com.xiaxi.mall.error.MallExceptionEnum;
import com.xiaxi.mall.filter.UserFilter;
import com.xiaxi.mall.model.dao.*;
import com.xiaxi.mall.model.pojo.Order;
import com.xiaxi.mall.model.pojo.OrderItem;
import com.xiaxi.mall.model.pojo.Product;
import com.xiaxi.mall.model.request.CreatOrderReq;
import com.xiaxi.mall.model.vo.CartVo;
import com.xiaxi.mall.model.vo.OrderItemVo;
import com.xiaxi.mall.model.vo.OrderVo;
import com.xiaxi.mall.service.OrderService;
import com.xiaxi.mall.utils.OrderCodeFactory;
import com.xiaxi.mall.utils.QRCodeGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private CartVoMapper cartVoMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Value("${file.upload.ip}")
    String ip;
    @Value("${file.upload.dir}")
    String dir;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable("creatOrder")
    public String createOrder(CreatOrderReq req, int userId) {
        Order order = new Order();
        BeanUtils.copyProperties(req, order);
        order.setUserId(userId);
        order.setOrderNo(OrderCodeFactory.getOrderCode((long) userId));
        List<CartVo> list = cartVoMapper.selectCheckedByUserId(userId);
        if (ObjectUtils.isEmpty(list)) throw  new MallException(MallExceptionEnum.EMPTY_CART);
        int count = 0;
        for (CartVo cartVo : list) {
            Product product = productMapper.selectByPrimaryKey(cartVo.getProductId());
            if (product == null) throw  new MallException(MallExceptionEnum.INEXISTENT_PRODUCT);
            //检查商品是否上架
            if(product.getStatus()!=Constant.ProductStatus.SALE) throw new MallException(MallExceptionEnum.REMOVED_PRODUCT.getCode(),product.getName()+":"+MallExceptionEnum.REMOVED_PRODUCT.getMessage());
            //更新商品库存
            int stock = product.getStock()-cartVo.getQuantity();
            if(stock<0) throw new MallException(MallExceptionEnum.INSUFFICIENT_INVENTORY.getCode(),product.getName()+":"+MallExceptionEnum.INSUFFICIENT_INVENTORY.getMessage());
            product.setStock(stock);
            productMapper.updateByPrimaryKeySelective(product);
            //计算订单总价
            count+=cartVo.getTotalPrice();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderNo(order.getOrderNo());
            orderItem.setProductId(cartVo.getProductId());
            orderItem.setProductName(cartVo.getProductName());
            orderItem.setProductImg(cartVo.getProductImage());
            orderItem.setUnitPrice(cartVo.getPrice());
            orderItem.setQuantity(cartVo.getQuantity());
            orderItem.setTotalPrice(cartVo.getTotalPrice());
            orderItemMapper.insertSelective(orderItem);
            //商品无论是否上架都从购物车删除
            cartMapper.deleteByPrimaryKey(cartVo.getId());
        }
        if (count==0) throw  new MallException(MallExceptionEnum.REMOVED_PRODUCT);
        order.setTotalPrice(count);
        order.setOrderStatus(Constant.OrderStatus.UNPAID.getCode());
        orderMapper.insertSelective(order);
        return order.getOrderNo();
    }

    @Override
    public OrderVo GetOrderDetails(Long orderNo) {
        if (orderNo==null) throw new MallException(MallExceptionEnum.NON_ORDER_NO);
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(ObjectUtils.isEmpty(order)) throw  new MallException(MallExceptionEnum.INEXISTENT_ORDER_NO);
        if(!Objects.equals(order.getUserId(), UserFilter.getCurrentUser().getId())) throw new MallException(MallExceptionEnum.NOT_PERSONAL_ORDER);
        List<OrderItem> items = orderItemMapper.selectByOrderNo(orderNo);
        if(ObjectUtils.isEmpty(items)) throw  new MallException(MallExceptionEnum.UNEXPECTED_ERROR.getCode(),MallExceptionEnum.UNEXPECTED_ERROR.getMessage()+":查询不到相关订单号的商品列表");
        OrderVo orderVo = new OrderVo();
        List<OrderItemVo> orderItemVos = new ArrayList<>();
        items.forEach(item -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(item, orderItemVo);
            orderItemVos.add(orderItemVo);
        });
        BeanUtils.copyProperties(order, orderVo);
        orderVo.setOrderItems(orderItemVos);
        orderVo.setOrderStatusName(Constant.OrderStatus.codeOf(orderVo.getOrderStatus()).getValue());
        return orderVo;
    }

    @Override
    public void cancelOrder(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(ObjectUtils.isEmpty(order)) throw  new MallException(MallExceptionEnum.INEXISTENT_ORDER_NO);
        if(!Objects.equals(order.getUserId(), UserFilter.getCurrentUser().getId())) throw new MallException(MallExceptionEnum.NOT_PERSONAL_ORDER);
        if(order.getOrderStatus()==Constant.OrderStatus.UNPAID.getCode()){
            order.setOrderStatus(Constant.OrderStatus.CANCEL.getCode());
            order.setEndTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        }else {
            throw new MallException(MallExceptionEnum.CANCEL_FAILED);
        }
    }

    @Override
    public String qrcode(Long orderNo){
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String address = ip+":"+request.getLocalPort();
        String payUrl = "http://"+address+"/pay?orderNo="+orderNo;
        try {
            QRCodeGenerator.generateQRCodeImage(payUrl,350,350,dir+"/"+orderNo+".png");
        } catch (IOException | WriterException e) {
            throw new RuntimeException(e);
        }
        String pngAddress = "http://"+address+"/images/"+orderNo+".png";
        return pngAddress;
    }

    @Override
    public PageInfo listOrderForAdmin(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize,"create_time asc");
        List<Order> list = orderMapper.selectAllForAdmin();
        List<OrderVo> orderVos = new ArrayList<>();
        list.forEach(order -> {
            List<OrderItem> orderItems = orderItemMapper.selectByOrderNo(Long.valueOf(order.getOrderNo()));
            orderVos.add(OrderTurnToOrderVo(order,orderItems));
        });
        PageInfo pageInfo = new PageInfo(orderVos);
        return pageInfo;
    }

    @Override
    public void delivery(Long orderNo) {
        Order order = checkOrderNo(orderNo,Constant.OrderStatus.PAID.getCode());
        order.setDeliveryTime(new Date());
        order.setOrderStatus(Constant.OrderStatus.SHIPPED.getCode());
        orderMapper.updateByPrimaryKeySelective(order);
    }

    private Order checkOrderNo(Long orderNo,int statusCode) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (ObjectUtils.isEmpty(order)) throw  new MallException(MallExceptionEnum.INEXISTENT_ORDER_NO);
        if(order.getOrderStatus()!=statusCode) throw new MallException(MallExceptionEnum.WRONG_STATUS_CODE);
        return order;
    }

    private OrderVo OrderTurnToOrderVo(Order order,List<OrderItem> orderItems){
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);
        List<OrderItemVo> orderItemVos = new ArrayList<>();
        orderItems.forEach(orderItem -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(orderItem, orderItemVo);
            orderItemVos.add(orderItemVo);
        });
        orderVo.setOrderItems(orderItemVos);
        orderVo.setOrderStatusName(Constant.OrderStatus.codeOf(orderVo.getOrderStatus()).getValue());
        return orderVo;
    }

    public void pay(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order==null) throw  new MallException(MallExceptionEnum.INEXISTENT_ORDER_NO);
        if (order.getOrderStatus()==Constant.OrderStatus.UNPAID.getCode()){
            order.setOrderStatus(Constant.OrderStatus.PAID.getCode());
            order.setPayTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        }else {
            throw new MallException(MallExceptionEnum.CANNOT_PAY);
        }
    }
    @Override
    public void complete(Long orderNo) {
        Order order = checkOrderNo(orderNo,Constant.OrderStatus.SHIPPED.getCode());
        if (!Objects.equals(order.getUserId(), UserFilter.getCurrentUser().getId())&&UserFilter.getCurrentUser().getRole()!=Constant.UserRole.admin) throw new MallException(MallExceptionEnum.NOT_PERSONAL_ORDER);
        order.setOrderStatus(Constant.OrderStatus.COMPLETED.getCode());
        order.setEndTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
    }
}
