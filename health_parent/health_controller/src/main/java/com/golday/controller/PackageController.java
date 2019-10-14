package com.golday.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.golday.constant.MessageConstant;
import com.golday.entity.PageResult;
import com.golday.entity.QueryPageBean;
import com.golday.entity.Result;
import com.golday.pojo.CheckGroup;
import com.golday.pojo.Package;
import com.golday.service.PackageService;
import com.golday.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/package")
public class PackageController {
    @Reference
    private PackageService packageService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/allPackage")
    public Result allPackage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Package> pageResult = packageService.allPackage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,pageResult);
    }

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        String filename = imgFile.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        filename = UUID.randomUUID().toString() + suffix;
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),filename);
            Jedis jedis = jedisPool.getResource();
            jedis.sadd("qiniuyun",filename);
            jedis.close();
            Map<String, String> map = new HashMap<>();
            map.put("filename",filename);
            map.put("address",QiniuUtils.ADDRESS);
            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,map);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("/addPackage")
    public Result addPackage(@RequestBody Package pak,@RequestParam Integer[] checkgroupIds){
        packageService.addPackage(pak,checkgroupIds);
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @RequestMapping("/findPackage")
    public Result findPackage(int id){
        Package pak = packageService.findPackage(id);
        String img = pak.getImg();
        pak.setImg("http://" + QiniuUtils.ADDRESS + "/" + img);
        return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,pak);
    }

    @RequestMapping("/allCheckGroup")
    public Result allCheckGroup(){
        List<CheckGroup> list = packageService.allCheckGroup();
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
    }

    @RequestMapping("/packageIds")
    public Result packageIds(int id){
        Integer[] ids = packageService.packageIds(id);
        return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,ids);
    }

    @RequestMapping("/updatePackage")
    public Result updatePackage(@RequestBody Package pak,@RequestParam Integer[] checkgroupIds){
        packageService.updatePackage(pak,checkgroupIds);
        return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
    }
}
