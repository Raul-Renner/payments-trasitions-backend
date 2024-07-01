package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.entities.IndividualPerson;
import com.api.appTransitionBanks.repository.IndividualPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class IndividualPersonServiceImpl {


    private final IndividualPersonRepository individualPersonRepository;

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public void save(IndividualPerson person){
        individualPersonRepository.insert(person);
    }

    @Transactional(readOnly = true)
    public IndividualPerson findBy(Example<IndividualPerson> example) {
        return individualPersonRepository.findOne(example).orElse(null);
    }

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public void deleteProfile(String id){
        try {
            individualPersonRepository.deleteById(id);
        } catch (Exception e) {

        }
    }

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public Optional<IndividualPerson> findById(String id){
        return individualPersonRepository.findById(id);
    }

    @Transactional(rollbackFor = { Exception.class, Throwable.class })

    public List<IndividualPerson> findAll() {
        return individualPersonRepository.findAll();
    }

    @Transactional(rollbackFor = { Exception.class, Throwable.class })

    public Boolean existBy(Example<IndividualPerson> example) {
        return individualPersonRepository.exists(example);
    }
}
