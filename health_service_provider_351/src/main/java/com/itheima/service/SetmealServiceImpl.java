package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConst;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.utils.FastJsonUtils;
import com.itheima.utils.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealDao setmealDao;

    @Override
    public PageResult findPageByCondition(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        Page<Setmeal> setmealPage = setmealDao.findPageByCondition(queryPageBean.getQueryString());
        return new PageResult(setmealPage.getTotal(),setmealPage);
    }

    @Override
    public Result addsetmealWithCheckGroup(Setmeal setmeal, Integer[] checkgroupIds) {

        try {
            setmealDao.insertSetmeal(setmeal);

            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("setmealId",setmeal.getId());
            paramMap.put("checkgroupIds",checkgroupIds);

            setmealDao.insertSetmealIdWithCheckgroupIds(paramMap);
            Jedis jedis = JedisUtils.getJedis();
            jedis.sadd("insert",setmeal.getImg());
            return new Result(true,MessageConst.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.ADD_SETMEAL_FAIL);
        }
    }

    @Override
    public Result findAll() {
        try {
            List<Setmeal> setmealList = null;
            Jedis jedis = JedisUtils.getJedis();
            String redisList = jedis.get("setmealList");

            if ("".equals(redisList) || redisList== null){
                setmealList = setmealDao.findAll();
                String json = FastJsonUtils.convertObjectToJSON(setmealList);
                jedis.set("setmealList",json);
            }else {
                setmealList = FastJsonUtils.toList(redisList, Setmeal.class);
            }
            return new Result(true,MessageConst.QUERY_SETMEAL_SUCCESS,setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConst.QUERY_SETMEAL_FAIL);
        }
    }

    @Override
    public Result findById(Integer id) {
        try {
            Setmeal setmeal = null;
            Jedis jedis = JedisUtils.getJedis();
            String redisJson = jedis.get("setmeal"+id);
            if ("".equals(redisJson) || redisJson== null){
                setmeal = setmealDao.findById(id);
                String s = FastJsonUtils.convertObjectToJSON(setmeal);
                jedis.set("setmeal"+id,s);
            }else {
                setmeal = FastJsonUtils.toBean(redisJson,Setmeal.class);
            }


            return new Result(true,MessageConst.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConst.QUERY_SETMEAL_FAIL);
        }
    }
}
