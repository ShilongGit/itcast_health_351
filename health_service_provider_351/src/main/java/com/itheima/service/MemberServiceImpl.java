package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;

    @Override
    public void smsLogin(Map<String, String> map) {
        Member member = memberDao.selectMemberByTelephone(map.get("telephone"));

        if (member==null){
            member = new Member();
            member.setPhoneNumber(map.get("telephone"));
            member.setRegTime(new Date());
            memberDao.insertMember(member);
        }
    }
}
