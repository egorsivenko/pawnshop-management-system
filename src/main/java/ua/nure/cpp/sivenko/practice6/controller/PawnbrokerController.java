package ua.nure.cpp.sivenko.practice6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.nure.cpp.sivenko.practice6.form.PawnbrokerForm;
import ua.nure.cpp.sivenko.practice6.model.ItemCategory;
import ua.nure.cpp.sivenko.practice6.model.Pawnbroker;
import ua.nure.cpp.sivenko.practice6.service.ItemCategoryService;
import ua.nure.cpp.sivenko.practice6.service.PawnbrokerService;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Controller
public class PawnbrokerController {

    @Autowired
    private PawnbrokerService pawnbrokerService;

    @Autowired
    private ItemCategoryService itemCategoryService;

    @GetMapping("/pawnbrokers")
    public String getAllPawnbrokers(Model model) {
        List<Pawnbroker> pawnbrokers = pawnbrokerService.getAllPawnbrokers();
        model.addAttribute("pawnbrokers", pawnbrokers);
        return "pawnbroker/pawnbrokers";
    }

    @GetMapping("/pawnbrokers/add")
    public String addPawnbrokerForm(Model model) {
        PawnbrokerForm pawnbrokerForm = new PawnbrokerForm();
        model.addAttribute("pawnbrokerForm", pawnbrokerForm);

        List<ItemCategory> itemCategories = itemCategoryService.getAllItemCategories();
        model.addAttribute("itemCategories", itemCategories);
        return "pawnbroker/add_pawnbroker";
    }

    @PostMapping("/pawnbrokers")
    public String addPawnbroker(@ModelAttribute("pawnbroker") PawnbrokerForm pawnbrokerForm, Model model) {
        try {
            Pawnbroker pawnbroker = convertToEntity(pawnbrokerForm);
            pawnbrokerService.addPawnbroker(pawnbroker);
        } catch (SQLException e) {
            model.addAttribute("error", e.getMessage());
            return "pawnbroker/add_pawnbroker";
        }
        return "redirect:/pawnbrokers";
    }

    @GetMapping("/pawnbrokers/edit/{pawnbrokerId}")
    public String updatePawnbrokerForm(@PathVariable Long pawnbrokerId, Model model) {
        Pawnbroker pawnbroker = pawnbrokerService.getPawnbrokerById(pawnbrokerId);
        PawnbrokerForm pawnbrokerForm = convertToForm(pawnbroker);
        model.addAttribute("pawnbrokerForm", pawnbrokerForm);

        List<ItemCategory> itemCategories = itemCategoryService.getAllItemCategories();
        model.addAttribute("itemCategories", itemCategories);
        return "pawnbroker/update_pawnbroker";
    }

    @PostMapping("/pawnbrokers/{pawnbrokerId}")
    public String updatePawnbroker(@PathVariable Long pawnbrokerId, @ModelAttribute("pawnbroker") PawnbrokerForm pawnbrokerForm, Model model) {
        try {
            Pawnbroker pawnbroker = convertToEntity(pawnbrokerForm);
            Pawnbroker pawnbrokerById = pawnbrokerService.getPawnbrokerById(pawnbrokerId);
            if (!Objects.equals(pawnbrokerById, pawnbroker)) {
                pawnbrokerService.updatePawnbroker(pawnbrokerId, pawnbroker);
            }
        } catch (SQLException e) {
            model.addAttribute("error", e.getMessage());
            return "pawnbroker/update_pawnbroker";
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

    private Pawnbroker convertToEntity(PawnbrokerForm pawnbrokerForm) {
        List<ItemCategory> specializations = pawnbrokerForm.getSpecializationIds()
                .stream()
                .map(specId -> itemCategoryService.getItemCategoryById(specId))
                .toList();

        return new Pawnbroker(
                pawnbrokerForm.getPawnbrokerId(),
                pawnbrokerForm.getFirstName(),
                pawnbrokerForm.getLastName(),
                pawnbrokerForm.getBirthdate(),
                pawnbrokerForm.getContactNumber(),
                pawnbrokerForm.getEmail(),
                pawnbrokerForm.getAddress(),
                specializations
        );
    }

    private PawnbrokerForm convertToForm(Pawnbroker pawnbroker) {
        List<Long> specializationIds = pawnbroker.getSpecializations()
                .stream()
                .map(ItemCategory::getItemCategoryId)
                .toList();

        return new PawnbrokerForm(
                pawnbroker.getPawnbrokerId(),
                pawnbroker.getFirstName(),
                pawnbroker.getLastName(),
                pawnbroker.getBirthdate(),
                pawnbroker.getContactNumber(),
                pawnbroker.getEmail(),
                pawnbroker.getAddress(),
                specializationIds
        );
    }
}
