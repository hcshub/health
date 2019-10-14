package com.golday.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.golday.dao.MobilePackageDao;
import com.golday.pojo.Package;
import com.golday.service.MobilePackageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service(interfaceClass = MobilePackageService.class)
public class MobilePackageServiceImpl implements MobilePackageService {
    @Autowired
    private MobilePackageDao mobilePackageDao;

    @Override
    public List<Package> getPackage() {
        return mobilePackageDao.getPackage();
    }

    @Override
    public Package findPackage(int id) {
        return mobilePackageDao.findPackage(id);
    }

    @Override
    public Package showPackage(int id) {
        return mobilePackageDao.showPackage(id);
    }
}
