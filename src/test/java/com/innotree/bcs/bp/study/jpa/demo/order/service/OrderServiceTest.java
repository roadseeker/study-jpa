package com.innotree.bcs.bp.study.jpa.demo.order.service;

import com.innotree.bcs.bp.study.jpa.demo.item.domain.Book;
import com.innotree.bcs.bp.study.jpa.demo.item.domain.Item;
import com.innotree.bcs.bp.study.jpa.demo.member.domain.Address;
import com.innotree.bcs.bp.study.jpa.demo.member.domain.Member;
import com.innotree.bcs.bp.study.jpa.demo.order.domain.Order;
import com.innotree.bcs.bp.study.jpa.demo.order.domain.OrderSearch;
import com.innotree.bcs.bp.study.jpa.demo.order.domain.OrderStatus;
import com.innotree.bcs.bp.study.jpa.demo.order.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName(value = "상품주문")
    public void createOrder() {
        Member member = createMember();
        Book item = createBook();
        int orderCount = 10;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        Order findOrder = orderRepository.findOne(orderId);

        assertThat(OrderStatus.ORDER).isEqualTo(findOrder.getStatus());
        assertThat(1).isEqualTo(findOrder.getOrderItems().size());
        assertThat(1000 * 10).isEqualTo(findOrder.getTotalPrice());
        assertThat(90).isEqualTo(item.getStockQuantity());
    }

    @Test
    @DisplayName(value = "주문취소")
    public void cancelOrder() {
        Member member = createMember();
        Item item = createBook();
        int orderCount = 10;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        orderService.cancelOrder(orderId);

        Order findOrder = orderRepository.findOne(orderId);

        assertThat(OrderStatus.CANCEL).isEqualTo(findOrder.getStatus());
        assertThat(100).isEqualTo(item.getStockQuantity());
    }
    @Test
    @DisplayName(value = "주문검색")
    public void orderSearch() {
        Member member = createMember();
        Item item = createBook();
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setOrderStatus(OrderStatus.ORDER);
        orderSearch.setMemberName("lee");
        int orderCount = 10;

        orderService.order(member.getId(), item.getId(), orderCount);
        List<Order> orders = orderService.findOrder(orderSearch);

        assertThat(1).isEqualTo(orders.size());

    }

    private Book createBook() {
        Book item = new Book();
        item.setStockQuantity(100);
        item.setPrice(1000);
        item.setName("Book");
        item.setIsbn("12345");
        item.setAuthor("kim");
        em.persist(item);
        return item;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("lee");
        Address address = new Address("서울", "테헤란로", "12345");
        member.setAddress(address);
        em.persist(member);
        return member;
    }
}
