package ua.nure.cpp.sivenko.practice6.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.nure.cpp.sivenko.practice6.DatabaseConfig;
import ua.nure.cpp.sivenko.practice6.dao.PawnTransactionDAO;
import ua.nure.cpp.sivenko.practice6.model.PawnTransaction;
import ua.nure.cpp.sivenko.practice6.model.PawnTransaction.TransactionStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PawnTransactionDAOMySQLImpl implements PawnTransactionDAO {
    private static final String GET_BY_ID = "SELECT * FROM pawn_transactions WHERE transaction_id = ?";
    private static final String GET_BY_CUSTOMER_ID = "SELECT * FROM pawn_transactions WHERE customer_id = ?";
    private static final String GET_BY_STATUS = "SELECT * FROM pawn_transactions WHERE transaction_status = ?";
    private static final String GET_ALL = "SELECT * FROM pawn_transactions";

    private static final String INSERT = "INSERT INTO pawn_transactions " +
            "(customer_id, item_id, pawnbroker_id, pawn_amount, interest_rate, monthly_period) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    @Autowired
    private DatabaseConfig databaseConfig;

    @Override
    public PawnTransaction getPawnTransactionById(long pawnTransactionId) {
        try (Connection connection = databaseConfig.createConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BY_ID)) {
            ps.setLong(1, pawnTransactionId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return mapPawnTransaction(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PawnTransaction> getPawnTransactionsByCustomerId(long customerId) {
        List<PawnTransaction> pawnTransactions = new ArrayList<>();

        if (customerId < 1) {
            throw new IllegalArgumentException("Customer id cannot be <= 0");
        }
        try (Connection connection = databaseConfig.createConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BY_CUSTOMER_ID)) {
            ps.setLong(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pawnTransactions.add(mapPawnTransaction(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pawnTransactions;
    }

    @Override
    public List<PawnTransaction> getPawnTransactionsByStatus(TransactionStatus transactionStatus) {
        List<PawnTransaction> pawnTransactions = new ArrayList<>();

        try (Connection connection = databaseConfig.createConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BY_STATUS)) {
            ps.setString(1, transactionStatus.toString());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pawnTransactions.add(mapPawnTransaction(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pawnTransactions;
    }

    @Override
    public List<PawnTransaction> getAllPawnTransactions() {
        List<PawnTransaction> pawnTransactions = new ArrayList<>();

        try (Connection connection = databaseConfig.createConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(GET_ALL)) {
            while (rs.next()) {
                pawnTransactions.add(mapPawnTransaction(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pawnTransactions;
    }

    @Override
    public void addPawnTransaction(PawnTransaction pawnTransaction) throws SQLException {
        try (Connection connection = databaseConfig.createConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT)) {
            ps.setLong(1, pawnTransaction.getCustomerId());
            ps.setLong(2, pawnTransaction.getItemId());
            ps.setLong(3, pawnTransaction.getPawnbrokerId());
            ps.setBigDecimal(4, pawnTransaction.getPawnAmount());
            ps.setInt(5, pawnTransaction.getInterestRate());
            ps.setInt(6, pawnTransaction.getMonthlyPeriod());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    private PawnTransaction mapPawnTransaction(ResultSet rs) throws SQLException {
        PawnTransaction pawnTransaction = new PawnTransaction();
        pawnTransaction.setTransactionId(rs.getLong("transaction_id"));
        pawnTransaction.setCustomerId(rs.getLong("customer_id"));
        pawnTransaction.setItemId(rs.getLong("item_id"));
        pawnTransaction.setPawnbrokerId(rs.getLong("pawnbroker_id"));
        pawnTransaction.setPawnAmount(rs.getBigDecimal("pawn_amount"));
        pawnTransaction.setInterestRate(rs.getInt("interest_rate"));
        pawnTransaction.setMonthlyPeriod(rs.getInt("monthly_period"));
        pawnTransaction.setRepaymentAmount(rs.getBigDecimal("repayment_amount"));
        pawnTransaction.setPawnDate(rs.getDate("pawn_date").toLocalDate());
        pawnTransaction.setExpirationDate(rs.getDate("expiration_date").toLocalDate());
        pawnTransaction.setTransactionStatus(TransactionStatus.valueOf(rs.getString("transaction_status").toUpperCase()));
        return pawnTransaction;
    }
}
