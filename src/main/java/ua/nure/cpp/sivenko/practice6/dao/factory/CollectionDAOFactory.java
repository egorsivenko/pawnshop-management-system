package ua.nure.cpp.sivenko.practice6.dao.factory;

import ua.nure.cpp.sivenko.practice6.dao.*;
import ua.nure.cpp.sivenko.practice6.dao.collection.*;

public final class CollectionDAOFactory implements DAOFactory {
    private CustomerDAO customerDAO;
    private ItemDAO itemDAO;
    private ItemCategoryDAO itemCategoryDAO;
    private PawnbrokerDAO pawnbrokerDAO;
    private PawnTransactionDAO pawnTransactionDAO;
    private RepaymentDAO repaymentDAO;
    private PaymentMethodDAO paymentMethodDAO;

    private static CollectionDAOFactory instance;

    private CollectionDAOFactory() {
    }

    public static synchronized CollectionDAOFactory getInstance() {
        if (instance == null) {
            instance = new CollectionDAOFactory();
        }
        return instance;
    }

    @Override
    public CustomerDAO getCustomerDAO() {
        if (customerDAO == null) {
            customerDAO = new CustomerDAOCollectionImpl();
        }
        return customerDAO;
    }

    @Override
    public ItemDAO getItemDAO() {
        if (itemDAO == null) {
            itemDAO = new ItemDAOCollectionImpl();
        }
        return itemDAO;
    }

    @Override
    public ItemCategoryDAO getItemCategoryDAO() {
        if (itemCategoryDAO == null) {
            itemCategoryDAO = new ItemCategoryDAOCollectionImpl();
        }
        return itemCategoryDAO;
    }

    @Override
    public PawnbrokerDAO getPawnbrokerDAO() {
        if (pawnbrokerDAO == null) {
            pawnbrokerDAO = new PawnbrokerDAOCollectionImpl();
        }
        return pawnbrokerDAO;
    }

    @Override
    public PawnTransactionDAO getPawnTransactionDAO() {
        if (pawnTransactionDAO == null) {
            pawnTransactionDAO = new PawnTransactionDAOCollectionImpl();
        }
        return pawnTransactionDAO;
    }

    @Override
    public RepaymentDAO getRepaymentDAO() {
        if (repaymentDAO == null) {
            repaymentDAO = new RepaymentDAOCollectionImpl();
        }
        return repaymentDAO;
    }

    @Override
    public PaymentMethodDAO getPaymentMethodDAO() {
        if (paymentMethodDAO == null) {
            paymentMethodDAO = new PaymentMethodDAOCollectionImpl();
        }
        return paymentMethodDAO;
    }
}
