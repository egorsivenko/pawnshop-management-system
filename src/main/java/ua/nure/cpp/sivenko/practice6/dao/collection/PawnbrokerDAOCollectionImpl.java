package ua.nure.cpp.sivenko.practice6.dao.collection;

import ua.nure.cpp.sivenko.practice6.dao.PawnbrokerDAO;
import ua.nure.cpp.sivenko.practice6.model.Pawnbroker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PawnbrokerDAOCollectionImpl implements PawnbrokerDAO {
    private final List<Pawnbroker> pawnbrokers = new ArrayList<>();
    private final AtomicInteger id = new AtomicInteger(1);

    @Override
    public Pawnbroker getPawnbrokerById(long pawnbrokerId) {
        return pawnbrokers.stream()
                .filter(pawnbroker -> pawnbroker.getPawnbrokerId() == pawnbrokerId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Pawnbroker getPawnbrokerByContactNumber(String contactNumber) {
        return pawnbrokers.stream()
                .filter(pawnbroker -> pawnbroker.getContactNumber().equals(contactNumber))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Pawnbroker getPawnbrokerByEmail(String email) {
        return pawnbrokers.stream()
                .filter(pawnbroker -> pawnbroker.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Pawnbroker> getAllPawnbrokers() {
        return pawnbrokers;
    }

    @Override
    public void addPawnbroker(Pawnbroker pawnbroker) {
        pawnbroker.setPawnbrokerId(id.getAndIncrement());
        pawnbrokers.add(pawnbroker);
    }

    @Override
    public void updatePawnbroker(long pawnbrokerId, Pawnbroker pawnbroker) {
        pawnbrokers.stream()
                .filter(p -> p.getPawnbrokerId() == pawnbrokerId)
                .findFirst()
                .ifPresent(p -> {
                    int index = pawnbrokers.indexOf(p);
                    pawnbrokers.set(index, pawnbroker);
                });
    }

    @Override
    public void deletePawnbroker(long pawnbrokerId) {
        pawnbrokers.removeIf(pawnbroker -> pawnbroker.getPawnbrokerId() == pawnbrokerId);
    }
}
