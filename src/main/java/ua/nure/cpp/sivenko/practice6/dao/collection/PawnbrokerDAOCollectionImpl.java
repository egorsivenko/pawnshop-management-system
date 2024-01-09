package ua.nure.cpp.sivenko.practice6.dao.collection;

import ua.nure.cpp.sivenko.practice6.dao.PawnbrokerDAO;
import ua.nure.cpp.sivenko.practice6.model.Pawnbroker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PawnbrokerDAOCollectionImpl implements PawnbrokerDAO {
    private final List<Pawnbroker> pawnbrokers = new ArrayList<>();
    private final AtomicInteger id = new AtomicInteger(1);

    public PawnbrokerDAOCollectionImpl() {
        addPawnbroker(new Pawnbroker(1, "Daniel", "Runolfsson", LocalDate.now(), "983-346-2564",
                "Daniel.Runolfsson@gmail.com", "712 Langosh Hollow", new ArrayList<>()));
        addPawnbroker(new Pawnbroker(2, "Sheila", "Ernser", LocalDate.now(), "165-155-8744",
                "Sheila.Ernser@gmail.com", "6034 Green Union", new ArrayList<>()));
        addPawnbroker(new Pawnbroker(3, "Terence", "Wehner", LocalDate.now(), "586-868-0447",
                "Terence.Wehner42@yahoo.com", "897 Hillary Trafficway", new ArrayList<>()));
    }

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
    public void updatePawnbroker(Pawnbroker pawnbroker) {
        pawnbrokers.set((int) pawnbroker.getPawnbrokerId() - 1, pawnbroker);
    }

    @Override
    public void deletePawnbroker(long pawnbrokerId) {
        pawnbrokers.remove((int) pawnbrokerId - 1);
    }
}
