package ua.nure.cpp.sivenko.practice6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.cpp.sivenko.practice6.dao.CustomerDAO;
import ua.nure.cpp.sivenko.practice6.dao.ItemDAO;
import ua.nure.cpp.sivenko.practice6.dao.PawnTransactionDAO;
import ua.nure.cpp.sivenko.practice6.dao.PawnbrokerDAO;
import ua.nure.cpp.sivenko.practice6.model.Customer;
import ua.nure.cpp.sivenko.practice6.model.Item;
import ua.nure.cpp.sivenko.practice6.model.PawnTransaction;
import ua.nure.cpp.sivenko.practice6.model.Pawnbroker;

import java.sql.SQLException;
import java.util.List;

@Service
public class PawnTransactionService {

    @Autowired
    private PawnTransactionDAO pawnTransactionDAO;

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private PawnbrokerDAO pawnbrokerDAO;

    @Autowired
    private ItemDAO itemDAO;

    public List<PawnTransaction> getAllPawnTransactions() {
        return pawnTransactionDAO.getAllPawnTransactions();
    }

    public PawnTransaction getPawnTransactionById(long pawnTransactionId) {
        return pawnTransactionDAO.getPawnTransactionById(pawnTransactionId);
    }

    public void addPawnTransaction(PawnTransaction pawnTransaction) throws SQLException {
        Customer customer = customerDAO.getCustomerById(pawnTransaction.getCustomerId());
        Item item = itemDAO.getItemById(pawnTransaction.getItemId());
        Pawnbroker pawnbroker = pawnbrokerDAO.getPawnbrokerById(pawnTransaction.getPawnbrokerId());

        if (customer == null) {
            throw new SQLException("Customer with id '" + pawnTransaction.getCustomerId() + "' does not exists");
        } else if (item == null) {
            throw new SQLException("Item with id '" + pawnTransaction.getItemId() + "' does not exists");
        } else if (item.getItemStatus() != Item.ItemStatus.PAWNED) {
            throw new SQLException("Item with id '" + pawnTransaction.getItemId() + "' cannot be pawned");
        } else if (pawnbroker == null) {
            throw new SQLException("Pawnbroker with id '" + pawnTransaction.getPawnbrokerId() + "' does not exists");
        }

        pawnTransactionDAO.addPawnTransaction(pawnTransaction);
    }
}
