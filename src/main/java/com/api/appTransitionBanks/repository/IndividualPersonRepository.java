package com.api.appTransitionBanks.repository;

import com.api.appTransitionBanks.entities.IndividualPerson;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;
@Repository
@EnableMongoRepositories
public interface IndividualPersonRepository extends MongoRepository<IndividualPerson, String> {
}
