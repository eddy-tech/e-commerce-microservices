package org.micro.ecommerce.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException  extends RuntimeException{
    private String message;
}
