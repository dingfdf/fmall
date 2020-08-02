package indi.df.fmall.service;

import indi.df.fmall.bean.PmsBaseAttrInfo;
import indi.df.fmall.bean.PmsBaseAttrValue;
import indi.df.fmall.bean.PmsBaseSaleAttr;

import java.util.List;


public interface AttrService {

    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    List<PmsBaseSaleAttr> baseSaleAttrList();
}
