package indi.df.fmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import indi.df.fmall.service.SearchService;
import org.springframework.stereotype.Controller;

@Controller
public class SearchController {
    @Reference
    SearchService searchService;


}
