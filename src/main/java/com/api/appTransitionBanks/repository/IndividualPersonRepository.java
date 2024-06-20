package com.api.appTransitionBanks.repository;

import com.api.appTransitionBanks.entities.IndividualPerson;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@EnableMongoRepositories
public interface IndividualPersonRepository extends MongoRepository<IndividualPerson, String> {
}
