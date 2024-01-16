package ua.nure.cpp.sivenko.practice6.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.nure.cpp.sivenko.practice6.dao.RepaymentDAO;
import ua.nure.cpp.sivenko.practice6.model.Repayment;
import ua.nure.cpp.sivenko.practice6.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RepaymentDAOMySQLImpl implements RepaymentDAO {
    private static final String GET_BY_ID = "SELECT * FROM repayments WHERE repayment_id = ?";
    private static final String GET_BY_TRANSACTION_ID = "SELECT * FROM repayments WHERE transaction_id = ?";
    private static final String GET_BY_PAYMENT_METHOD = "SELECT * FROM repayments WHERE payment_method = ?";
    private static final String GET_ALL = "SELECT * FROM repayments";

    private static final String INSERT = "INSERT INTO repayments (transaction_id, payment_method) VALUES (?, ?)";

    @Autowired
    private DatabaseConfig databaseConfig;

    @Override
    public Repayment getRepaymentById(long repaymentId) {
        try (Connection connection = databaseConfig.createConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BY_ID)) {
            ps.setLong(1, repaymentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return mapRepayment(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Repayment getRepaymentByTransactionId(long transactionId) {
        try (Connection connection = databaseConfig.createConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BY_TRANSACTION_ID)) {
            ps.setLong(1, transactionId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return mapRepayment(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Repayment> getRepaymentsByPaymentMethod(long paymentMethodId) {
        List<Repayment> repayments = new ArrayList<>();

        try (Connection connection = databaseConfig.createConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BY_PAYMENT_METHOD)) {
            ps.setLong(1, paymentMethodId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    repayments.add(mapRepayment(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return repayments;
    }

    @Override
    public List<Repayment> getAllRepayments() {
        List<Repayment> repayments = new ArrayList<>();

        try (Connection connection = databaseConfig.createConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(GET_ALL)) {
            while (rs.next()) {
                repayments.add(mapRepayment(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return repayments;
    }

    @Override
    public void addRepayment(Repayment repayment) throws SQLException {
        try (Connection connection = databaseConfig.createConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT)) {
            ps.setLong(1, repayment.getTransactionId());
            ps.setLong(2, repayment.getPaymentMethodId());

            ps.executeUpdate();
        }
    }

    private Repayment mapRepayment(ResultSet rs) throws SQLException {
        Repayment repayment = new Repayment();
        repayment.setRepaymentId(rs.getLong("repayment_id"));
        repayment.setTransactionId(rs.getLong("transaction_id"));
        repayment.setPaymentMethodId(rs.getLong("payment_method"));
        repayment.setRepaymentDate(rs.getTimestamp("repayment_date").toLocalDateTime());
        return repayment;
    }
}
