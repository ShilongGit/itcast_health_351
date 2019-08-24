package com.itheima.service;

import com.itheima.pojo.SexReport;

import java.util.List;
import java.util.Map;

public interface ReportService {
    Map<String,Object> findMemberReport(Map<String,String> param);

    Map<String,Object> findSexReport();

    Map<String,Object> getSetmealReport();

    Map<String,Object> getBusinessReportData() throws Exception;
}
