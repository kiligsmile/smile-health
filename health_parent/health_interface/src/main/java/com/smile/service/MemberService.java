package com.smile.service;

import com.smile.pojo.Member;

public interface MemberService {
    public void add(Member member);
    public Member findByTelephone(String telephone);
}
