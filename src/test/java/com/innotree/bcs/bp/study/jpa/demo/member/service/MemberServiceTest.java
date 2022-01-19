package com.innotree.bcs.bp.study.jpa.demo.member.service;

import com.innotree.bcs.bp.study.jpa.demo.member.domain.Member;
import com.innotree.bcs.bp.study.jpa.demo.member.repossitory.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName(value = "회원가입")
    public void join() {
        Member member = new Member();
        member.setName("lee");
        Long memberId = memberService.join(member);
        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(memberId));
    }
}