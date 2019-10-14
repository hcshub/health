package com.golday.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.golday.constant.MessageConstant;
import com.golday.dao.CheckgroupDao;
import com.golday.entity.PageResult;
import com.golday.entity.QueryPageBean;
import com.golday.exception.MyException2;
import com.golday.exception.MyException3;
import com.golday.pojo.CheckGroup;
import com.golday.pojo.CheckItem;
import com.golday.service.CheckgroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckgroupService.class)
@Transactional
public class CheckgroupServiceImpl implements CheckgroupService {
    @Autowired
    private CheckgroupDao checkgroupDao;

    @Override
    public List<CheckItem> allCheckItem() throws Exception {
        List<CheckItem> list = checkgroupDao.allCheckItem();
        return list;
    }

    @Override
    public void addCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds) throws MyException2 {
        checkgroupDao.addCheckGroup(checkGroup);
        Integer id = checkGroup.getId();
        addCheckGroupFor(checkitemIds, id);
    }

    private void addCheckGroupFor(Integer[] checkitemIds, Integer id) {
        Map<String,Integer> map = new HashMap<>();
        map.put("id",id);
        for (Integer checkitemId : checkitemIds) {
            map.put("checkitemId",checkitemId);
            checkgroupDao.addCheckGroupCheckItem(map);
        }
    }

    @Override
    public void groupAddCheck(List<Integer> value) {

    }

    @Override
    public PageResult<CheckGroup> allCheckGroup(QueryPageBean queryPageBean) throws Exception{
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<CheckGroup> page = checkgroupDao.allCheckGroup(queryPageBean.getQueryString());
        PageResult<CheckGroup> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return pageResult;
    }

    @Override
    public void deleteCheckGroup(int id) throws MyException3 {
        //先判断检查组是否被套餐引用
        int num = checkgroupDao.findGroupPackage(id);
        if (num > 0) {
            throw new MyException3(MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        checkgroupDao.deleteCheckGroup(id);
    }

    @Override
    public CheckGroup findCheckgroup(int id) {
        return checkgroupDao.findCheckgroup(id);
    }

    @Override
    public Integer[] findCheckitemIds(int id) {
        return checkgroupDao.findCheckitemIds(id);
    }

    @Override
    public void updateCheckgroup(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkgroupDao.updateCheckgroup(checkGroup);
        checkgroupDao.deleteGroupItem(checkGroup.getId());
        addCheckGroupFor(checkitemIds,checkGroup.getId());
    }
}
