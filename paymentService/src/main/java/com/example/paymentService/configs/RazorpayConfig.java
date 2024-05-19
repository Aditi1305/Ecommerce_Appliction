package com.example.paymentService.configs;

import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.razorpay.RazorpayClient;
@Configuration
public class RazorpayConfig {
    @Value("${razorpay.key_id}")
    private String razorpaykeyId;

    @Value("${razorpay.key_secret}")
    private String razorpayKeySecret;

    @Bean
    public RazorpayClient createRazorpayClient() throws RazorpayException {
        return new RazorpayClient(
                razorpaykeyId,
                razorpayKeySecret
        );
    }

}
