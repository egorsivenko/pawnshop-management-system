package ua.nure.cpp.sivenko.practice6.dao.factory;

import ua.nure.cpp.sivenko.practice6.dao.*;

public interface DAOFactory {
    CustomerDAO getCustomerDAO();

    ItemDAO getItemDAO();

    ItemCategoryDAO getItemCategoryDAO();

    PawnbrokerDAO getPawnbrokerDAO();

    PawnTransactionDAO getPawnTransactionDAO();

    RepaymentDAO getRepaymentDAO();

    PaymentMethodDAO getPaymentMethodDAO();
}
