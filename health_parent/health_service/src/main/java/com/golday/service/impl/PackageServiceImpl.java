package com.golday.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.golday.dao.PackageDao;
import com.golday.entity.PageResult;
import com.golday.entity.QueryPageBean;
import com.golday.pojo.CheckGroup;
import com.golday.pojo.Package;
import com.golday.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = PackageService.class)
public class PackageServiceImpl implements PackageService {
    @Autowired
    private PackageDao packageDao;

    @Transactional
    @Override
    public PageResult<Package> allPackage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Package> page = packageDao.findPackageByString(queryPageBean.getQueryString());
        PageResult<Package> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return pageResult;
    }

    @Override
    public void addPackage(Package pak, Integer[] checkgroupIds) {
        packageDao.addPackage(pak);
        Integer packageId = pak.getId();
        for (Integer checkgroupId : checkgroupIds) {
            packageDao.addPackageCheckgroup(packageId,checkgroupId);
        }
    }

    @Override
    public Package findPackage(int id) {
        return packageDao.findPackage(id);
    }

    @Override
    public List<CheckGroup> allCheckGroup() {
       return packageDao.allCheckGroup();
    }

    @Override
    public Integer[] packageIds(int id) {
        return packageDao.packageIds(id);
    }

    @Override
    public void updatePackage(Package pak, Integer[] checkgroupIds) {
        //1. 更新package表单信息
        packageDao.updatePackage(pak);
        //2. 删除原本套餐和检查组的关联表信息
        packageDao.deletePackageCheckGroup(pak.getId());
        //3. 增加套餐和检查项关联表信息
        Integer packageId = pak.getId();
        for (Integer checkgroupId : checkgroupIds) {
            packageDao.addPackageCheckgroup(packageId,checkgroupId);
        }
    }
}
