package org.micro.notification.core.email;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.micro.notification.core.email.EmailTemplates.PAYMENT_CONFIRMATION;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(
            String destinationEmail, String customerName, BigDecimal amount, String orderReference
    ) throws MessagingException {
        var mimeMessage = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
        messageHelper.setFrom("contact@zakke.com");

        final String templateName = PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);

        var context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        try {
            var htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);

            log.info(String.format(
                    "INFO - Email successfully sent to %s with template %s ", destinationEmail, templateName)
            );

        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", destinationEmail);
        }
    }

}
