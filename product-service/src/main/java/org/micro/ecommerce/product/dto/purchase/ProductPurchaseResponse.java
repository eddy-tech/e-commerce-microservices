package org.micro.ecommerce.product.dto.purchase;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        Integer productId, String name, String description, BigDecimal price, double quantity
) {}
