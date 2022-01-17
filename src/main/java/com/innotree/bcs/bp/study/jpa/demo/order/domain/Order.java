package com.innotree.bcs.bp.study.jpa.demo.order.domain;

import com.innotree.bcs.bp.study.jpa.demo.delivery.domain.Delivery;
import com.innotree.bcs.bp.study.jpa.demo.member.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    private int orderPrice;
    private int orderAmount;
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public void setMember(Member member) {
        if(this.member != null) {
            this.member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderPrice=" + orderPrice +
                ", orderAmount=" + orderAmount +
                ", orderDate=" + orderDate +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
