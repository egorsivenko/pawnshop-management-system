package ua.nure.cpp.sivenko.practice6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.nure.cpp.sivenko.practice6.model.Pawnbroker;
import ua.nure.cpp.sivenko.practice6.service.PawnbrokerService;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Controller
public class PawnbrokerController {

    @Autowired
    private PawnbrokerService pawnbrokerService;

    @GetMapping("/pawnbrokers")
    public String getAllPawnbrokers(Model model) {
        List<Pawnbroker> pawnbrokers = pawnbrokerService.getAllPawnbrokers();
        model.addAttribute("pawnbrokers", pawnbrokers);
        return "pawnbrokers";
    }

    @GetMapping("/pawnbrokers/add")
    public String addPawnbrokerForm(Model model) {
        Pawnbroker pawnbroker = new Pawnbroker();
        model.addAttribute("pawnbroker", pawnbroker);
        return "add_pawnbroker";
    }

    @PostMapping("/pawnbrokers")
    public String addPawnbroker(@ModelAttribute("pawnbroker") Pawnbroker pawnbroker, Model model) {
        try {
            pawnbrokerService.addPawnbroker(pawnbroker);
        } catch (SQLException e) {
            model.addAttribute("error", e.getMessage());
            return "add_pawnbroker";
        }
        return "redirect:/pawnbrokers";
    }

    @GetMapping("/pawnbrokers/edit/{pawnbrokerId}")
    public String updatePawnbrokerForm(@PathVariable Long pawnbrokerId, Model model) {
        Pawnbroker pawnbroker = pawnbrokerService.getPawnbrokerById(pawnbrokerId);
        model.addAttribute("pawnbroker", pawnbroker);
        return "update_pawnbroker";
    }

    @PostMapping("/pawnbrokers/{pawnbrokerId}")
    public String updatePawnbroker(@PathVariable Long pawnbrokerId, @ModelAttribute("pawnbroker") Pawnbroker pawnbroker, Model model) {
        try {
            Pawnbroker pawnbrokerById = pawnbrokerService.getPawnbrokerById(pawnbrokerId);
            if (!Objects.equals(pawnbrokerById, pawnbroker)) {
                pawnbrokerService.updatePawnbroker(pawnbrokerId, pawnbroker);
            }
        } catch (SQLException e) {
            model.addAttribute("error", e.getMessage());
            return "update_pawnbroker";
        }
        return "redirect:/pawnbrokers";
    }

    @GetMapping("/pawnbrokers/{pawnbrokerId}")
    public String deletePawnbroker(@PathVariable Long pawnbrokerId, Model model) {
        try {
            pawnbrokerService.deletePawnbroker(pawnbrokerId);
        } catch (SQLException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/pawnbrokers";
    }
}
