package indi.df.fmall.service;

import indi.df.fmall.bean.OmsCartItem;

import java.util.List;

public interface CartService {
    OmsCartItem ifCartExistByUser(String memberId, String skuId);

    void addCart(OmsCartItem omsCartItem);

    void updateCart(OmsCartItem omsCartItemFromDb);

    void flushCartCache(String memberId);

    List<OmsCartItem> cartList(String userId);

    void checkCart(OmsCartItem omsCartItem);
}
