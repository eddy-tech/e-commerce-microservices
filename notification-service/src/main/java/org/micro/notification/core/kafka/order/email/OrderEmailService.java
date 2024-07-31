package org.micro.notification.core.kafka.order.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.micro.notification.core.kafka.order.dto.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.micro.notification.core.email.EmailTemplates.ORDER_CONFIRMATION;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderEmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    @Value("${spring.mail.contact.name}")
    private String contactEmail;

    @Async
    public void sendOrderConfirmationEmail(
            String destinationEmail, String customerName, BigDecimal amount, String orderReference,
            List<Product> products
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        mimeMessageHelper.setFrom(contactEmail);

        final String templateName = ORDER_CONFIRMATION.getTemplate();

        var variables = new HashMap<String, Object>();
        variables.put("customerName", customerName);
        variables.put("totalAmount", amount);
        variables.put("orderReference", orderReference);
        variables.put("products", products);

        var context = new Context();
        context.setVariables(variables);
        mimeMessageHelper.setSubject(ORDER_CONFIRMATION.getSubject());

        try {
            var htmlTemplate = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(htmlTemplate, true);
            mimeMessageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format(
                    "INFO - Email successfully sent to %s with template %s ", destinationEmail, templateName)
            );
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", destinationEmail);
        }
    }
}
