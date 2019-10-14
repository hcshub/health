package com.golday.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.golday.constant.MessageConstant;
import com.golday.entity.Result;
import com.golday.service.MemberService;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        Map<String,List<Object>> map = memberService.findByMemberReport();
        return new Result(true,MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }

    @RequestMapping("/getPackageReport")
    public Result getPackageReport(){
        Map<String,Object> map = memberService.findByPackageReport();
        return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
    }

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        Map<String,Object> map = memberService.findByBusinessReport();
        return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,map);
    }

    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = memberService.findByBusinessReport();
        String excelPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";
        XSSFWorkbook sheets = new XSSFWorkbook(excelPath);
        XSSFSheet sheet = sheets.getSheetAt(0);
        //日期
        sheet.getRow(2).getCell(5).setCellValue(String.valueOf(map.get("reportDate")));
        //新增会员数
        sheet.getRow(4).getCell(5).setCellValue(String.valueOf(map.get("todayNewMember")));
        //本周新增会员数
        sheet.getRow(5).getCell(5).setCellValue(String.valueOf(map.get("thisWeekNewMember")));
        //今日预约数
        sheet.getRow(7).getCell(5).setCellValue(String.valueOf(map.get("todayOrderNumber")));
        //本周预约数
        sheet.getRow(8).getCell(5).setCellValue(String.valueOf(map.get("thisWeekOrderNumber")));
        //本月预约数
        sheet.getRow(9).getCell(5).setCellValue(String.valueOf(map.get("thisMonthOrderNumber")));
        //总会员数
        sheet.getRow(4).getCell(7).setCellValue(String.valueOf(map.get("totalMember")));
        //本月新增会员数
        sheet.getRow(5).getCell(7).setCellValue(String.valueOf(map.get("thisMonthNewMember")));
        //今日到诊数
        sheet.getRow(7).getCell(7).setCellValue(String.valueOf(map.get("todayVisitsNumber")));
        //本周到诊数
        sheet.getRow(8).getCell(7).setCellValue(String.valueOf(map.get("thisWeekVisitsNumber")));
        //本月到诊数
        sheet.getRow(9).getCell(7).setCellValue(String.valueOf(map.get("thisMonthVisitsNumber")));
        //热门套餐
        List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("hotPackage");
        List<String> packageColumn = new ArrayList<>();
        packageColumn.add("name");
        packageColumn.add("count");
        packageColumn.add("proportion");
        packageColumn.add("remark");
        if (list != null) {
            for (Map<String, Object> stringObjectMap : list) {
                for (int i = 12; i <= 15; i++) {
                    for (int j = 0; j < packageColumn.size(); j++) {
                        int count = 4;
                        String s = packageColumn.get(j);
                        sheet.getRow(i).getCell(count + j).setCellValue(String.valueOf(stringObjectMap.get(s)));

                    }
                }
            }
        }
        //
        String filename = String.valueOf(map.get("reportDate")) + "_运营数据.xlsx";
        filename = new String(filename.getBytes(),"ISO-8859-1");
        //告诉客户端我的文件是excel
        response.setContentType("application/vnd.ms-excel");
        //将文件转化成流
        ServletOutputStream ops = response.getOutputStream();
        //通过响应头将文件带给客户端
        response.setHeader("Content-Disposition","attachment;filename="+filename);
        sheets.write(ops);
        ops.flush();
        ops.close();
        sheets.close();
        return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS);
    }

    @RequestMapping("/exportBusinessReport2")
    public Result exportBusinessReport(){

        return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS);
    }
}
