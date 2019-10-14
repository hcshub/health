package com.golday.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.golday.constant.MessageConstant;
import com.golday.dao.CheckItemDao;
import com.golday.entity.PageResult;
import com.golday.entity.QueryPageBean;
import com.golday.exception.MyException;
import com.golday.pojo.CheckItem;
import com.golday.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) throws Exception{
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult pageSearch(QueryPageBean queryPageBean) throws Exception {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckItem> page = checkItemDao.findCodeName(queryPageBean.getQueryString());
        PageResult<CheckItem> pageResult = new PageResult<CheckItem>(page.getTotal(), page.getResult());
        return pageResult;
    }

    @Override
    public void delete(int id) throws MyException {
        int num = checkItemDao.findCheck(id);
        if (num > 0) {
           throw new MyException(MessageConstant.DELETE_CHECKGROUP_FAIL);
        }else {
            checkItemDao.delete(id);
        }
    }

    @Override
    public CheckItem showOne(int id) throws Exception{
            CheckItem checkItem = checkItemDao.showOne(id);
        return checkItem;
    }

    @Override
    public void updateCheckItem(CheckItem checkItem) throws Exception {
        checkItemDao.updateCheckItem(checkItem);
    }
}
