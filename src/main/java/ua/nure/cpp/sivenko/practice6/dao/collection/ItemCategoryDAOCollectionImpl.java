package ua.nure.cpp.sivenko.practice6.dao.collection;

import ua.nure.cpp.sivenko.practice6.dao.ItemCategoryDAO;
import ua.nure.cpp.sivenko.practice6.model.ItemCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ItemCategoryDAOCollectionImpl implements ItemCategoryDAO {
    private final List<ItemCategory> itemCategories = new ArrayList<>();
    private final AtomicInteger id = new AtomicInteger(1);

    @Override
    public ItemCategory getItemCategoryById(long itemCategoryId) {
        return itemCategories.stream()
                .filter(itemCategory -> itemCategory.getItemCategoryId() == itemCategoryId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<ItemCategory> getAllItemCategories() {
        return itemCategories;
    }

    @Override
    public void addItemCategory(ItemCategory itemCategory) {
        itemCategory.setItemCategoryId(id.getAndIncrement());
        itemCategories.add(itemCategory);
    }

    @Override
    public void updateItemCategoryName(long itemCategoryId, String itemCategoryName) {
        itemCategories.stream()
                .filter(itemCategory -> itemCategory.getItemCategoryId() == itemCategoryId)
                .findFirst()
                .ifPresent(itemCategory -> itemCategory.setItemCategoryName(itemCategoryName));
    }

    @Override
    public void deleteItemCategory(long itemCategoryId) {
        itemCategories.removeIf(itemCategory -> itemCategory.getItemCategoryId() == itemCategoryId);
    }
}
