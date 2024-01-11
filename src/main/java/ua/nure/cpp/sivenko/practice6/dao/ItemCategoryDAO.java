package ua.nure.cpp.sivenko.practice6.dao;

import ua.nure.cpp.sivenko.practice6.model.ItemCategory;

import java.sql.SQLException;
import java.util.List;

public interface ItemCategoryDAO {
    ItemCategory getItemCategoryById(long itemCategoryId);

    List<ItemCategory> getAllItemCategories();

    void addItemCategory(ItemCategory itemCategory) throws SQLException;

    void updateItemCategoryName(long itemCategoryId, String itemCategoryName) throws SQLException;

    void deleteItemCategory(long itemCategoryId);
}
