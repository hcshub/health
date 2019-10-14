package com.golday.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.golday.constant.MessageConstant;
import com.golday.entity.Result;
import com.golday.pojo.Package;
import com.golday.service.MobilePackageService;
import com.golday.utils.QiniuUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/package")
public class PackageController {
    @Reference
    private MobilePackageService mobilePackageService;

    @RequestMapping("/getPackage")
    public Result getPackage(){
        List<Package> list = mobilePackageService.getPackage();
        if (list != null) {
            for (Package aPackage : list) {
                String img = aPackage.getImg();
                aPackage.setImg("http://"+QiniuUtils.ADDRESS+"/"+img);
            }
        }
        return new Result(true,MessageConstant.GET_SETMEAL_LIST_SUCCESS,list);
    }

    @RequestMapping("/packageOne")
    public Result packageOne(int id){
        Package pack = mobilePackageService.findPackage(id);
        String img = pack.getImg();
        pack.setImg("http://"+QiniuUtils.ADDRESS+"/"+img);
        System.out.println(pack);
        return new Result(true,MessageConstant.GET_SETMEAL_LIST_SUCCESS,pack);
    }

    @RequestMapping("/findPackage")
    public Result findPackage(int id){
        Package pack = mobilePackageService.showPackage(id);
        String img = pack.getImg();
        String imgURL = "http://" + QiniuUtils.ADDRESS + "/" + img;
        pack.setImg(imgURL);
        return new Result(true,MessageConstant.GET_SETMEAL_LIST_SUCCESS,pack);
    }
}
