package com.golday.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class FileRead {
    public static void main(String[] args) {
        try {
            XSSFWorkbook sheets = new XSSFWorkbook("/E:\\1-项目部分\\测试.xlsx");
            XSSFSheet sheet = sheets.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum >= 1) {
                for (int i = 1; i <= lastRowNum; i++) {
                    XSSFRow row = sheet.getRow(i);
                    short lastCellNum = row.getLastCellNum();
                    if (lastCellNum >= 1) {
                        for (int i1 = 0; i1 < lastCellNum; i1++) {
                            XSSFCell cell = row.getCell(i1);
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                System.out.print(cell.getNumericCellValue() + " - ");
                            }else if(cell.getCellType() == Cell.CELL_TYPE_STRING){
                                System.out.print(cell.getStringCellValue() + " - ");
                            }
                        }
                            System.out.println();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
