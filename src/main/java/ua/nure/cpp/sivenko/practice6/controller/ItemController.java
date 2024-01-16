package ua.nure.cpp.sivenko.practice6.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.nure.cpp.sivenko.practice6.model.Item;
import ua.nure.cpp.sivenko.practice6.model.ItemCategory;
import ua.nure.cpp.sivenko.practice6.service.ItemCategoryService;
import ua.nure.cpp.sivenko.practice6.service.ItemService;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Controller
@Log
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemCategoryService itemCategoryService;

    @GetMapping("/items")
    public String getAllItems(Model model) {
        List<Item> items = itemService.getAllItems();
        model.addAttribute("items", items);

        List<ItemCategory> itemCategories = itemCategoryService.getAllItemCategories();
        model.addAttribute("itemCategories", itemCategories);
        return "items";
    }

    @GetMapping("/items/add")
    public String addItemForm(Model model) {
        Item item = new Item();
        model.addAttribute("item", item);

        List<ItemCategory> itemCategories = itemCategoryService.getAllItemCategories();
        model.addAttribute("itemCategories", itemCategories);
        return "add_item";
    }

    @PostMapping("/items")
    public String addItem(@ModelAttribute("item") Item item) {
        itemService.addItem(item);
        return "redirect:/items";
    }

    @GetMapping("/items/edit/{itemId}")
    public String updateItemForm(@PathVariable Long itemId, Model model) {
        Item item = itemService.getItemById(itemId);
        model.addAttribute("item", item);

        List<ItemCategory> itemCategories = itemCategoryService.getAllItemCategories();
        model.addAttribute("itemCategories", itemCategories);
        return "update_item";
    }

    @PostMapping("/items/{itemId}")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("item") Item item, Model model) {
        Item itemById = itemService.getItemById(itemId);
        if (!Objects.equals(itemById, item)) {
            itemService.updateItem(itemId, item);
        }
        return "redirect:/items";
    }

    @GetMapping("/items/{itemId}")
    public String deleteItem(@PathVariable Long itemId) {
        try {
            itemService.deleteItem(itemId);
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return "redirect:/items";
    }
}
