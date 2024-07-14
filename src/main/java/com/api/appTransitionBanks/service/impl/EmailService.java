package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.entities.BankAccount;
import com.api.appTransitionBanks.entities.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    @Value("${spring.mail.properties.mail.from}")
    private String emailFrom;

    public void sendEmail(Mail mail) throws MessagingException {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            String htmlContent = getHtmlContent(mail);

            mimeMessageHelper.setTo(mail.getTo());
            mimeMessageHelper.setFrom(mail.getFrom());
            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
        } catch (Exception e){
            throw new RuntimeException("Error sending email", e);
        }

    }

    private String getHtmlContent(Mail mail) throws MessagingException {
        try {
            var context = new Context();
            context.setVariables(mail.getHtmlTemplate().getProps());
            return templateEngine.process(mail.getHtmlTemplate().getTemplate(), context);
        } catch (Exception e){
            throw new RuntimeException("Error getting html content", e);
        }

    }

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public void buildTemplateEmailToSendUser(BankAccount bankAccount) throws MessagingException {
        try {
            Map<String, Object> context = new HashMap<>();
            context.put("name", bankAccount.getPerson().getUserInformation().getName() + bankAccount.getPerson().getUserInformation().getLastname());
            context.put("account", bankAccount.getNumberAccount());
            context.put("password", bankAccount.getPasswordApp());

            this.sendEmail(Mail.builder()
                    .to(bankAccount.getPerson().getUserInformation().getEmail())
                    .from(emailFrom)
                    .htmlTemplate(new Mail.HtmlTemplate("ConfirmationRegisterUser", context))
                    .subject("Agradecemos por abrir uma conta em nosso banco!")
                    .build());
        } catch (Exception e){
            throw new MessagingException("Error building email template", e);
        }

    }

}
