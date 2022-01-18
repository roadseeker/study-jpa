package com.innotree.bcs.bp.study.jpa.demo.order.domain;

import com.innotree.bcs.bp.study.jpa.demo.item.domain.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "order_item")
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    private int orderPrice;
    private int count;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    //==생성자 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 메서드==//
    /** 주문취소 */
    public void cancel() {
        getItem().addStock(count);
    }
    /** 주문상품 가격조회 */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}