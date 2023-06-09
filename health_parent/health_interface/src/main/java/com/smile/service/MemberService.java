package com.smile.service;

import com.smile.pojo.Member;

import java.util.List;

public interface MemberService {
    public void add(Member member);
    public Member findByTelephone(String telephone);
    public List<Integer> findMemberCountByMonths(List<String> months);
}
