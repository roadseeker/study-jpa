package com.innotree.bcs.bp.study.jpa.demo.web.member;

import com.innotree.bcs.bp.study.jpa.demo.member.domain.Address;
import com.innotree.bcs.bp.study.jpa.demo.member.domain.Member;
import com.innotree.bcs.bp.study.jpa.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createMemberForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String createMember(@Valid MemberForm form, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        Member member = new Member();
        member.setName(form.getName());
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        member.setAddress(address);
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String listMember(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
