package com.innotree.bcs.bp.study.jpa.demo.order.domain;

import com.innotree.bcs.bp.study.jpa.demo.delivery.domain.Delivery;
import com.innotree.bcs.bp.study.jpa.demo.delivery.domain.DeliveryStatus;
import com.innotree.bcs.bp.study.jpa.demo.item.domain.Book;
import com.innotree.bcs.bp.study.jpa.demo.item.domain.Item;
import com.innotree.bcs.bp.study.jpa.demo.member.domain.Address;
import com.innotree.bcs.bp.study.jpa.demo.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class OrderTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName(value = "연관관계 매핑 - 다대일 / 일대다")
    public void memberOrder() {

        Member member = createMember("lee");

        Order order = createOrder(member);

        em.flush();
        em.clear();

        Order findOrder = em.find(Order.class, order.getId());
        System.out.println("findOrder.getMember().getName() = " + findOrder.getMember().getName());

        int orderAmount = findOrder.getMember().getOrders().get(0).getOrderAmount();
        System.out.println("orderAmount = " + orderAmount);
        int size = findOrder.getMember().getOrders().size();
        System.out.println("size = " + size);
    }


    @Test
    @DisplayName(value = "연관관계 매핑 - 일대일")
    public void orderDelivery() {
        Member member = createMember("lee");
        Order order = createOrder(member);
        Delivery delivery = createDelivery(member);

        order.setDelivery(delivery);
        em.persist(order);
        em.flush();
        em.clear();

        Order findOrder = em.find(Order.class, order.getId());
        Long deliveryId = findOrder.getDelivery().getId();
        System.out.println("deliveryId = " + deliveryId);

        System.out.println("findOrder.getDelivery().getOrder() = " + findOrder.getDelivery().getOrder());
    }

    @Test
    @DisplayName("주문상품 추가")
    public void addOrderItem() {
        Member member = createMember("lee");
        Order order = createOrder(member);
        Delivery delivery = createDelivery(member);
        order.setDelivery(delivery);
        Book book1 = createItem("book1", 1000, 100);
        Book book2 = createItem("book2", 12000, 150);
        OrderItem orderAlbum =createOrderItem(order, book1);
        OrderItem orderBook = createOrderItem(order, book2);

        em.persist(order);
        em.flush();
        em.clear();
        System.out.println("===========================================");

        Order findOrder = em.find(Order.class, order.getId());
        System.out.println("findOrder.getDelivery() = " + findOrder.getDelivery());
        List<OrderItem> orderItems = findOrder.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            System.out.println("orderItem = " + orderItem);
        }

    }

    private OrderItem createOrderItem(Order order, Item item) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrder(order);
        orderItem.setCount(2);
        orderItem.setOrderPrice(item.getPrice() * orderItem.getCount());
        order.addOrderItem(orderItem);
        return orderItem;
    }

    private Book createItem(String name, int price, int stockQuantity) {
        Book item = new Book();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        item.setAuthor("저자");
        item.setIsbn("1234567890");
        em.persist(item);
        return item;
    }

    private Delivery createDelivery(Member member) {
        Delivery delivery = new Delivery();
        delivery.setDeliveryStatus(DeliveryStatus.READY);
        Address address = member.getAddress();
        delivery.setAddress(address);
        return delivery;
    }

    private Order createOrder(Member member) {
        Order order = new Order();
        order.setMember(member);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderAmount(2);
        order.setOrderStatus(OrderStatus.ORDER);
        em.persist(order);
        return order;
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        Address address = new Address("Seoul", "Teheran", "12345");
        member.setAddress(address);
        em.persist(member);
        return member;
    }
}