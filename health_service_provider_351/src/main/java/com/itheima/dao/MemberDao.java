package com.itheima.dao;

import com.itheima.pojo.Member;

import java.util.Map;

public interface MemberDao {
    Member selectMemberByTelephone(String telephone);

    void insertMember(Member member);

    Long selectMemberCountByMonth(String s);


    Long selectSexCount(String s);

    long selectTodayNewMember(String reportDate);

    long selectTotalMember();

    long selectMemberWithStartEnd(String begin, String end);
}
