package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.entities.BankAccount;
import com.api.appTransitionBanks.entities.IndividualPerson;
import com.api.appTransitionBanks.repository.IndividualPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.api.appTransitionBanks.enums.TypeAccount.FISICA;


@Service
@RequiredArgsConstructor
public class IndividualPersonServiceImpl {

    private final IndividualPersonRepository individualPersonRepository;

    private final BankAccountService bankAccountService;

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public void save(IndividualPerson person){
        try {
            var userCopy = individualPersonRepository.insert(person);
            bankAccountService.createAccountBanking(BankAccount.builder()
                    .person(userCopy)
                    .typeAccount(FISICA)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Error while saving IndividualPerson", e);
        }

    }

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public void update(IndividualPerson person){
        individualPersonRepository.save(person);
    }

    @Transactional(readOnly = true)
    public IndividualPerson findBy(Example<IndividualPerson> example) {
        return individualPersonRepository.findOne(example).orElse(null);
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
