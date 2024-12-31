package com.xiaxi.mall.model.dao;

import com.xiaxi.mall.model.vo.CartVo;

import java.util.List;

public interface CartVoMapper {
    List<CartVo> selectByUserId(int userId);

    List<CartVo> selectCheckedByUserId(int userId);
}
