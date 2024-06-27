package com.api.appTransitionBanks.repository;

import com.api.appTransitionBanks.entities.TransitionsHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableMongoRepositories
public interface TransitionsHistoryRepository extends MongoRepository<TransitionsHistory, String> {

    TransitionsHistory findByPersonReceiverAndPersonSender(String numberAccount);
}
