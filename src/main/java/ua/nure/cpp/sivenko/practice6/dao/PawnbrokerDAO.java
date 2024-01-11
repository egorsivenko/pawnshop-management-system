package ua.nure.cpp.sivenko.practice6.dao;

import ua.nure.cpp.sivenko.practice6.model.Pawnbroker;

import java.util.List;

public interface PawnbrokerDAO {
    Pawnbroker getPawnbrokerById(long pawnbrokerId);

    Pawnbroker getPawnbrokerByContactNumber(String contactNumber);

    Pawnbroker getPawnbrokerByEmail(String email);

    List<Pawnbroker> getAllPawnbrokers();

    void addPawnbroker(Pawnbroker pawnbroker);

    void updatePawnbroker(long pawnbrokerId, Pawnbroker pawnbroker);

    void deletePawnbroker(long pawnbrokerId);
}
