package ua.nure.cpp.sivenko.practice6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.nure.cpp.sivenko.practice6.model.ItemCategory;
import ua.nure.cpp.sivenko.practice6.service.ItemCategoryService;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Controller
public class ItemCategoryController {

    @Autowired
    private ItemCategoryService itemCategoryService;

    @GetMapping("/itemCategories")
    public String getAllItemCategories(Model model) {
        List<ItemCategory> itemCategories = itemCategoryService.getAllItemCategories();
        model.addAttribute("itemCategories", itemCategories);
        return "itemCategory/itemCategories";
    }

    @GetMapping("/itemCategories/add")
    public String addItemCategoryForm(Model model) {
        ItemCategory itemCategory = new ItemCategory();
        model.addAttribute("itemCategory", itemCategory);
        return "itemCategory/add_itemCategory";
    }

    @PostMapping("/itemCategories")
    public String addItemCategory(@ModelAttribute("itemCategory") ItemCategory itemCategory, Model model) {
        try {
            itemCategoryService.addItemCategory(itemCategory);
        } catch (SQLException e) {
            model.addAttribute("error", e.getMessage());
            return "itemCategory/add_itemCategory";
        }
        return "redirect:/itemCategories";
    }

    @GetMapping("/itemCategories/edit/{itemCategoryId}")
    public String updateItemCategoryForm(@PathVariable Long itemCategoryId, Model model) {
        ItemCategory itemCategory = itemCategoryService.getItemCategoryById(itemCategoryId);
        model.addAttribute("itemCategory", itemCategory);
        return "itemCategory/update_itemCategory";
    }

    @PostMapping("/itemCategories/{itemCategoryId}")
    public String updateItemCategory(@PathVariable Long itemCategoryId, @ModelAttribute("itemCategory") ItemCategory itemCategory, Model model) {
        try {
            ItemCategory itemCategoryById = itemCategoryService.getItemCategoryById(itemCategoryId);
            if (!Objects.equals(itemCategoryById.getItemCategoryName(), itemCategory.getItemCategoryName())) {
                itemCategoryService.updateItemCategoryName(itemCategoryId, itemCategory.getItemCategoryName());
            }
        } catch (SQLException e) {
            model.addAttribute("error", e.getMessage());
            return "itemCategory/update_itemCategory";
        }
        return "redirect:/itemCategories";
    }

    @GetMapping("/itemCategories/{itemCategoryId}")
    public String deleteItemCategory(@PathVariable Long itemCategoryId, Model model) {
        try {
            itemCategoryService.deleteItemCategory(itemCategoryId);
        } catch (SQLException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/itemCategories";
    }
}
