package com.golday.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class Lianxi {
    public static void main(String[] args) {
        //读取已有的表,并在表中添加数据
        try {
            XSSFWorkbook sheets = new XSSFWorkbook("/E:\\1-项目部分\\测试.xlsx");
            XSSFSheet sheet = sheets.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum >= 0) {
                for (int i = 0; i <= lastRowNum; i++) {
                    XSSFRow row = sheet.getRow(i);
                    short lastCellNum = row.getLastCellNum();
                    row.createCell(lastCellNum).setCellValue(i);
                }
            }
            sheets.write(new FileOutputStream("/E:\\1-项目部分\\添加后文件3.xlsx"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
