package ua.nure.cpp.sivenko.practice6.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.nure.cpp.sivenko.practice6.DatabaseConfig;
import ua.nure.cpp.sivenko.practice6.dao.ItemCategoryDAO;
import ua.nure.cpp.sivenko.practice6.model.ItemCategory;
import ua.nure.cpp.sivenko.practice6.model.Pawnbroker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemCategoryDAOMySQLImpl implements ItemCategoryDAO {
    private static final String GET_BY_ID = "SELECT * FROM item_categories WHERE item_category_id = ?";
    private static final String GET_ALL = "SELECT * FROM item_categories ORDER BY item_category_id";

    private static final String INSERT = "INSERT INTO item_categories(item_category_name) VALUES (?)";
    private static final String UPDATE = "UPDATE item_categories " +
            "SET item_category_name = ? WHERE item_category_id = ?";
    private static final String DELETE = "DELETE FROM item_categories WHERE item_category_id = ?";

    private static final String SELECT_PAWNBROKER_SPECIALIZATION = "SELECT * FROM pawnbroker_specialization ps " +
            "JOIN pawnbrokers p ON ps.pawnbroker_id = p.pawnbroker_id WHERE specialization = ?";
    private static final String INSERT_PAWNBROKER_SPECIALIZATION = "INSERT INTO pawnbroker_specialization VALUES (?, ?)";

    @Autowired
    private DatabaseConfig databaseConfig;

    @Override
    public ItemCategory getItemCategoryById(long itemCategoryId) {
        try (Connection connection = databaseConfig.createConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            try (PreparedStatement ps = connection.prepareStatement(GET_BY_ID);
                 PreparedStatement ps_pawn_spec = connection.prepareStatement(SELECT_PAWNBROKER_SPECIALIZATION)) {
                ps.setLong(1, itemCategoryId);
                ItemCategory itemCategory = getItemCategory(ps);

                if (itemCategory != null) {
                    ps_pawn_spec.setLong(1, itemCategoryId);
                    itemCategory.setActivePawnbrokers(getActivePawnbrokers(ps_pawn_spec));
                }
                return itemCategory;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ItemCategory> getAllItemCategories() {
        List<ItemCategory> itemCategories = new ArrayList<>();

        try (Connection connection = databaseConfig.createConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            try (Statement st = connection.createStatement();
                 PreparedStatement ps_pawn_spec = connection.prepareStatement(SELECT_PAWNBROKER_SPECIALIZATION);
                 ResultSet rs = st.executeQuery(GET_ALL)) {

                while (rs.next()) {
                    ItemCategory itemCategory = mapItemCategory(rs);

                    ps_pawn_spec.setLong(1, itemCategory.getItemCategoryId());
                    itemCategory.setActivePawnbrokers(getActivePawnbrokers(ps_pawn_spec));

                    itemCategories.add(itemCategory);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemCategories;
    }

    @Override
    public void addItemCategory(ItemCategory itemCategory) throws SQLException {
        try (Connection connection = databaseConfig.createConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement ps_pawn_spec = connection.prepareStatement(INSERT_PAWNBROKER_SPECIALIZATION)) {
                ps.setString(1, itemCategory.getItemCategoryName());

                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next() && itemCategory.getActivePawnbrokers() != null) {
                        for (Pawnbroker pawnbroker : itemCategory.getActivePawnbrokers()) {
                            long pawnbrokerId = pawnbroker.getPawnbrokerId();
                            ps_pawn_spec.setLong(1, pawnbrokerId);
                            ps_pawn_spec.setLong(2, keys.getLong(1)); // itemCategoryId

                            ps_pawn_spec.executeUpdate();
                        }
                    }
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void updateItemCategoryName(long itemCategoryId, String itemCategoryName) throws SQLException {
        try (Connection connection = databaseConfig.createConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, itemCategoryName);
            ps.setLong(2, itemCategoryId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void deleteItemCategory(long itemCategoryId) {
        try (Connection connection = databaseConfig.createConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setLong(1, itemCategoryId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ItemCategory getItemCategory(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            return mapItemCategory(rs);
        }
    }

    private ItemCategory mapItemCategory(ResultSet rs) throws SQLException {
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setItemCategoryId(rs.getLong("item_category_id"));
        itemCategory.setItemCategoryName(rs.getString("item_category_name"));
        return itemCategory;
    }

    private List<Pawnbroker> getActivePawnbrokers(PreparedStatement ps) throws SQLException {
        List<Pawnbroker> activePawnbrokers = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                activePawnbrokers.add(mapPawnbroker(rs));
            }
        }
        return activePawnbrokers;
    }

    private Pawnbroker mapPawnbroker(ResultSet rs) throws SQLException {
        Pawnbroker pawnbroker = new Pawnbroker();
        pawnbroker.setPawnbrokerId(rs.getLong("pawnbroker_id"));
        pawnbroker.setFirstName(rs.getString("first_name"));
        pawnbroker.setLastName(rs.getString("last_name"));
        pawnbroker.setBirthdate(rs.getDate("birthdate").toLocalDate());
        pawnbroker.setContactNumber(rs.getString("contact_number"));
        pawnbroker.setEmail(rs.getString("email"));
        pawnbroker.setAddress(rs.getString("address"));
        return pawnbroker;
    }
}
