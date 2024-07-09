package com.api.appTransitionBanks;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.ALL;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;
import static com.fasterxml.jackson.databind.DeserializationFeature.*;
import static com.fasterxml.jackson.databind.SerializationFeature.*;

@EnableMongoRepositories
@SpringBootApplication
public class AppTransitionBanksApplication{

	public static void main(String[] args) {
		SpringApplication.run(AppTransitionBanksApplication.class, args);
	}

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		return new ObjectMapper()
				.setVisibility(ALL, NONE)
				.setVisibility(FIELD, ANY)
				.setSerializationInclusion(NON_EMPTY)
				.enable(INDENT_OUTPUT)
				.enable(USE_EQUALITY_FOR_OBJECT_ID)
				.enable(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
				.disable(WRITE_DATES_AS_TIMESTAMPS)
				.disable(ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.disable(FAIL_ON_UNKNOWN_PROPERTIES);
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer customizer()
	{
		return builder -> builder.serializerByType(ObjectId.class,new ToStringSerializer());
	}

}
