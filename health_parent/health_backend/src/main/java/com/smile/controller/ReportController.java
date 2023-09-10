package com.smile.controller;

//报表操作

import com.alibaba.dubbo.config.annotation.Reference;
import com.smile.constant.MessageConstant;
import com.smile.entity.Result;
import com.smile.service.MemberService;
import com.smile.service.ReportService;
import com.smile.service.SetmealService;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;

    @Reference
    private SetmealService setmealService;

    @Reference
    private ReportService reportService;

//    会员数量折线图数据
    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        Map<String,Object> map=new HashMap<>();
        List<String> months= new ArrayList();
        Calendar calendar=Calendar.getInstance(); // 获得日历对象，模拟时间就是当前时间
        //    计算过去一年的12个月
        calendar.add(Calendar.MONTH,-12);
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,1);
            Date date = calendar.getTime();
            months.add(new SimpleDateFormat("yyyy.MM").format(date));
        }
        map.put("months",months);
        List<Integer> memberCount = memberService.findMemberCountByMonths(months);
        map.put("memberCount",memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }

//    套餐预约占比饼形图
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        Map<String,Object> data=new HashMap<>();
        try{
            List<Map<String,Object>> setmealCount=setmealService.findSetmealCount();
            data.put("setmealCount",setmealCount);
            List<String> setmealNames=new ArrayList<>();
            for(Map<String,Object> map:setmealCount){
                String name = (String) map.get("name");
                setmealNames.add(name);
            }
            data.put("setmealNames",setmealNames);
            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,data);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }

    }

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        try {
            Map<String, Object> data = reportService.getBusinessReportData();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,data);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, Object> result = reportService.getBusinessReportData();
            //取出返回结果数据，准备将报表数据写入到Excel文件中
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

            //获得Excel模板文件绝对路径
//            File.separator可以解决win和Linux系统下的目录分隔符 \ /
            String filePath= request.getSession().getServletContext().getRealPath("template")+ File.separator+"report_template.xlsx";
            XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File(filePath)));
//            读取第一个工作表
            XSSFSheet sheet=excel.getSheetAt(0);
//            获得第三行
            XSSFRow row = sheet.getRow(2);
            XSSFCell cell=row.getCell(5);
            cell.setCellValue(reportDate);

            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数
            int rowNum = 12;
            for(Map map : hotSetmeal){//热门套餐
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum ++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }

            //通过输出流基于浏览器进行文件下载
            OutputStream out=response.getOutputStream();
            response.setContentType("application/vnd.ms-excel"); // 代表的是excel文件类型
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx"); // 指定以附件形式下载
            excel.write(out);
            out.flush();
            out.close();
            excel.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }
}
