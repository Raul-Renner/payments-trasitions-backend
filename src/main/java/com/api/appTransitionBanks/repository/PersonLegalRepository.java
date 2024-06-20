package com.api.appTransitionBanks.repository;

import com.api.appTransitionBanks.entities.LegalPerson;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@EnableMongoRepositories
public interface PersonLegalRepository extends MongoRepository<LegalPerson, String> {
}
