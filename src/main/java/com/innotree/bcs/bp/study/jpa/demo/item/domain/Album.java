package com.innotree.bcs.bp.study.jpa.demo.item.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "A")
@Getter @Setter
public class Album extends Item{
    private String writer;
    private String singer;
}
