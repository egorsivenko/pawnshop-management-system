package ua.nure.cpp.sivenko.practice6.dao;

import ua.nure.cpp.sivenko.practice6.model.Item;

import java.util.List;

public interface ItemDAO {
    Item getItemById(long itemId);

    List<Item> getAllItems();

    void addItem(Item item);

    void updateItem(long itemId, Item item);

    void deleteItem(long itemId);
}
