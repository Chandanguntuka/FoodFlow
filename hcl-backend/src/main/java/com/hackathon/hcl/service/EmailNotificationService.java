package com.hackathon.hcl.service;

import com.hackathon.hcl.model.Order;
import com.hackathon.hcl.model.User;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {
    public void sendRegistrationConfirmation(User user) {
    }

    public void sendOrderConfirmation(Order order) {
    }

    public void sendOrderStatusUpdate(Order order) {
    }
}
