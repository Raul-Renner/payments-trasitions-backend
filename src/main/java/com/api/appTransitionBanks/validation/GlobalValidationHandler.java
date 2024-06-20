package com.api.appTransitionBanks.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


import static io.vavr.API.Option;
import static io.vavr.API.Try;
import static io.vavr.collection.LinkedHashSet.ofAll;
import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;
import static java.util.stream.Collectors.toSet;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.core.publisher.Mono.just;

@Slf4j
@Order(-2)
@Configuration
public class GlobalValidationHandler implements ErrorWebExceptionHandler {

    @Qualifier("objectMapper")
    private final ObjectMapper objectMapper;

    public GlobalValidationHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange server, Throwable t) {
        server.getResponse().setStatusCode(BAD_REQUEST);
        server.getResponse().getHeaders().setContentType(APPLICATION_JSON);
        ResourceBundle validationBundle = getBundle("ValidationMessages", getDefault());
        ResourceBundle messageBundle = getBundle("Messages", getDefault());

        var factory = server.getResponse().bufferFactory();

        if (t instanceof WebExchangeBindException) {
            var value = ofAll(((WebExchangeBindException) t).getAllErrors()).map(MessageValidation::new).collect(toSet()).toArray();
            return process(factory, server, value);
        } else if (t instanceof ConstraintViolationException) {
            log.error(t.getMessage());
            var value = Option((ConstraintViolationException) t).map(i -> i.getConstraintViolations()
                .stream().map(cv -> MessageValidation.builder().message(cv.getMessageTemplate()).fieldName(cv.getPropertyPath().toString()).build())
                .collect(Collectors.toSet())).get().toArray();
            return process(factory, server, value);
        } else if (t instanceof ValidationException) {
            var value = Option((ValidationException) t).map(i -> MessageValidation.builder()
                .message(validationBundle.getString("generic.validation.error")).build()).get();
            if(t.getCause() instanceof ResourceAccessException) {
                value = Option((ValidationException) t).map(i -> MessageValidation.builder()
                    .message(messageBundle.getString("connection.error")).build()).get();
                log.error(value.getMessage());
            } else {
                log.error(t.getMessage(), t);
            }
            return process(factory, server, value);
        } else if(t instanceof RuntimeException && !(t instanceof NullPointerException)) {
            log.error(t.getMessage(), t);
            return process(factory, server, t.getMessage());
        } else {
            log.error(t.getMessage(), t);
            return server.getResponse().writeWith(just(factory.wrap(messageBundle.getString("generic.error").getBytes())));
        }
    }

    private Mono<Void> process(DataBufferFactory factory, ServerWebExchange server, Serializable value) {
        var buffer = Try(() -> objectMapper.writeValueAsBytes(value))
            .map(factory::wrap)
            .onFailure(i -> factory.wrap(i.getMessage().getBytes())).get();
        return server.getResponse().writeWith(just(buffer));
    }
}