package indi.df.fmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import indi.df.fmall.bean.PmsSkuInfo;
import indi.df.fmall.service.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {
    @Reference
    SkuService skuService;

    //以skuid = [11-14]为例
    @RequestMapping("{skuId}.html")
    //通过 @PathVariable 可以将URL中占位符参数{xxx}绑定到处理器类的方法形参中
    //ModelMap可以在前后端传递参数
    public String item(@PathVariable String skuId, ModelMap map, HttpServletRequest request){
        String remoteAddr = request.getRemoteAddr();
        // request.getHeader("");// nginx负载均衡
        PmsSkuInfo pmsSkuInfo = skuService.getSkuById(skuId,remoteAddr);
        //sku对象
        map.put("skuInfo",pmsSkuInfo);
        return "item";
    }

    @RequestMapping("/index")
    //@ResponseBody 当使用thymleaf模板引擎时，就不能用responsebody注解，因为这是返回数据的
    public String index(ModelMap modelMap){
        List<String> list = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            list.add("循环数据"+i);
        }

        modelMap.put("list",list);
        modelMap.put("hello","hello thymeleaf !!");
        modelMap.put("check","0"); //0不选中，1选中

        return "index"; //借助于thymleaf模板引擎，可以直接用返回字符串的方式返回页面
    }
}
