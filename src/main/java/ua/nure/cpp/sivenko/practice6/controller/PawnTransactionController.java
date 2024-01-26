package ua.nure.cpp.sivenko.practice6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.nure.cpp.sivenko.practice6.model.PawnTransaction;
import ua.nure.cpp.sivenko.practice6.service.PawnTransactionService;

import java.sql.SQLException;
import java.util.List;

@Controller
public class PawnTransactionController {

    @Autowired
    private PawnTransactionService pawnTransactionService;

    @GetMapping("/pawnTransactions")
    public String getAllPawnTransactions(Model model) {
        List<PawnTransaction> pawnTransactions = pawnTransactionService.getAllPawnTransactions();
        model.addAttribute("pawnTransactions", pawnTransactions);
        return "pawnTransaction/pawnTransactions";
    }

    @GetMapping("/pawnTransactions/add")
    public String addPawnTransactionForm(Model model) {
        PawnTransaction pawnTransaction = new PawnTransaction();
        model.addAttribute("pawnTransaction", pawnTransaction);
        return "pawnTransaction/add_pawnTransaction";
    }

    @PostMapping("/pawnTransactions")
    public String addPawnTransaction(@ModelAttribute("pawnTransaction") PawnTransaction pawnTransaction, Model model) {
        try {
            pawnTransactionService.addPawnTransaction(pawnTransaction);
        } catch (SQLException e) {
            model.addAttribute("error", e.getMessage());
            return "pawnTransaction/add_pawnTransaction";
        }
        return "redirect:/pawnTransactions";
    }
}
