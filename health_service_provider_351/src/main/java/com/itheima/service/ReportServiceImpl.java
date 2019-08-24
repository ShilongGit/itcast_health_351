package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.ReportDao;
import com.itheima.utils.Date2Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportDao reportDao;

    @Autowired
    MemberDao memberDao;

    @Autowired
    OrderDao orderDao;

    @Override
    public Map<String, Object> findMemberReport(Map<String,String> param) {

        Map<String, Object> resultMap = new HashMap<>();

        List<String> months = getLastYearMonths(param.get("startTime"),param.get("endTime"));

        List<Long> counts = new ArrayList<>();

        resultMap.put("months",months);

        for (String month : months) {
            Long count = memberDao.selectMemberCountByMonth(month+"31");
            counts.add(count);
        }

        resultMap.put("memberCount",counts);

        return resultMap;
    }

    @Override
    public Map<String,Object> findSexReport() {



        Long man = memberDao.selectSexCount("1");
        Long woman = memberDao.selectSexCount("2");

        Map<String,Object> resultMap = new HashMap<>();

        resultMap.put("man",man);
        resultMap.put("woman",woman);

        return resultMap;
    }

    @Override
    public Map<String, Object> getSetmealReport() {

        Map<String, Object> resultMap = new HashMap<>();

        List<String> setmealNames = new ArrayList<>();

        List<Map<String,Object>> setmealCount = reportDao.getSetmealReport();

        for (Map<String, Object> setmeal : setmealCount) {
            String name = (String) setmeal.get("name");
            setmealNames.add(name);
        }

        resultMap.put("setmealNames",setmealNames);

        resultMap.put("setmealCount",setmealCount);

        return resultMap;
    }

    @Override
    public Map<String,Object> getBusinessReportData() throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

//        reportDate:null,
        String reportDate = Date2Utils.parseDate2String(new Date());
        resultMap.put("reportDate",reportDate);
//        todayNewMember :0,
        long todayNewMember = memberDao.selectTodayNewMember(reportDate);
        resultMap.put("todayNewMember",todayNewMember);
//        totalMember :0,
        long totalMember = memberDao.selectTotalMember();
        resultMap.put("totalMember",totalMember);
//        thisWeekNewMember :0,
        Date monday = Date2Utils.getFirstDayOfWeek(new Date());
        String firstDayOfWeek = Date2Utils.parseDate2String(monday);
        long thisWeekNewMember = memberDao.selectMemberWithStartEnd(firstDayOfWeek,reportDate);
        resultMap.put("thisWeekNewMember",thisWeekNewMember);
//        thisMonthNewMember :0,
        Date firstDay4ThisMonth = Date2Utils.getFirstDay4ThisMonth();
        String firstDay = Date2Utils.parseDate2String(firstDay4ThisMonth);
        long thisMonthNewMember = memberDao.selectMemberWithStartEnd(firstDay,reportDate);
        resultMap.put("thisMonthNewMember",thisMonthNewMember);
//        todayOrderNumber :0,
        long todayOrderNumber = orderDao.selectTodayOrderNumber(reportDate);
        resultMap.put("todayOrderNumber",todayOrderNumber);
//        todayVisitsNumber :0,
        long todayVisitsNumber = orderDao.selectTodayVisitsNumber(reportDate);
        resultMap.put("todayVisitsNumber",todayVisitsNumber);
//        thisWeekOrderNumber :0,
        Date lastDayOfWeek = Date2Utils.getLastDayOfWeek(new Date());
        String lastWeekDay = Date2Utils.parseDate2String(lastDayOfWeek);
        long thisWeekOrderNumber = orderDao.selectOrderNumberWithStartEnd(firstDayOfWeek,lastWeekDay);
        resultMap.put("thisWeekOrderNumber",thisWeekOrderNumber);
//        thisWeekVisitsNumber :0,
        long thisWeekVisitsNumber = orderDao.selectVisitsNumber(firstDayOfWeek);
        resultMap.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
//        thisMonthOrderNumber :0,
        Date lastDay4ThisMonth = Date2Utils.getLastDay4ThisMonth();
        String lastDay = Date2Utils.parseDate2String(lastDay4ThisMonth);
        long thisMonthOrderNumber = orderDao.selectOrderNumberWithStartEnd(firstDay,lastDay);
        resultMap.put("thisMonthOrderNumber",thisMonthOrderNumber);
//        thisMonthVisitsNumber :0,
        long thisMonthVisitsNumber = orderDao.selectVisitsNumber(firstDay);
        resultMap.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
//        hotSetmeal :[
//        {name:'阳光爸妈升级肿瘤12项筛查（男女单人）体检套餐',setmeal_count:200,proportion:0.222},
//        {name:'阳光爸妈升级肿瘤12项筛查体检套餐',setmeal_count:200,proportion:0.222}]

        List<Map<String,Object>> hotSetmeal = reportDao.selectHotSetmeal();
        resultMap.put("hotSetmeal",hotSetmeal);

        return resultMap;
    }


    private List<String> getLastYearMonths(String begin,String end){

        try {
            Date startDate = new SimpleDateFormat("yyyy-MM").parse(begin);
            Date endDate = new SimpleDateFormat("yyyy-MM").parse(end);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            // 获取开始年份和开始月份
            int startYear = calendar.get(Calendar.YEAR);
            int startMonth = calendar.get(Calendar.MONTH);
            // 获取结束年份和结束月份
            calendar.setTime(endDate);
            int endYear = calendar.get(Calendar.YEAR);
            int endMonth = calendar.get(Calendar.MONTH);
            //
            List<String> list = new ArrayList<String>();
            for (int i = startYear; i <= endYear; i++) {
                String date = "";
                if (startYear == endYear) {
                    for (int j = startMonth; j <= endMonth; j++) {
                        if (j < 9) {
                            date = i + "-0" + (j + 1);
                        } else {
                            date = i + "-" + (j + 1);
                        }
                        list.add(date);
                    }

                } else {
                    if (i == startYear) {
                        for (int j = startMonth; j < 12; j++) {
                            if (j < 9) {
                                date = i + "-0" + (j + 1);
                            } else {
                                date = i + "-" + (j + 1);
                            }
                            list.add(date);
                        }
                    } else if (i == endYear) {
                        for (int j = 0; j <= endMonth; j++) {
                            if (j < 9) {
                                date = i + "-0" + (j + 1);
                            } else {
                                date = i + "-" + (j + 1);
                            }
                            list.add(date);
                        }
                    } else {
                        for (int j = 0; j < 12; j++) {
                            if (j < 9) {
                                date = i + "-0" + (j + 1);
                            } else {
                                date = i + "-" + (j + 1);
                            }
                            list.add(date);
                        }
                    }

                }

            }

            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
