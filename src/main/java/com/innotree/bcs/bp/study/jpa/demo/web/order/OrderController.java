package com.innotree.bcs.bp.study.jpa.demo.web.order;

import com.innotree.bcs.bp.study.jpa.demo.item.domain.Item;
import com.innotree.bcs.bp.study.jpa.demo.item.service.ItemService;
import com.innotree.bcs.bp.study.jpa.demo.member.domain.Member;
import com.innotree.bcs.bp.study.jpa.demo.member.service.MemberService;
import com.innotree.bcs.bp.study.jpa.demo.order.domain.Order;
import com.innotree.bcs.bp.study.jpa.demo.order.domain.OrderSearch;
import com.innotree.bcs.bp.study.jpa.demo.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping(value = "/order")
    public String createOrderForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/createOrderForm";
    }

    @PostMapping(value = "/order")
    public String createOrder(@RequestParam("memberId") Long memberId,
                              @RequestParam("itemId") Long itemId,
                              @RequestParam("count") int count) {
        Long orderId = orderService.order(memberId, itemId, count);
        return "redirect:/orders";

    }

    @GetMapping(value = "/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrder(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @PostMapping(value = "/orders/{orderId}/cancel")
    public String orderCancel(@PathVariable("orderId") Long orderId, Model model) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }

}
