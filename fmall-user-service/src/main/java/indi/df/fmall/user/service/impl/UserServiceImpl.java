package indi.df.fmall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import indi.df.fmall.bean.UmsMember;
import indi.df.fmall.bean.UmsMemberReceiveAddress;
import indi.df.fmall.service.UserService;
import indi.df.fmall.user.mapper.UmsMemberReceiveAddressMapper;
import indi.df.fmall.user.mapper.UserMapper;
import indi.df.fmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<UmsMember> getAllUser() {
        return null;
    }

    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {
        return null;
    }

    //验证用户名是否在缓存或数据库中存在，有则返回
    @Override
    public UmsMember login(UmsMember umsMember) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            if(jedis != null){
                String umsMemberStr = jedis.get("user:" + umsMember.getPassword() + umsMember.getUsername() + ":info");
                if (StringUtils.isNotBlank(umsMemberStr)) {
                    // 密码正确
                    UmsMember umsMemberFromCache = JSON.parseObject(umsMemberStr, UmsMember.class); //json格式转对象
                    return umsMemberFromCache;
                }
            }
            // 链接redis失败，开启数据库
            UmsMember umsMemberFromDb = loginFromDb(umsMember);
            if(umsMemberFromDb != null){
                jedis.setex("user:" + umsMember.getPassword()+umsMember.getUsername() + ":info",60 * 60 * 24, JSON.toJSONString(umsMemberFromDb));
            }
            return umsMemberFromDb; //没查到的话就返回一个空值
        }finally {
            jedis.close();
        }
    }

    //从数据库中查询用户
    private UmsMember loginFromDb(UmsMember umsMember) {
        List<UmsMember> umsMembers = userMapper.select(umsMember);
        if(umsMembers != null){
            return umsMembers.get(0);
        }
        return null;
    }

    //将创建出的token存到服务器redis中一份
    @Override
    public void addUserToken(String token, String memberId) {
        Jedis jedis = redisUtil.getJedis();
        jedis.setex("user:" + memberId + ":token", 60 * 60 * 2, token); //Setex 命令为指定的 key设置值及其过期时间
        jedis.close();
    }

    @Override
    public UmsMemberReceiveAddress getReceiveAddressById(String receiveAddressId) {
        return null;
    }
}
