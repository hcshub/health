package com.golday.excel;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileWrite {
    public static void main(String[] args) {
        XSSFWorkbook sheets = new XSSFWorkbook();
        XSSFSheet sheet = sheets.createSheet("文件");
        for (int i = 0; i < 3; i++) {
            XSSFRow row = sheet.createRow(i);
            for (int j = 0; j < 3; j++) {
                row.createCell(j).setCellValue("第"+(i+1)+"行第"+(j+1)+"列");
            }
        }
        try {
            sheets.write(new FileOutputStream("/E:\\1-项目部分\\文件下载.xlsx"));
            sheets.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
