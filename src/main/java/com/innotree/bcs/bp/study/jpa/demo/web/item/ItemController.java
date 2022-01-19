package com.innotree.bcs.bp.study.jpa.demo.web.item;

import com.innotree.bcs.bp.study.jpa.demo.item.domain.Book;
import com.innotree.bcs.bp.study.jpa.demo.item.domain.Item;
import com.innotree.bcs.bp.study.jpa.demo.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping(value = "/items/new")
    public String createItemForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping(value = "/items/new")
    public String createItem(BookForm form, Model model) {
        Book item = new Book();
        item.setStockQuantity(form.getStockQuantity());
        item.setName(form.getName());
        item.setIsbn(form.getIsbn());
        item.setPrice(form.getPrice());
        item.setAuthor(form.getAuthor());

        itemService.saveItem(item);
        return "redirect:/";
    }

    @GetMapping(value = "/items")
    public String itemList(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping(value = "/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book)itemService.findOne(itemId);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        BookForm form = mapper.map(item, BookForm.class);
        model.addAttribute("form", form);
        return "items/updateForm";
    }

    @PostMapping(value = "items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form, Model model) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Book book = mapper.map(form, Book.class);
        itemService.saveItem(book);
        return "redirect:/items";
    }
}
