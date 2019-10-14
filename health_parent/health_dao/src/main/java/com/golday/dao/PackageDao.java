package com.golday.dao;

import com.github.pagehelper.Page;
import com.golday.entity.PageResult;
import com.golday.entity.QueryPageBean;
import com.golday.pojo.CheckGroup;
import com.golday.pojo.Package;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PackageDao {
    Page<Package> findPackageByString(String queryString);

    void addPackage(Package pak);

    void addPackageCheckgroup(@Param("packageId") Integer packageId, @Param("checkgroupId") Integer checkgroupId);

    Package findPackage(int id);

    List<CheckGroup> allCheckGroup();

    Integer[] packageIds(int id);

    void updatePackage(Package pak);

    void deletePackageCheckGroup(Integer id);

    List<Map<String,Object>> findByHotPackage();
}
