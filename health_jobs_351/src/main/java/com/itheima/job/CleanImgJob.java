package com.itheima.job;

import com.itheima.utils.JedisUtils;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@Component
public class CleanImgJob {



    /**
     * 1. 找出垃圾图片
     *      两个set集合的差值
     * 2. 删除七牛上的垃圾图片
     * 3. redis的垃圾图片名称清除
     */
    public void clear(){
        Jedis jedis = JedisUtils.getJedis();
        //sdiff : 取两个集合的差值
        //返回值：垃圾图片的名称集合
        Set<String> sdiff = jedis.sdiff("add", "insert");
        //2. 删除七牛上的垃圾图片
        for (String imgName : sdiff) {
            QiniuUtils.deleteFileFromQiniu(imgName);
            //3. redis的垃圾图片名称清除
            jedis.srem("add", imgName);
        }

    }

}
