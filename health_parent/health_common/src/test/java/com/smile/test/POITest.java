package com.smile.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class POITest {
//    使用POI读取Excel文件中的数据
    @Test
    public void test1() throws Exception{
//        加载指定文件，创建一个Excel对象（工作簿）
        XSSFWorkbook excel=new XSSFWorkbook(new FileInputStream(new File("/Users/Kilig/Desktop/poi.xlsx")));
//        读取Excel文件中第一个sheet标签页
        XSSFSheet sheet = excel.getSheetAt(0);
//        遍历sheet标签页，获得每一行数据
        for (Row row : sheet) {
            //遍历行对象获取单元格对象
//            判断cell是否为数字：StringUtils.isNumber(String.valueOf(cell))
            for (Cell cell : row) {
                System.out.println(cell.getStringCellValue());
            }
        }
        excel.close();
    }

    //    使用POI读取Excel文件中的数据
    @Test
    public void test2() throws Exception{
//        加载指定文件，创建一个Excel对象（工作簿）
        XSSFWorkbook excel=new XSSFWorkbook(new FileInputStream(new File("/Users/Kilig/Desktop/poi.xlsx")));
//        读取Excel文件中第一个sheet标签页
        XSSFSheet sheet = excel.getSheetAt(0);
//        获得当前工作表中最后一个行号，需要注意：行号从0开始
        int lastRowNum = sheet.getLastRowNum();
        for(int i=0;i<=lastRowNum;i++){
//            上面的row是一个接口，下面的row是一个实现类
            XSSFRow row = sheet.getRow(i);  // 根据行号获取每一行
//            获得当前行最后一个单元格，获得的是总列数，不用加=
            short lastCellNum = row.getLastCellNum();
            for(int j=0;j<lastCellNum;j++){
                XSSFCell cell = row.getCell(j);  // 根据单元格索引获得单元格对象
                System.out.println(cell.getStringCellValue());
            }
        }
        excel.close();
    }

//    使用POI向Excel文件写入数据，并且通过输出流将创建的Excel文件保存到本地
    @Test
    public void test3() throws Exception{
//        在内存中创建一个Excel文件（工作簿）
        XSSFWorkbook excel=new XSSFWorkbook();
//        创建一个工作表对象
        XSSFSheet sheet = excel.createSheet("拉拉的表");
//        在工作表中创建行对象
        XSSFRow title = sheet.createRow(0);
//        在行中创建单元格对象
        title.createCell(0).setCellValue("姓名");
        title.createCell(1).setCellValue("地址");
        title.createCell(2).setCellValue("年龄");

        XSSFRow dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue("拉拉");
        dataRow.createCell(1).setCellValue("长沙");
        dataRow.createCell(2).setCellValue("17");

//        创建一个输出流，将内存中的Excel文件写到磁盘
        FileOutputStream out = new FileOutputStream(new File("/Users/Kilig/Desktop/test1.xlsx"));
        excel.write(out);
//        刷新
        out.flush();
        excel.close();
    }

}
