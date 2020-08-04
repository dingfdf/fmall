package indi.df.fmall.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import indi.df.fmall.bean.OmsCartItem;
import indi.df.fmall.service.CartService;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Override
    public OmsCartItem ifCartExistByUser(String memberId, String skuId) {
        return null;
    }

    @Override
    public void addCart(OmsCartItem omsCartItem) {

    }

    @Override
    public void updateCart(OmsCartItem omsCartItemFromDb) {

    }

    @Override
    public void flushCartCache(String memberId) {

    }

    @Override
    public List<OmsCartItem> cartList(String userId) {
        return null;
    }

    @Override
    public void checkCart(OmsCartItem omsCartItem) {

    }
}
