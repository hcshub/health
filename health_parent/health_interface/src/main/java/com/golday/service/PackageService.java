package com.golday.service;

import com.golday.entity.PageResult;
import com.golday.entity.QueryPageBean;
import com.golday.pojo.CheckGroup;
import com.golday.pojo.Package;

import java.util.List;

public interface PackageService {
    PageResult<Package> allPackage(QueryPageBean queryPageBean);

    void addPackage(Package pak, Integer[] checkgroupIds);

    Package findPackage(int id);

    List<CheckGroup> allCheckGroup();

    Integer[] packageIds(int id);

    void updatePackage(Package pak, Integer[] checkgroupIds);
}
