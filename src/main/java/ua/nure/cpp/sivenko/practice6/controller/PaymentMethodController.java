package ua.nure.cpp.sivenko.practice6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.nure.cpp.sivenko.practice6.model.PaymentMethod;
import ua.nure.cpp.sivenko.practice6.service.PaymentMethodService;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Controller
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping("/paymentMethods")
    public String getAllPaymentMethods(Model model) {
        List<PaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethods();
        model.addAttribute("paymentMethods", paymentMethods);
        return "paymentMethods";
    }

    @GetMapping("/paymentMethods/add")
    public String addPaymentMethodForm(Model model) {
        PaymentMethod paymentMethod = new PaymentMethod();
        model.addAttribute("paymentMethod", paymentMethod);
        return "add_paymentMethod";
    }

    @PostMapping("/paymentMethods")
    public String addPaymentMethod(@ModelAttribute("paymentMethod") PaymentMethod paymentMethod, Model model) {
        try {
            paymentMethodService.addPaymentMethod(paymentMethod);
        } catch (SQLException e) {
            model.addAttribute("error", e.getMessage());
            return "add_paymentMethod";
        }
        return "redirect:/paymentMethods";
    }

    @GetMapping("/paymentMethods/edit/{paymentMethodId}")
    public String updatePaymentMethodForm(@PathVariable Long paymentMethodId, Model model) {
        PaymentMethod paymentMethodById = paymentMethodService.getPaymentMethodById(paymentMethodId);
        model.addAttribute("paymentMethod", paymentMethodById);
        return "update_paymentMethod";
    }

    @PostMapping("/paymentMethods/{paymentMethodId}")
    public String updatePaymentMethod(@PathVariable Long paymentMethodId, @ModelAttribute("paymentMethod") PaymentMethod paymentMethod, Model model) {
        try {
            PaymentMethod paymentMethodById = paymentMethodService.getPaymentMethodById(paymentMethodId);
            if (!Objects.equals(paymentMethodById, paymentMethod)) {
                paymentMethodService.updatePaymentMethodName(paymentMethodId, paymentMethod.getPaymentMethodName());
            }
        } catch (SQLException e) {
            model.addAttribute("error", e.getMessage());
            return "update_paymentMethod";
        }
        return "redirect:/paymentMethods";
    }

    @GetMapping("/paymentMethods/{paymentMethodId}")
    public String deletePaymentMethod(@PathVariable Long paymentMethodId, Model model) {
        try {
            paymentMethodService.deletePaymentMethod(paymentMethodId);
        } catch (SQLException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/paymentMethods";
    }
}
