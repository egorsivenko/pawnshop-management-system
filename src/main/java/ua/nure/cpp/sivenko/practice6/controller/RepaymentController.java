package ua.nure.cpp.sivenko.practice6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.nure.cpp.sivenko.practice6.model.PaymentMethod;
import ua.nure.cpp.sivenko.practice6.model.Repayment;
import ua.nure.cpp.sivenko.practice6.service.PaymentMethodService;
import ua.nure.cpp.sivenko.practice6.service.RepaymentService;

import java.util.List;

@Controller
public class RepaymentController {

    @Autowired
    private RepaymentService repaymentService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping("/repayments")
    public String getAllRepayments(Model model) {
        List<Repayment> repayments = repaymentService.getAllRepayments();
        model.addAttribute("repayments", repayments);

        List<PaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethods();
        model.addAttribute("paymentMethods", paymentMethods);
        return "repayments";
    }

    @GetMapping("/repayments/add")
    public String addRepaymentForm(Model model) {
        Repayment repayment = new Repayment();
        model.addAttribute("repayment", repayment);

        List<PaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethods();
        model.addAttribute("paymentMethods", paymentMethods);
        return "add_repayment";
    }

    @PostMapping("/repayments")
    public String addRepayment(@ModelAttribute("repayment") Repayment repayment) {
        repaymentService.addRepayment(repayment);
        return "redirect:/repayments";
    }
}
