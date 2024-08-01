package org.micro.ecommerce.payment.resource;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.micro.ecommerce.payment.dto.PaymentRequest;
import org.micro.ecommerce.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentResource {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Integer> createPayment(@RequestBody @Valid PaymentRequest request) {
        return new ResponseEntity<>(this.paymentService.createPayment(request), CREATED);
    }
}
