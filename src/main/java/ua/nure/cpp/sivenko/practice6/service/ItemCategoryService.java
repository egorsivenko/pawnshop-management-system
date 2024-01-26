package ua.nure.cpp.sivenko.practice6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.cpp.sivenko.practice6.dao.ItemCategoryDAO;
import ua.nure.cpp.sivenko.practice6.model.ItemCategory;

import java.sql.SQLException;
import java.util.List;

@Service
public class ItemCategoryService {

    @Autowired
    private ItemCategoryDAO itemCategoryDAO;

    public List<ItemCategory> getAllItemCategories() {
        return itemCategoryDAO.getAllItemCategories();
    }

    public ItemCategory getItemCategoryById(long itemCategoryId) {
        return itemCategoryDAO.getItemCategoryById(itemCategoryId);
    }

    public void addItemCategory(ItemCategory itemCategory) throws SQLException {
        List<ItemCategory> itemCategories = itemCategoryDAO.getAllItemCategories();

        for (var category : itemCategories) {
            if (category.getItemCategoryName().equalsIgnoreCase(itemCategory.getItemCategoryName().strip())) {
                throw new SQLException("Item Category with name '" + itemCategory.getItemCategoryName().strip() + "' already exists");
            }
        }
        itemCategoryDAO.addItemCategory(itemCategory);
    }

    public void deleteItemCategory(long itemCategoryId) throws SQLException {
        ItemCategory itemCategoryById = itemCategoryDAO.getItemCategoryById(itemCategoryId);
        if (itemCategoryById == null) {
            throw new SQLException("Item Category with id '" + itemCategoryId + "' does not exists");
        }
        itemCategoryDAO.deleteItemCategory(itemCategoryId);
    }

    public void updateItemCategoryName(long itemCategoryId, String itemCategoryName) throws SQLException {
        List<ItemCategory> itemCategories = itemCategoryDAO.getAllItemCategories();

        for (var category : itemCategories) {
            if (category.getItemCategoryName().equalsIgnoreCase(itemCategoryName.strip())) {
                throw new SQLException("Item Category with name '" + itemCategoryName.strip() + "' already exists");
            }
        }
        itemCategoryDAO.updateItemCategoryName(itemCategoryId, itemCategoryName);
    }
}
