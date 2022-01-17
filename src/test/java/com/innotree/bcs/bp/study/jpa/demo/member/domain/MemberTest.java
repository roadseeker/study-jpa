package com.innotree.bcs.bp.study.jpa.demo.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {
    @Autowired
    EntityManager em;

    @Test
    @DisplayName(value = "회원 생성, 조회, 수정, 삭제")
    public void memberEntity() {
        Member member = new Member();
        member.setName("lee");
        Address address = new Address("Seoul", "Teheran", "12345");
        member.setAddress(address);
        em.persist(member);
        member.setName("kim");
        em.flush();
        em.clear();

        Member findMember = em.find(Member.class, member.getId());
        System.out.println("findMember = " + findMember);

        em.flush();
        em.remove(findMember);
    }
}