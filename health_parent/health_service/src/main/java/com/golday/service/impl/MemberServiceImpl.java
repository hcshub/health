package com.golday.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.golday.constant.MessageConstant;
import com.golday.dao.*;
import com.golday.pojo.Order;
import com.golday.pojo.User;
import com.golday.service.MemberService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrdersettingDao ordersettingDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private PackageDao packageDao;


    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public Map<String, List<Object>> findByMemberReport() {
        List<Object> months = new ArrayList<>();
        List<Object> memberCount = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-13);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,+1);
            Date date = calendar.getTime();
            String format = sdf.format(date);
            String time = format + "-31";
            months.add(format);
            int number = userDao.findByNumber(time);
            memberCount.add(number);
        }
        Map<String,List<Object>> map = new HashMap<>();
        map.put("months",months);
        map.put("memberCount",memberCount);
        return map;
    }

    @Override
    public Map<String, Object> findByPackageReport() {
        //Map<String,List<String>>
        //Map<String,List<Map<Integer,String>>>
        List<Object> names = new ArrayList<>();
        List<Map<String,Object>> list = userDao.findByCheckgroupReport();
        if (list != null) {
            for (Map<String, Object> stringObjectMap : list) {
                Object name = stringObjectMap.get("name");
                names.add(name);
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("legendData",names);
        map.put("seriesData",list);
        return map;
    }

    @Override
    public Map<String, Object> findByBusinessReport() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        Map<String,Object> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取当月 "yyyy-MM" 字符串
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
        String monthStr = sdf2.format(date);

        //今天的日期字符串
        String today = sdf.format(date);
        map.put("reportDate",today);
        //总的会员数
        int totalMember = memberDao.findByTotalMember();
        map.put("totalMember",totalMember);
        //今天新增会员数
        int todayNewMember = memberDao.findByTodayMmber(today);
        map.put("todayNewMember",todayNewMember);
        //今日预约数
        int todayOrderNumber = 0;
        try {
            todayOrderNumber = ordersettingDao.findByTodayOrderNumber(today);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("todayOrderNumber",todayOrderNumber);
        //今日到诊数
        String orderstatusYes = Order.ORDERSTATUS_YES; //已就诊
        int todayVisitsNumber = orderDao.findByTodayVisitsNumber(today,orderstatusYes);
        map.put("todayVisitsNumber",todayVisitsNumber);

        //求本周的第一天和最后一天
        //今天在本周是第几天(第一天是周日)
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        //本周开始日期
        if ((i-2)>=0) {
            calendar.add(Calendar.DAY_OF_MONTH,2-i);
        }else {
            calendar.add(Calendar.DAY_OF_MONTH,-6);
        }
        String beginDate = sdf.format(date);
        //本周结束日期
        calendar.add(Calendar.DAY_OF_MONTH,6);
        Date time = calendar.getTime();
        String endDate = sdf.format(time);

        //本周新增会员
        int thisWeekNewMember = memberDao.findByThisWeekNewMember(beginDate,endDate);
        map.put("thisWeekNewMember",thisWeekNewMember);
        //本周预约数
        int thisWeekOrderNumber = 0;
        try {
            thisWeekOrderNumber = ordersettingDao.findThisWeekOrderNumber(beginDate,endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("thisWeekOrderNumber",thisWeekOrderNumber);
        //本周到诊数
        int thisWeekVisitsNumber = orderDao.findByThisWeekVisitsNumber(beginDate,endDate,orderstatusYes);
        map.put("thisWeekVisitsNumber",thisWeekVisitsNumber);

        //月数据,使用的开始和结束日期
        String monthStartDay = monthStr + "-1";
        String monthLastDay = monthStr + "-31";
        //本月新增会员数
        int thisMonthNewMember = memberDao.findByThisMonthNewMember(monthStartDay,monthLastDay);
        map.put("thisMonthNewMember",thisMonthNewMember);
        //本月预约数
        int thisMonthOrderNumber = ordersettingDao.findByThisMonthOrderNumber(monthStartDay,monthLastDay);
        map.put("thisMonthOrderNumber",thisMonthOrderNumber);
        //本月到诊数
        int thisMonthVisitsNumber = orderDao.findByThisMonthVisitsNumber(monthStartDay,monthLastDay,orderstatusYes);
        map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);

        //热门套餐
        List<Map<String,Object>> hotPackage = packageDao.findByHotPackage();
        map.put("hotPackage",hotPackage);
        return map;
    }
}
