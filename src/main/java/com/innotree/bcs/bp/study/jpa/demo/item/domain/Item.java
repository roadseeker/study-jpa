package com.innotree.bcs.bp.study.jpa.demo.item.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@TableGenerator(name = "ITEM_SEQ_GENERATOR", table = "MY_SEQUENCE", pkColumnValue = "ITEM_SEQ", allocationSize = 1)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {
    @Id @GeneratedValue(strategy = GenerationType.TABLE, generator = "ITEM_SEQ_GENERATOR")
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private int StockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", StockQuantity=" + StockQuantity +
                '}';
    }
}
