package indi.df.fmall.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import indi.df.fmall.service.CartService;
import indi.df.fmall.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderController {
    @Reference
    CartService cartService;

    @Reference
    OrderService orderService;

    @RequestMapping("toTrade")
    public String toTrade(){
        return "trade";
    }
}
