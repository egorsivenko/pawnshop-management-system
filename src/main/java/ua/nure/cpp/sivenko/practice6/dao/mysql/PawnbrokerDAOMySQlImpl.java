package ua.nure.cpp.sivenko.practice6.dao.mysql;

import org.springframework.stereotype.Repository;
import ua.nure.cpp.sivenko.practice6.dao.PawnbrokerDAO;
import ua.nure.cpp.sivenko.practice6.model.ItemCategory;
import ua.nure.cpp.sivenko.practice6.model.Pawnbroker;
import ua.nure.cpp.sivenko.practice6.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PawnbrokerDAOMySQlImpl implements PawnbrokerDAO {
    private static final String GET_BY_ID = "SELECT * FROM pawnbrokers WHERE pawnbroker_id = ?";
    private static final String GET_BY_CONTACT_NUMBER = "SELECT * FROM pawnbrokers WHERE contact_number = ?";
    private static final String GET_BY_EMAIL = "SELECT * FROM pawnbrokers WHERE email = ?";
    private static final String GET_ALL = "SELECT * FROM pawnbrokers";

    private static final String INSERT = "INSERT INTO pawnbrokers (first_name, last_name, birthdate, contact_number, email, address) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE pawnbrokers " +
            "SET first_name = ?, last_name = ?, birthdate = ?, contact_number = ?, email = ?, address = ? WHERE pawnbroker_id = ?";
    private static final String DELETE = "DELETE FROM pawnbrokers WHERE pawnbroker_id = ?";

    private static final String SELECT_PAWNBROKER_SPECIALIZATION = "SELECT * FROM pawnbroker_specialization ps " +
            "JOIN item_categories ic ON ps.specialization = ic.item_category_id WHERE pawnbroker_id = ?";
    private static final String INSERT_PAWNBROKER_SPECIALIZATION = "INSERT INTO pawnbroker_specialization VALUES (?, ?)";
    private static final String DELETE_PAWNBROKER_SPECIALIZATION = "DELETE FROM pawnbroker_specialization WHERE pawnbroker_id = ?";

    @Override
    public Pawnbroker getPawnbrokerById(long pawnbrokerId) {
        try (Connection connection = ConnectionFactory.createMySQLConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            try (PreparedStatement ps = connection.prepareStatement(GET_BY_ID);
                 PreparedStatement ps_pawn_spec = connection.prepareStatement(SELECT_PAWNBROKER_SPECIALIZATION)) {
                ps.setLong(1, pawnbrokerId);
                Pawnbroker pawnbroker = getPawnbroker(ps);

                if (pawnbroker != null) {
                    ps_pawn_spec.setLong(1, pawnbrokerId);
                    pawnbroker.setSpecializations(getPawnbrokerSpecializations(ps_pawn_spec));
                }
                return pawnbroker;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pawnbroker getPawnbrokerByContactNumber(String contactNumber) {
        try (Connection connection = ConnectionFactory.createMySQLConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            try (PreparedStatement ps = connection.prepareStatement(GET_BY_CONTACT_NUMBER);
                 PreparedStatement ps_pawn_spec = connection.prepareStatement(SELECT_PAWNBROKER_SPECIALIZATION)) {
                ps.setString(1, contactNumber);
                Pawnbroker pawnbroker = getPawnbroker(ps);

                if (pawnbroker != null) {
                    ps_pawn_spec.setLong(1, pawnbroker.getPawnbrokerId());
                    pawnbroker.setSpecializations(getPawnbrokerSpecializations(ps_pawn_spec));
                }
                return pawnbroker;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pawnbroker getPawnbrokerByEmail(String email) {
        try (Connection connection = ConnectionFactory.createMySQLConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            try (PreparedStatement ps = connection.prepareStatement(GET_BY_EMAIL);
                 PreparedStatement ps_pawn_spec = connection.prepareStatement(SELECT_PAWNBROKER_SPECIALIZATION)) {
                ps.setString(1, email);
                Pawnbroker pawnbroker = getPawnbroker(ps);

                if (pawnbroker != null) {
                    ps_pawn_spec.setLong(1, pawnbroker.getPawnbrokerId());
                    pawnbroker.setSpecializations(getPawnbrokerSpecializations(ps_pawn_spec));
                }
                return pawnbroker;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pawnbroker> getAllPawnbrokers() {
        List<Pawnbroker> pawnbrokers = new ArrayList<>();

        try (Connection connection = ConnectionFactory.createMySQLConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            try (Statement st = connection.createStatement();
                 PreparedStatement ps_pawn_spec = connection.prepareStatement(SELECT_PAWNBROKER_SPECIALIZATION);
                 ResultSet rs = st.executeQuery(GET_ALL)) {

                while (rs.next()) {
                    Pawnbroker pawnbroker = mapPawnbroker(rs);

                    ps_pawn_spec.setLong(1, pawnbroker.getPawnbrokerId());
                    pawnbroker.setSpecializations(getPawnbrokerSpecializations(ps_pawn_spec));

                    pawnbrokers.add(pawnbroker);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pawnbrokers;
    }

    @Override
    public void addPawnbroker(Pawnbroker pawnbroker) {
        try (Connection connection = ConnectionFactory.createMySQLConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement ps_pawn_spec = connection.prepareStatement(INSERT_PAWNBROKER_SPECIALIZATION)) {
                ps.setString(1, pawnbroker.getFirstName());
                ps.setString(2, pawnbroker.getLastName());
                ps.setDate(3, Date.valueOf(pawnbroker.getBirthdate()));
                ps.setString(4, pawnbroker.getContactNumber());
                ps.setString(5, pawnbroker.getEmail());
                ps.setString(6, pawnbroker.getAddress());

                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next() && pawnbroker.getSpecializations() != null) {
                        for (ItemCategory itemCategory : pawnbroker.getSpecializations()) {
                            long itemCategoryId = itemCategory.getItemCategoryId();
                            ps_pawn_spec.setLong(1, keys.getLong(1)); // pawnbrokerId
                            ps_pawn_spec.setLong(2, itemCategoryId);

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
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePawnbroker(long pawnbrokerId, Pawnbroker pawnbroker) {
        try (Connection connection = ConnectionFactory.createMySQLConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement(UPDATE);
                 PreparedStatement ps_pawn_spec_insert = connection.prepareStatement(INSERT_PAWNBROKER_SPECIALIZATION);
                 PreparedStatement ps_pawn_spec_delete = connection.prepareStatement(DELETE_PAWNBROKER_SPECIALIZATION)) {
                ps.setString(1, pawnbroker.getFirstName());
                ps.setString(2, pawnbroker.getLastName());
                ps.setDate(3, Date.valueOf(pawnbroker.getBirthdate()));
                ps.setString(4, pawnbroker.getContactNumber());
                ps.setString(5, pawnbroker.getEmail());
                ps.setString(6, pawnbroker.getAddress());
                ps.setLong(7, pawnbrokerId);

                ps.executeUpdate();

                ps_pawn_spec_delete.setLong(1, pawnbroker.getPawnbrokerId());
                ps_pawn_spec_delete.executeUpdate();

                if (pawnbroker.getSpecializations() != null) {
                    for (ItemCategory itemCategory : pawnbroker.getSpecializations()) {
                        long itemCategoryId = itemCategory.getItemCategoryId();
                        ps_pawn_spec_insert.setLong(1, pawnbroker.getPawnbrokerId()); // pawnbrokerId
                        ps_pawn_spec_insert.setLong(2, itemCategoryId);

                        ps_pawn_spec_insert.executeUpdate();
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePawnbroker(long pawnbrokerId) {
        try (Connection connection = ConnectionFactory.createMySQLConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setLong(1, pawnbrokerId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Pawnbroker getPawnbroker(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            return mapPawnbroker(rs);
        }
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

    private List<ItemCategory> getPawnbrokerSpecializations(PreparedStatement ps) throws SQLException {
        List<ItemCategory> specializations = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                specializations.add(mapItemCategory(rs));
            }
        }
        return specializations;
    }

    private ItemCategory mapItemCategory(ResultSet rs) throws SQLException {
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setItemCategoryId(rs.getLong("item_category_id"));
        itemCategory.setItemCategoryName(rs.getString("item_category_name"));
        return itemCategory;
    }
}
