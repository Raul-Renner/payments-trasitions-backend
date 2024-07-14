package com.api.appTransitionBanks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.username}")
    private String userName;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(userName);
        mailSender.setPassword(password);

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.connectiontimeout", 10000);

        mailSender.setJavaMailProperties(props);

        return mailSender;
    }

    @Bean
    public ClassLoaderTemplateResolver htmlTemplateResolver(){
        ClassLoaderTemplateResolver templateResolverEmail = new ClassLoaderTemplateResolver();
        templateResolverEmail.setPrefix("templates/");
        templateResolverEmail.setSuffix(".html");
        templateResolverEmail.setTemplateMode(TemplateMode.HTML);
        templateResolverEmail.setCharacterEncoding(StandardCharsets.UTF_8.name());

        return templateResolverEmail;

    }

    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(htmlTemplateResolver());

        return engine;
    }


}
