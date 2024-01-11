package ua.nure.cpp.sivenko.practice6.dao.mysql;

import org.springframework.stereotype.Repository;
import ua.nure.cpp.sivenko.practice6.dao.PaymentMethodDAO;
import ua.nure.cpp.sivenko.practice6.model.PaymentMethod;
import ua.nure.cpp.sivenko.practice6.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentMethodDAOMySQLImpl implements PaymentMethodDAO {
    private static final String GET_BY_ID = "SELECT * FROM payment_methods WHERE payment_method_id = ?";
    private static final String GET_ALL = "SELECT * FROM payment_methods ORDER BY payment_method_id";

    private static final String INSERT = "INSERT INTO payment_methods(payment_method_name) VALUES (?)";
    private static final String UPDATE = "UPDATE payment_methods " +
            "SET payment_method_name = ? WHERE payment_method_id = ?";
    private static final String DELETE = "DELETE FROM payment_methods WHERE payment_method_id = ?";

    @Override
    public PaymentMethod getPaymentMethodById(long paymentMethodId) {
        try (Connection connection = ConnectionFactory.createMySQLConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BY_ID)) {
            ps.setLong(1, paymentMethodId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return mapPaymentMethod(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PaymentMethod> getAllPaymentMethods() {
        List<PaymentMethod> paymentMethods = new ArrayList<>();

        try (Connection connection = ConnectionFactory.createMySQLConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(GET_ALL)) {
            while (rs.next()) {
                paymentMethods.add(mapPaymentMethod(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return paymentMethods;
    }

    @Override
    public void addPaymentMethod(String paymentMethodName) throws SQLException {
        try (Connection connection = ConnectionFactory.createMySQLConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT)) {
            ps.setString(1, paymentMethodName);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void updatePaymentMethodName(long paymentMethodId, String paymentMethodName) throws SQLException {
        try (Connection connection = ConnectionFactory.createMySQLConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, paymentMethodName);
            ps.setLong(2, paymentMethodId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void deletePaymentMethod(long paymentMethodId) {
        try (Connection connection = ConnectionFactory.createMySQLConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setLong(1, paymentMethodId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PaymentMethod mapPaymentMethod(ResultSet rs) throws SQLException {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setPaymentMethodId(rs.getLong("payment_method_id"));
        paymentMethod.setPaymentMethodName(rs.getString("payment_method_name"));
        return paymentMethod;
    }
}
