package com.itheima.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;
/*
    Jedis工具类
 */
public class JedisUtils {
    private static JedisPool pool ;

    static{

        //读取配置文件 创建ResourceBundle
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        //获取最大连接数
        String maxTotal = bundle.getString("maxTotal");
        //获取最大空闲连接数
        String maxIdle = bundle.getString("maxIdle");
        //获取ip地址
        String host = bundle.getString("host");
        //获取端口号
        String port = bundle.getString("port");


        //连接池对象的配置信息
        JedisPoolConfig config = new JedisPoolConfig();
        //最大连接数
        config.setMaxTotal(Integer.valueOf(maxTotal));
        //最大空闲连接
        config.setMaxIdle(Integer.valueOf(maxIdle));
        //创建了连接池对象
         pool = new JedisPool(config,host,Integer.valueOf(port));
    }

    //获取Jedis对象
    public static Jedis getJedis(){

        //获取到jedis
        return  pool.getResource();
    }
}
