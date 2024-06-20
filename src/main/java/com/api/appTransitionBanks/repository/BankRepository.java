package com.api.appTransitionBanks.repository;

import com.api.appTransitionBanks.entities.BankAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableMongoRepositories
public interface BankRepository extends MongoRepository<BankAccount, String> {

    BankAccount findByNumberAccount(String numberAccount);
}
