package com.example.paymentService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePaymentLinkRequestDto {
    private Long orderId;
}
