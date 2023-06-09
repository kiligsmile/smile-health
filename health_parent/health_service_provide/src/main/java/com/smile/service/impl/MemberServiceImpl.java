package com.smile.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.smile.dao.MemberDao;
import com.smile.pojo.Member;
import com.smile.service.MemberService;
import com.smile.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;
    //根据手机号查询会员
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }
    //新增会员
    public void add(Member member) {
        if(member.getPassword() != null){
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }

//    根据月份查询会员数量
    public List<Integer> findMemberCountByMonths(List<String> months) {  // 2018.05
        List<Integer> memberCount=new ArrayList<>();
        for (String month : months) {
//            该问题未解决
            String date= month+".28"; // 2018.05.31
            Integer count = memberDao.findMemberCountBeforeDate(date);
            memberCount.add(count);
        }
        return memberCount;
    }
}
