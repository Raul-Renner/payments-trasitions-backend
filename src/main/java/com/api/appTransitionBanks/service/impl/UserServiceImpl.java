package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.entities.IndividualPerson;
import com.api.appTransitionBanks.entities.Person;
import com.api.appTransitionBanks.repository.PersonRepository;
import com.api.appTransitionBanks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PersonRepository personRepository;

//    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public void viewNotifications() {

    }

    @Override
    public void viewHistoricTransitions() {

    }

    @Override
    public void requestReportTransitions() {

    }

    @Override
    public void depositValueAccount() {

    }
}
