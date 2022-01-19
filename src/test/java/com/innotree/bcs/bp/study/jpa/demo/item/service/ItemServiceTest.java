package com.innotree.bcs.bp.study.jpa.demo.item.service;

import com.innotree.bcs.bp.study.jpa.demo.exception.NotEnoughStockException;
import com.innotree.bcs.bp.study.jpa.demo.item.domain.Book;
import com.innotree.bcs.bp.study.jpa.demo.item.domain.Item;
import com.innotree.bcs.bp.study.jpa.demo.item.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {
    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName(value = "상품등록")
    public void saveItem() {
        Item item = new Book();
        item.setName("book");
        item.setPrice(1000);
        item.setStockQuantity(100);

        Long itemId = itemService.saveItem(item);

        Assertions.assertThat(item).isEqualTo(itemRepository.findOne(itemId));
    }

    @Test
    @DisplayName(value = "재고부족 예외발생")
    public void itemStockException() {
        Item item = new Book();
        item.setName("book");
        item.setPrice(1000);
        item.setStockQuantity(100);

        Long itemId = itemService.saveItem(item);

        org.junit.jupiter.api.Assertions.assertThrows(NotEnoughStockException.class,
                () -> itemRepository.findOne(item.getId()).removeStock(101));

    }

}