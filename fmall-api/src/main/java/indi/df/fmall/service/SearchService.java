package indi.df.fmall.service;

import indi.df.fmall.bean.PmsSearchParam;
import indi.df.fmall.bean.PmsSearchSkuInfo;

import java.util.List;

public interface SearchService {
    List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam);
}
