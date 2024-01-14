package ua.nure.cpp.sivenko.practice6.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.nure.cpp.sivenko.practice6.DatabaseConfig;
import ua.nure.cpp.sivenko.practice6.dao.ItemDAO;
import ua.nure.cpp.sivenko.practice6.model.Item;
import ua.nure.cpp.sivenko.practice6.model.Item.ItemStatus;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemDAOMySQLImpl implements ItemDAO {
    private static final String GET_BY_ID = "SELECT * FROM items WHERE item_id = ?";
    private static final String GET_BY_CATEGORY = "SELECT * FROM items WHERE item_category = ?";
    private static final String GET_BY_STATUS = "SELECT * FROM items WHERE item_status = ?";
    private static final String GET_ALL = "SELECT * FROM items";

    private static final String INSERT = "INSERT INTO items (item_name, item_category, appraised_value) " +
            "VALUES (?, ?, ?)";
    private static final String UPDATE_APPRAISED_VALUE = "UPDATE items " +
            "SET appraised_value = ? WHERE item_id = ?";
    private static final String DELETE = "DELETE FROM items WHERE item_id = ?";

    @Autowired
    private DatabaseConfig databaseConfig;

    @Override
    public Item getItemById(long itemId) {
        try (Connection connection = databaseConfig.createConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BY_ID)) {
            ps.setLong(1, itemId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return mapItem(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Item> getItemsByCategory(long itemCategoryId) {
        List<Item> items = new ArrayList<>();

        try (Connection connection = databaseConfig.createConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BY_CATEGORY)) {
            ps.setLong(1, itemCategoryId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(mapItem(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

    @Override
    public List<Item> getItemsByStatus(ItemStatus itemStatus) {
        List<Item> items = new ArrayList<>();

        try (Connection connection = databaseConfig.createConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BY_STATUS)) {
            ps.setString(1, itemStatus.toString());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(mapItem(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();

        try (Connection connection = databaseConfig.createConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(GET_ALL)) {
            while (rs.next()) {
                items.add(mapItem(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

    @Override
    public void addItem(Item item) throws SQLException {
        try (Connection connection = databaseConfig.createConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT)) {
            ps.setString(1, item.getItemName());
            ps.setLong(2, item.getItemCategory());
            ps.setBigDecimal(3, item.getAppraisedValue());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void updateItemAppraisedValue(long itemId, BigDecimal appraisedValue) {
        try (Connection connection = databaseConfig.createConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_APPRAISED_VALUE)) {
            ps.setBigDecimal(1, appraisedValue);
            ps.setLong(2, itemId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteItem(long itemId) {
        try (Connection connection = databaseConfig.createConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setLong(1, itemId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Item mapItem(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setItemId(rs.getLong("item_id"));
        item.setItemName(rs.getString("item_name"));
        item.setItemCategory(rs.getLong("item_category"));
        item.setAppraisedValue(rs.getBigDecimal("appraised_value"));
        item.setMarketPriceMax(rs.getBigDecimal("market_price_max"));
        item.setMarketPriceMin(rs.getBigDecimal("market_price_min"));
        item.setItemStatus(ItemStatus.valueOf(rs.getString("item_status").toUpperCase()));
        return item;
    }
}
