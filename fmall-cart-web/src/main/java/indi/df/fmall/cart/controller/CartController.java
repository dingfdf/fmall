package indi.df.fmall.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import indi.df.fmall.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartController {
    @Reference
    CartService cartService;

    @RequestMapping("/addToCart")
    public String addToCart(){
        return "cartlist";
    }
}
