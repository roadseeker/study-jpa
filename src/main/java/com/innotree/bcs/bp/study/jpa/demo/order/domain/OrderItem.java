package com.innotree.bcs.bp.study.jpa.demo.order.domain;

import com.innotree.bcs.bp.study.jpa.demo.item.domain.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    private int orderPrice;
    private int count;

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", item=" + item +
                ", orderPrice=" + orderPrice +
                ", count=" + count +
                '}';
    }
}
