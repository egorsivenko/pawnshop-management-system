package ua.nure.cpp.sivenko.practice6.dao.factory;

import ua.nure.cpp.sivenko.practice6.dao.*;
import ua.nure.cpp.sivenko.practice6.dao.mysql.*;

public final class MySqlDAOFactory implements DAOFactory {
    private CustomerDAO customerDAO;
    private ItemDAO itemDAO;
    private ItemCategoryDAO itemCategoryDAO;
    private PawnbrokerDAO pawnbrokerDAO;
    private PawnTransactionDAO pawnTransactionDAO;
    private RepaymentDAO repaymentDAO;
    private PaymentMethodDAO paymentMethodDAO;

    private static MySqlDAOFactory instance;

    private MySqlDAOFactory() {
    }

    public static synchronized MySqlDAOFactory getInstance() {
        if (instance == null) {
            instance = new MySqlDAOFactory();
        }
        return instance;
    }

    @Override
    public CustomerDAO getCustomerDAO() {
        if (customerDAO == null) {
            customerDAO = new CustomerDAOMySQLImpl();
        }
        return customerDAO;
    }

    @Override
    public ItemDAO getItemDAO() {
        if (itemDAO == null) {
            itemDAO = new ItemDAOMySQLImpl();
        }
        return itemDAO;
    }

    @Override
    public ItemCategoryDAO getItemCategoryDAO() {
        if (itemCategoryDAO == null) {
            itemCategoryDAO = new ItemCategoryDAOMySQLImpl();
        }
        return itemCategoryDAO;
    }

    @Override
    public PawnbrokerDAO getPawnbrokerDAO() {
        if (pawnbrokerDAO == null) {
            pawnbrokerDAO = new PawnbrokerDAOMySQlImpl();
        }
        return pawnbrokerDAO;
    }

    @Override
    public PawnTransactionDAO getPawnTransactionDAO() {
        if (pawnTransactionDAO == null) {
            pawnTransactionDAO = new PawnTransactionDAOMySQLImpl();
        }
        return pawnTransactionDAO;
    }

    @Override
    public RepaymentDAO getRepaymentDAO() {
        if (repaymentDAO == null) {
            repaymentDAO = new RepaymentDAOMySQLImpl();
        }
        return repaymentDAO;
    }

    @Override
    public PaymentMethodDAO getPaymentMethodDAO() {
        if (paymentMethodDAO == null) {
            paymentMethodDAO = new PaymentMethodDAOMySQLImpl();
        }
        return paymentMethodDAO;
    }
}
