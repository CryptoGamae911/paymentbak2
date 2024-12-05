package com.razorpaydem.razorpaym;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/razorpay")
public class RazorpayController {

    @Autowired
    private RazorpayService razorpayService;

    @PostMapping("/createOrder")
    public String createOrder(@RequestParam int amount) {
        try {
            return razorpayService.createOrder(amount, "INR");
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }
}
