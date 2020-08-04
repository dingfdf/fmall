package indi.df.fmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import indi.df.fmall.bean.PmsSkuAttrValue;
import indi.df.fmall.bean.PmsSkuImage;
import indi.df.fmall.bean.PmsSkuInfo;
import indi.df.fmall.bean.PmsSkuSaleAttrValue;
import indi.df.fmall.manage.mapper.PmsSkuAttrValueMapper;
import indi.df.fmall.manage.mapper.PmsSkuImageMapper;
import indi.df.fmall.manage.mapper.PmsSkuInfoMapper;
import indi.df.fmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import indi.df.fmall.service.SkuService;
import indi.df.fmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    RedisUtil redisUtil;

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

    //使用数据库查询sku，早期版本，不用了，使用缓存查询
    public PmsSkuInfo getSkuByIdFromDb(String skuId) {
        // sku商品对象
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(skuId);
        // sku的图片集合
        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setId(skuId);
        List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.select(pmsSkuImage);
        pmsSkuInfo.setSkuImageList(pmsSkuImages);
        return pmsSkuInfoMapper.selectOne(pmsSkuInfo);
    }

    //使用缓存查询sku
    //测试的时候，如果单机可以同时打开两个页面发出请求
    @Override
    public PmsSkuInfo getSkuById(String skuId, String ip) {
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        // 链接缓存
        Jedis jedis = redisUtil.getJedis();
        // 查询缓存
        String skuKey = "sku:" + skuId + ":info";
        String skuJson = jedis.get(skuKey);
        // 如果缓存中查得到，进行处理
        if(StringUtils.isNotBlank(skuJson)){
            //将查询到的json字符串转成sku对象
            pmsSkuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
        }else{
            // 如果缓存中没有，查询mysql
            // 设置分布式锁,解决缓存击穿的问题
            String token = UUID.randomUUID().toString(); //UUID.randomUUID().toString()是javaJDK提供的一个自动生成主键的方法
            String OK = jedis.set("sku:" + skuId + ":lock", token, "nx", "px", 10*1000);// 拿到锁的线程有10秒的过期时间
            // 设置成功，有权在10秒的过期时间内访问数据库,其他的请求（线程）走到这就被锁住，必须等过期时间结束
            if(StringUtils.isNotBlank(OK)&&OK.equals("OK")){
                pmsSkuInfo =  getSkuByIdFromDb(skuId);
                if(pmsSkuInfo != null){
                    // mysql查询结果存入redis
                    jedis.set("sku:" + skuId + ":info", JSON.toJSONString(pmsSkuInfo));
                }else{
                    // 数据库中不存在该sku
                    // 为了防止缓存穿透，将null或者空字符串值设置给redis，并设置3分钟的过期时间
                    jedis.setex("sku:" + skuId + ":info", 60*3, JSON.toJSONString(""));
                }
                // 在访问mysql后，将mysql的分布锁释放
                String lockToken = jedis.get("sku:" + skuId + ":lock");
                if(StringUtils.isNotBlank(lockToken) && lockToken.equals(token)){ //如果当前的token与访问前设置的token相同
                    //jedis.eval("lua");可与用lua脚本，在查询到key的同时删除该key，防止高并发下的意外的发生
                    jedis.del("sku:" + skuId + ":lock");// 用token确认删除的是自己的sku的锁，防止删错别人的锁
                }
            }else{
                // 设置失败，自旋（该线程在睡眠几秒后，重新尝试访问本方法）
                return getSkuById(skuId,ip);
            }
        }
        jedis.close();
        return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);
        return pmsSkuInfos;
    }

    @Override
    public List<PmsSkuInfo> getAllSku(String catalog3Id) {
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectAll();

        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfos) {
            String skuId = pmsSkuInfo.getId();

            PmsSkuAttrValue pmsSkuAttrValue = new PmsSkuAttrValue();
            pmsSkuAttrValue.setSkuId(skuId);
            List<PmsSkuAttrValue> select = pmsSkuAttrValueMapper.select(pmsSkuAttrValue);

            pmsSkuInfo.setSkuAttrValueList(select);
        }
        return pmsSkuInfos;
    }

    @Override
    public boolean checkPrice(String productSkuId, BigDecimal productPrice) {
        return false;
    }
}
