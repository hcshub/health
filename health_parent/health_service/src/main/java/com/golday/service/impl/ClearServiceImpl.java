package com.golday.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.golday.dao.ClearDao;
import com.golday.service.ClearService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = ClearService.class)
public class ClearServiceImpl implements ClearService {
    @Autowired
    private ClearDao clearDao;

    @Override
    public List<String> findImgs() {
        return clearDao.findImgs();
    }
}
