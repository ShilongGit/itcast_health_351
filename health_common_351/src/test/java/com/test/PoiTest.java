package com.test;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PoiTest {

//    @Test
//    public void test() throws IOException {
//
//        //1.获取工作薄对象【execl】
//        XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\admin\\Desktop\\ordersetting_template.xlsx");
//        //2. 获取工作表
//        XSSFSheet sheet = workbook.getSheetAt(0);
//        //3. 获取行对象
//        for (Row row : sheet) {
//            for (Cell cell : row) {
//
//                    cell.setCellType(Cell.CELL_TYPE_STRING);
//
//                System.out.println(cell.getStringCellValue());
//
//            }
//        }
//    }
//
//    @Test
//    public void write() throws IOException {
//        //创建excel表格【工作薄】
//        XSSFWorkbook workbook = new XSSFWorkbook();
////创建工作表
//        XSSFSheet sheet = workbook.createSheet("abc");
////创建行对象
//        XSSFRow row = sheet.createRow(0);
////创建单元格
//        XSSFCell cell1 = row.createCell(0);
//        cell1.setCellValue("username");
//        XSSFCell cell2 = row.createCell(1);
//        cell2.setCellValue("age");
//
////创建行对象
//        XSSFRow row2 = sheet.createRow(1);
////创建单元格
//        XSSFCell cell3 = row2.createCell(0);
//        cell3.setCellValue("张三");
//        XSSFCell cell4 = row2.createCell(1);
//        cell4.setCellValue("23");
//
//
//
//        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\admin\\Desktop\\test.xlsx");
//        workbook.write(fileOutputStream);
//
//
//        fileOutputStream.flush();
//        workbook.close();
//        fileOutputStream.close();
//    }

}
