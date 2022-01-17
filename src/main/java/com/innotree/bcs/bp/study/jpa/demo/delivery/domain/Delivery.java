package com.innotree.bcs.bp.study.jpa.demo.delivery.domain;

import com.innotree.bcs.bp.study.jpa.demo.member.domain.Address;
import com.innotree.bcs.bp.study.jpa.demo.order.domain.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;
//    private String city;
//    private String street;
//    private String zipcode;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", order=" + order +
                ", address=" + address +
                ", deliveryStatus=" + deliveryStatus +
                '}';
    }
}
