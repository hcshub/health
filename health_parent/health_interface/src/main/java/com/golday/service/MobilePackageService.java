package com.golday.service;

import com.golday.pojo.Package;

import java.util.List;

public interface MobilePackageService {
    List<Package> getPackage();

    Package findPackage(int id);

    Package showPackage(int id);
}
