package com.api.appTransitionBanks.repository;

import com.api.appTransitionBanks.entities.MenuNotification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableMongoRepositories
public interface NotificationRepository extends MongoRepository<MenuNotification, String> {

}
