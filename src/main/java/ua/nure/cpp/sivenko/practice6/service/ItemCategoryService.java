package ua.nure.cpp.sivenko.practice6.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.cpp.sivenko.practice6.dao.ItemCategoryDAO;
import ua.nure.cpp.sivenko.practice6.model.ItemCategory;

import java.sql.SQLException;
import java.util.List;

@Service
@Log
public class ItemCategoryService {

    @Autowired
    private ItemCategoryDAO itemCategoryDAO;

    public List<ItemCategory> getAllItemCategories() {
        return itemCategoryDAO.getAllItemCategories();
    }

    public ItemCategory getItemCategoryById(long itemCategoryId) {
        return itemCategoryDAO.getItemCategoryById(itemCategoryId);
    }

    public void addItemCategory(ItemCategory itemCategory) {
        try {
            itemCategoryDAO.addItemCategory(itemCategory);
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }

    public void deleteItemCategory(long itemCategoryId) throws SQLException {
        ItemCategory itemCategoryById = itemCategoryDAO.getItemCategoryById(itemCategoryId);
        if (itemCategoryById == null) {
            throw new SQLException("Item Category with id '" + itemCategoryId + "' does not exists");
        }
        itemCategoryDAO.deleteItemCategory(itemCategoryId);
    }

    public void updateItemCategory(long itemCategoryId, String itemCategoryName) {
        try {
            itemCategoryDAO.updateItemCategoryName(itemCategoryId, itemCategoryName);
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }
}
