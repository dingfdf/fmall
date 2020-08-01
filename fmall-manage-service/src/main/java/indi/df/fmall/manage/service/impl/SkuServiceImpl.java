package indi.df.fmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import indi.df.fmall.bean.PmsSkuAttrValue;
import indi.df.fmall.bean.PmsSkuImage;
import indi.df.fmall.bean.PmsSkuInfo;
import indi.df.fmall.bean.PmsSkuSaleAttrValue;
import indi.df.fmall.manage.mapper.PmsSkuAttrValueMapper;
import indi.df.fmall.manage.mapper.PmsSkuImageMapper;
import indi.df.fmall.manage.mapper.PmsSkuInfoMapper;
import indi.df.fmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import indi.df.fmall.service.SkuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.List;

@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;

    //@Autowired
    //RedisUtil redisUtil;

    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        // 插入skuInfo
        pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        String skuId = pmsSkuInfo.getId();
        //sku的属性包括两部分，分别是平台属性和销售属性，要分别插入
        // 插入sku平台属性的值
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }
        // 插入sku销售属性的值
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }
        // 插入图片信息
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }
    }

    @Override
    public PmsSkuInfo getSkuById(String skuId, String ip) {
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(skuId);
        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setId(skuId);
        List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.select(pmsSkuImage);
        pmsSkuInfo.setSkuImageList(pmsSkuImages);
        return pmsSkuInfoMapper.selectOne(pmsSkuInfo);
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {
        return null;
    }

    @Override
    public List<PmsSkuInfo> getAllSku(String catalog3Id) {
        return null;
    }

    @Override
    public boolean checkPrice(String productSkuId, BigDecimal productPrice) {
        return false;
    }
}
