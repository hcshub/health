package com.golday.dao;

import com.golday.pojo.Package;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MobilePackageDao {
    @Select("select * from t_package")
    List<Package> getPackage();

    Package findPackage(int id);

    Package showPackage(int id);
}
