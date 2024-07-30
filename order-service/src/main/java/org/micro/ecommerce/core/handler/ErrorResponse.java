package org.micro.ecommerce.core.handler;

import java.util.Map;

public record ErrorResponse(Map<String,String> errors) {}
