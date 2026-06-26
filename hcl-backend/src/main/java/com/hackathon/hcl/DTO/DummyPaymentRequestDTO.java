package com.hackathon.hcl.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DummyPaymentRequestDTO {
    private Integer orderId;
    private BigDecimal amount;
    private String method;
}
