package com.golday.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.golday.constant.MessageConstant;
import com.golday.entity.Result;
import com.golday.pojo.OrderSetting;
import com.golday.service.OrderSettingService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrdersetttingController {
    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile){
        try {
            InputStream is = excelFile.getInputStream();
            XSSFWorkbook sheets = new XSSFWorkbook(is);
            XSSFSheet sheet = sheets.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            List<OrderSetting> list = new ArrayList<>();
            if (lastRowNum >= 1) {
                for (int i = 1; i <= lastRowNum; i++) {
                    XSSFRow row = sheet.getRow(i);
                    Date date = row.getCell(0).getDateCellValue();
                    int num = (int)row.getCell(1).getNumericCellValue();
                    OrderSetting orderSetting = new OrderSetting(date, num);
                    list.add(orderSetting);
                }
            }
            orderSettingService.importFile(list);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    @RequestMapping("/getOrder")
    public Result getOrder(String date){
        List<Map<String, Integer>> list = orderSettingService.getOrder(date);
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,list);
    }

    @RequestMapping("/setNumber")
    public Result setNumber(@RequestBody OrderSetting orderSetting){
        orderSettingService.updateNumber(orderSetting);
        return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS);
    }
}
