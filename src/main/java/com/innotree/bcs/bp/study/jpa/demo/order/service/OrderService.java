package com.innotree.bcs.bp.study.jpa.demo.order.service;

import com.innotree.bcs.bp.study.jpa.demo.delivery.domain.Delivery;
import com.innotree.bcs.bp.study.jpa.demo.delivery.domain.DeliveryStatus;
import com.innotree.bcs.bp.study.jpa.demo.item.domain.Item;
import com.innotree.bcs.bp.study.jpa.demo.item.repository.ItemRepository;
import com.innotree.bcs.bp.study.jpa.demo.member.domain.Member;
import com.innotree.bcs.bp.study.jpa.demo.member.repossitory.MemberRepository;
import com.innotree.bcs.bp.study.jpa.demo.order.domain.Order;
import com.innotree.bcs.bp.study.jpa.demo.order.domain.OrderItem;
import com.innotree.bcs.bp.study.jpa.demo.order.domain.OrderSearch;
import com.innotree.bcs.bp.study.jpa.demo.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId,  int count) {

        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.READY);
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrder(OrderSearch orderSearch) {
        return orderRepository.findByCriteria(orderSearch);

    }
}
