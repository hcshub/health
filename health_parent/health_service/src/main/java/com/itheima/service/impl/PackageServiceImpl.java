package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.PackageDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Package;
import com.itheima.service.PackageService;
import com.itheima.util.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
@Service(interfaceClass = PackageService.class)
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageDao packageDao;
    @Autowired
    JedisPool jedisPool;
    @Override
    public List<CheckGroup> findAllGroups() {
        return packageDao.findAllGroups();
    }

    @Transactional
    @Override
    public void addPackage(Package mypackage, int[] ids) {
        packageDao.addPackage(mypackage);
        for (int id : ids) {
            packageDao.addGroupRelation(mypackage.getId(),id);
        }

    }

    @Override
    public PageResult<Package> findPage(QueryPageBean queryPageBean) {
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Package> page=packageDao.findPackage(queryPageBean.getQueryString());
        return new PageResult<Package>(page.getTotal(),page.getResult());

    }

    /**
     *mobile
     * 查询所有套餐
     * @return
     */
    @Override
    public List<Package> getPackage() {

        //创建redis对象
        Jedis jedis = jedisPool.getResource();

        String values = jedis.get(RedisConstant.SETMEAL_NAME);


        if (values == null || "".equals(values)) {
            //调用dao
            List<Package> packageList = packageDao.getPackage();

            if (null != packageList) {
                for (Package pkg : packageList) {
                    //获取图片体制
                    pkg.setImg("http://" + QiNiuUtil.DOMAIN + "/" + pkg.getImg());
                }
            }
            jedis.set(RedisConstant.SETMEAL_NAME, JSONArray.parseArray(JSON.toJSONString(packageList)).toString());
            return packageList;
        }
        String s = jedis.get(RedisConstant.SETMEAL_NAME);

        List<Package> packageList = JSONObject.parseArray(s, Package.class);
        //归还连接
        jedis.close();

        return packageList;

    }

    /**
     * mobile
     * 套餐详情
     * @param id
     * @return
     */
    @Override
    public Package findById(int id) {
        Jedis jedis2 = jedisPool.getResource();

        String values2 = jedis2.get(RedisConstant.SETMEAL_NAME_DETAIL);

        if (values2 == null || "".equals(values2)) {
            Package aPackage = packageDao.findById(id);

            if (null != aPackage) {
                //添加图片
                aPackage.setImg("http://" + QiNiuUtil.DOMAIN + "/" + aPackage.getImg());
            }
            //存到jedis  Package----->JSON字符串
            jedis2.set(RedisConstant.SETMEAL_NAME_DETAIL+ "_" + id, JSON.parseObject(JSON.toJSONString(aPackage)).toString());

            return aPackage;
        }
        //JSON----->Package字符串
        String s = jedis2.get(RedisConstant.SETMEAL_NAME_DETAIL);

        Package aPackage = JSONObject.parseObject(s, Package.class);
        //归还连接
        jedis2.close();

        return aPackage;


    }
}
