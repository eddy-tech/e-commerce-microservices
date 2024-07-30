package org.micro.ecommerce.orderLine.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public record OrderLineResponse(
        Integer id, double quantity
) {}
