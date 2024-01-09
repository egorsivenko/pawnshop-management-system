package ua.nure.cpp.sivenko.practice6.dao;

import ua.nure.cpp.sivenko.practice6.model.ItemCategory;

import java.util.List;

public interface ItemCategoryDAO {
    ItemCategory getItemCategoryById(long itemCategoryId);

    List<ItemCategory> getAllItemCategories();

    void addItemCategory(ItemCategory itemCategory);

    void updateItemCategoryName(long itemCategoryId, String itemCategoryName);

    void deleteItemCategory(long itemCategoryId);
}
