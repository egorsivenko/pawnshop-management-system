package ua.nure.cpp.sivenko.practice6.dao.collection;

import ua.nure.cpp.sivenko.practice6.dao.ItemDAO;
import ua.nure.cpp.sivenko.practice6.model.Item;
import ua.nure.cpp.sivenko.practice6.model.Item.ItemStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ItemDAOCollectionImpl implements ItemDAO {
    private final List<Item> items = new ArrayList<>();
    private final AtomicInteger id = new AtomicInteger(1);

    @Override
    public Item getItemById(long itemId) {
        return items.stream()
                .filter(item -> item.getItemId() == itemId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Item> getItemsByCategory(long itemCategoryId) {
        return items.stream()
                .filter(item -> item.getItemCategoryId() == itemCategoryId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> getItemsByStatus(ItemStatus itemStatus) {
        return items.stream()
                .filter(item -> item.getItemStatus() == itemStatus)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> getAllItems() {
        return items;
    }

    @Override
    public void addItem(Item item) {
        item.setItemId(id.getAndIncrement());
        items.add(item);
    }

    @Override
    public void updateItem(long itemId, Item item) {
        items.stream()
                .filter(i -> i.getItemId() == itemId)
                .findFirst()
                .ifPresent(i -> {
                    int index = items.indexOf(i);
                    items.set(index, item);
                });
    }

    @Override
    public void deleteItem(long itemId) {
        items.removeIf(item -> item.getItemId() == itemId);
    }
}
