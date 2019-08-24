package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.annotation.Log;
import com.itheima.entity.Result;
import com.itheima.service.ReportService;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Reference
    ReportService reportService;

    @ResponseBody
    @RequestMapping("/getMemberReport")
    @Log(operationType = "查询操作",operationName = "查找会员")
    public Result getMemberReport(@RequestBody Map<String,String> param){
        try {

            Map<String,Object> resultMap = reportService.findMemberReport(param);
            return new Result(true,"",resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"");
        }
    }

    @ResponseBody
    @RequestMapping("/getSexReport")
    @Log(operationType = "查询操作",operationName = "查询会员性别")
    public Result getSexReport(){
        try {

            Map<String,Object> result = reportService.findSexReport();
            return new Result(true,"",result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"");
        }
    }

    @ResponseBody
    @RequestMapping("/getSetmealReport")
    @Log(operationType = "查询操作",operationName = "查询会员套餐占比")
    public Result getSetmealReport(){
        try {

            Map<String,Object> result = reportService.getSetmealReport();
            return new Result(true,"",result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"");
        }
    }

    @ResponseBody
    @RequestMapping("/getBusinessReportData")
    @Log(operationType = "查询操作",operationName = "流量统计")
    public Result getBusinessReportData(){
        try {

            Map<String,Object> result = reportService.getBusinessReportData();
            return new Result(true,"",result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"");
        }
    }

    @ResponseBody
    @RequestMapping("/exportBusinessReport")
    @Log(operationType = "导出操作",operationName = "导出会员流量数据表")
    public Result exportBusinessReport(HttpServletResponse response){
        try {
            Map<String,Object> map = reportService.getBusinessReportData();

            InputStream resourceAsStream = this.getClass().getResourceAsStream("/report_template.xlsx");

            XSSFWorkbook workbook = new XSSFWorkbook(resourceAsStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            //设置统计日期
            String reportDate = String.valueOf(map.get("reportDate"));
            //获取第三行
            XSSFRow row = sheet.getRow(2);
            //获取第5个单元格
            XSSFCell cell = row.getCell(5);
            cell.setCellValue(reportDate);
            //todayNewMember :0,  今日新增会员数
            //设置今日新增会员数
            String todayNewMember = String.valueOf(map.get("todayNewMember"));
            //获取第三行
            row = sheet.getRow(4);
            //获取第6个单元格
            cell = row.getCell(5);
            cell.setCellValue(todayNewMember);

            //totalMember :0,     会员总数
            //设置会员总数
            String totalMember = String.valueOf(map.get("totalMember"));
            //获取第五行
            row = sheet.getRow(4);
            //获取第8个单元格
            cell = row.getCell(7);
            cell.setCellValue(totalMember);

            //thisWeekNewMember :0,   本周新增会员数（周一以后新增了多少会员）
            //设置会员总数
            String thisWeekNewMember = String.valueOf(map.get("thisWeekNewMember"));
            //获取第六行
            row = sheet.getRow(5);
            //获取第6个单元格
            cell = row.getCell(5);
            cell.setCellValue(thisWeekNewMember);
            //thisMonthNewMember :0,  本月新增会员数（1号以后新增了多少会员）
            String thisMonthNewMember = String.valueOf(map.get("thisMonthNewMember"));
            //获取第6行
            row = sheet.getRow(5);
            //获取第8个单元格
            cell = row.getCell(7);
            cell.setCellValue(thisMonthNewMember);
            //todayOrderNumber :0,    今日预约数（预约今天来体检的人数）
            String todayOrderNumber = String.valueOf(map.get("todayOrderNumber"));
            //获取第8行
            row = sheet.getRow(7);
            //获取第6个单元格
            cell = row.getCell(5);
            cell.setCellValue(todayOrderNumber);
            //todayVisitsNumber :0,   今日到诊数（今天实际有多少人体检）
            String todayVisitsNumber = String.valueOf(map.get("todayVisitsNumber"));
            //获取第8行
            row = sheet.getRow(7);
            //获取第8个单元格
            cell = row.getCell(7);
            cell.setCellValue(todayVisitsNumber);
            //thisWeekOrderNumber :0, 本周预约数（预约本周（周一~~~周日）来体检的人数）
            String thisWeekOrderNumber = String.valueOf(map.get("thisWeekOrderNumber"));
            //获取第9行
            row = sheet.getRow(8);
            //获取第6个单元格
            cell = row.getCell(5);
            cell.setCellValue(thisWeekOrderNumber);
            //thisWeekVisitsNumber :0, 本周到诊数（本周（周一以后）实际来了多少人）
            String thisWeekVisitsNumber = String.valueOf(map.get("thisWeekVisitsNumber"));
            //获取第9行
            row = sheet.getRow(8);
            //获取第8个单元格
            cell = row.getCell(7);
            cell.setCellValue(thisWeekVisitsNumber);
            //thisMonthOrderNumber :0,  本月预约数（预约本月（1号--月底）来体检的人数）
            String thisMonthOrderNumber = String.valueOf(map.get("thisMonthOrderNumber"));
            //获取第10行
            row = sheet.getRow(9);
            //获取第6个单元格
            cell = row.getCell(5);
            cell.setCellValue(thisMonthOrderNumber);
            //thisMonthVisitsNumber :0, 本月到诊数(本月1号以后实际来了多少人)
            String thisMonthVisitsNumber = String.valueOf(map.get("thisMonthVisitsNumber"));
            //获取第10行
            row = sheet.getRow(9);
            //获取第8个单元格
            cell = row.getCell(7);
            cell.setCellValue(thisMonthVisitsNumber);
            //hotSetmeal （热门套餐）:
            //获取热门套餐
            List<Map<String,Object>> hotSetmeals = (List<Map<String, Object>>) map.get("hotSetmeal");
            int rowNum = 12;
            for (Map<String, Object> hotSetmeal : hotSetmeals) {
                //获取行
                row = sheet.getRow(rowNum);
                //获取单元格
                cell = row.getCell(4);
                cell.setCellValue(String.valueOf(hotSetmeal.get("name")));
                //获取单元格
                cell = row.getCell(5);
                cell.setCellValue(String.valueOf(hotSetmeal.get("setmeal_count")));
                //获取单元格
                cell = row.getCell(6);
                cell.setCellValue(String.valueOf(hotSetmeal.get("proportion")));

                rowNum ++;
            }

            // 通过输出流进行文件下载
            ServletOutputStream out = response.getOutputStream();
            //设置输入的文件的类型
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition","attachment;filename="+"report.xlsx");
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
