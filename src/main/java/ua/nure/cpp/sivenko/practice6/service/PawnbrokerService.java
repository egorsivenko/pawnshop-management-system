package ua.nure.cpp.sivenko.practice6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.cpp.sivenko.practice6.dao.PawnbrokerDAO;
import ua.nure.cpp.sivenko.practice6.model.Pawnbroker;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Service
public class PawnbrokerService {

    @Autowired
    private PawnbrokerDAO pawnbrokerDAO;

    public List<Pawnbroker> getAllPawnbrokers() {
        return pawnbrokerDAO.getAllPawnbrokers();
    }

    public Pawnbroker getPawnbrokerById(long pawnbrokerId) {
        return pawnbrokerDAO.getPawnbrokerById(pawnbrokerId);
    }

    public void addPawnbroker(Pawnbroker pawnbroker) throws SQLException {
        Pawnbroker pawnbrokerByContactNumber = pawnbrokerDAO.getPawnbrokerByContactNumber(pawnbroker.getContactNumber());
        Pawnbroker pawnbrokerByEmail = pawnbrokerDAO.getPawnbrokerByEmail(pawnbroker.getEmail());

        if (pawnbrokerByContactNumber != null) {
            throw new SQLException("Pawnbroker with contact number '" + pawnbroker.getContactNumber() + "' already exists");
        } else if (pawnbrokerByEmail != null) {
            throw new SQLException("Pawnbroker with email '" + pawnbroker.getEmail() + "' already exists");
        }
        pawnbrokerDAO.addPawnbroker(pawnbroker);
    }

    public void deletePawnbroker(long pawnbrokerId) throws SQLException {
        Pawnbroker pawnbrokerById = pawnbrokerDAO.getPawnbrokerById(pawnbrokerId);
        if (pawnbrokerById == null) {
            throw new SQLException("Pawnbroker with id '" + pawnbrokerId + "' does not exists");
        }
        pawnbrokerDAO.deletePawnbroker(pawnbrokerId);
    }

    public void updatePawnbroker(long pawnbrokerId, Pawnbroker pawnbroker) throws SQLException {
        Pawnbroker pawnbrokerById = pawnbrokerDAO.getPawnbrokerById(pawnbrokerId);

        if (!Objects.equals(pawnbrokerById.getContactNumber(), pawnbroker.getContactNumber())) {
            Pawnbroker pawnbrokerByContactNumber = pawnbrokerDAO.getPawnbrokerByContactNumber(pawnbroker.getContactNumber());
            if (pawnbrokerByContactNumber != null) {
                throw new SQLException("Pawnbroker with contact number '" + pawnbroker.getContactNumber() + "' already exists");
            }
        } else if (!Objects.equals(pawnbrokerById.getEmail(), pawnbroker.getEmail())) {
            Pawnbroker pawnbrokerByEmail = pawnbrokerDAO.getPawnbrokerByEmail(pawnbroker.getEmail());
            if (pawnbrokerByEmail != null) {
                throw new SQLException("Pawnbroker with email '" + pawnbroker.getEmail() + "' already exists");
            }
        }
        pawnbrokerDAO.updatePawnbroker(pawnbrokerId, pawnbroker);
    }
}
