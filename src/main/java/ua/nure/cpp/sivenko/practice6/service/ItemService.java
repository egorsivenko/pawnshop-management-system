package ua.nure.cpp.sivenko.practice6.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.cpp.sivenko.practice6.dao.ItemDAO;
import ua.nure.cpp.sivenko.practice6.model.Item;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Service
@Log
public class ItemService {

    @Autowired
    private ItemDAO itemDAO;

    public List<Item> getAllItems() {
        return itemDAO.getAllItems();
    }

    public Item getItemById(long itemId) {
        return itemDAO.getItemById(itemId);
    }

    public void addItem(Item item) {
        try {
            itemDAO.addItem(item);
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }

    public void deleteItem(long itemId) throws SQLException {
        Item itemById = itemDAO.getItemById(itemId);
        if (itemById == null) {
            throw new SQLException("Item with id '" + itemId + "' does not exists");
        }
        itemDAO.deleteItem(itemId);
    }

    public void updateItemAppraisedValue(long itemId, BigDecimal appraisedValue) {
        itemDAO.updateItemAppraisedValue(itemId, appraisedValue);
    }
}
