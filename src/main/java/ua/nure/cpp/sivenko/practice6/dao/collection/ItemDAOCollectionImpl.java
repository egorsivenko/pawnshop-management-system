package ua.nure.cpp.sivenko.practice6.dao.collection;

import ua.nure.cpp.sivenko.practice6.dao.ItemDAO;
import ua.nure.cpp.sivenko.practice6.model.Item;
import ua.nure.cpp.sivenko.practice6.model.Item.ItemStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ItemDAOCollectionImpl implements ItemDAO {
    private final List<Item> items = new ArrayList<>();
    private final AtomicInteger id = new AtomicInteger(1);

    public ItemDAOCollectionImpl() {
        addItem(new Item(12, "Electronic Steel Fish", 13, BigDecimal.valueOf(810),
                BigDecimal.valueOf(486), BigDecimal.valueOf(202.50), ItemStatus.PAWNED));
        addItem(new Item(5, "Sleek Fresh Chair", 12, BigDecimal.valueOf(774),
                BigDecimal.valueOf(464.40), BigDecimal.valueOf(193.50), ItemStatus.PAWNED));
        addItem(new Item(23, "Unbranded Rubber Chicken", 7, BigDecimal.valueOf(236),
                BigDecimal.valueOf(141.60), BigDecimal.valueOf(59), ItemStatus.PAWNED));
    }

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
                .filter(item -> item.getItemCategory() == itemCategoryId)
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
    public void updateItemAppraisedValue(long itemId, BigDecimal appraisedValue) {
        items.get((int) itemId - 1).setAppraisedValue(appraisedValue);
    }

    @Override
    public void deleteItem(long itemId) {
        items.remove((int) itemId - 1);
    }
}
