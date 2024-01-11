package ua.nure.cpp.sivenko.practice6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.nure.cpp.sivenko.practice6.model.Repayment;
import ua.nure.cpp.sivenko.practice6.service.RepaymentService;

import java.util.List;

@Controller
public class RepaymentController {

    @Autowired
    private RepaymentService repaymentService;

    @GetMapping("/repayments")
    public String getAllItems(Model model) {
        List<Repayment> repayments = repaymentService.getAllRepayments();
        model.addAttribute("repayments", repayments);
        return "repayments";
    }

    @GetMapping("/repayments/add")
    public String addRepaymentForm(Model model) {
        Repayment repayment = new Repayment();
        model.addAttribute("repayment", repayment);
        return "add_repayment";
    }

    @PostMapping("/repayments")
    public String addItem(@ModelAttribute("repayment") Repayment repayment) {
        repaymentService.addRepayment(repayment);
        return "redirect:/repayments";
    }
}