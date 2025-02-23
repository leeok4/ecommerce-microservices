package com.ecommerce.orderservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class CreateOrderRequest {
    @NotEmpty(message = "Customer name is required")
    private String customerName;

    @NotEmpty(message = "Customer email is required")
    @Email(message = "Invalid email format")
    private String customerEmail;

    @NotEmpty(message = "Shipping address is required")
    private String shippingAddress;

    @NotNull(message = "Order items cannot be null")
    @NotEmpty(message = "Order must have at least one item")
    private List<OrderItemRequest> items;
}
