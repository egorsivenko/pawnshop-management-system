package ua.nure.cpp.sivenko.practice6.dao.mysql;

import ua.nure.cpp.sivenko.practice6.dao.RepaymentDAO;
import ua.nure.cpp.sivenko.practice6.model.Repayment;
import ua.nure.cpp.sivenko.practice6.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepaymentDAOMySQLImpl implements RepaymentDAO {
    private static final String GET_BY_ID = "SELECT * FROM repayments WHERE repayment_id = ?";
    private static final String GET_BY_TRANSACTION_ID = "SELECT * FROM repayments WHERE transaction_id = ?";
    private static final String GET_BY_PAYMENT_METHOD = "SELECT * FROM repayments WHERE payment_method = ?";
    private static final String GET_ALL = "SELECT * FROM repayments";

    private static final String INSERT = "INSERT INTO repayments (transaction_id, payment_method) VALUES (?, ?)";

    @Override
    public Repayment getRepaymentById(long repaymentId) {
        if (repaymentId < 1) {
            throw new IllegalArgumentException("Repayment id cannot be <= 0");
        }
        try (Connection connection = ConnectionFactory.createMySQLConnection();
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
        if (transactionId < 1) {
            throw new IllegalArgumentException("Transaction id cannot be <= 0");
        }
        try (Connection connection = ConnectionFactory.createMySQLConnection();
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

        if (paymentMethodId < 1) {
            throw new IllegalArgumentException("PaymentMethod id cannot be <= 0");
        }
        try (Connection connection = ConnectionFactory.createMySQLConnection();
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

        try (Connection connection = ConnectionFactory.createMySQLConnection();
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
    public void addRepayment(Repayment repayment) {
        try (Connection connection = ConnectionFactory.createMySQLConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT)) {
            ps.setLong(1, repayment.getTransactionId());
            ps.setLong(2, repayment.getPaymentMethod());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Repayment mapRepayment(ResultSet rs) throws SQLException {
        Repayment repayment = new Repayment();
        repayment.setRepaymentId(rs.getLong("repayment_id"));
        repayment.setTransactionId(rs.getLong("transaction_id"));
        repayment.setPaymentMethod(rs.getLong("payment_method"));
        repayment.setRepaymentDate(rs.getTimestamp("repayment_date").toLocalDateTime());
        return repayment;
    }
}
