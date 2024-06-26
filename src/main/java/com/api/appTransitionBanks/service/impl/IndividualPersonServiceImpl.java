package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.entities.IndividualPerson;
import com.api.appTransitionBanks.repository.IndividualPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.api.appTransitionBanks.enums.TypeAccount.FISICA;

@Service
@RequiredArgsConstructor
public class IndividualPersonServiceImpl {


    private final IndividualPersonRepository individualPersonRepository;

    private final BankAccountService bankAccountService;

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public void save(IndividualPerson person){
        person.setBankAccount(bankAccountService.createAccountBanking(FISICA));
        individualPersonRepository.insert(person);
    }

    @Transactional(readOnly = true)
    public IndividualPerson findBy(Example<IndividualPerson> example) {
        return individualPersonRepository.findOne(example).orElse(null);
    }

    public void transferMoney(){

    }

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public void deleteProfile(String id){
        try {
            individualPersonRepository.deleteById(id);
        } catch (Exception e) {

        }
    }

    public Optional<IndividualPerson> findById(String id){
        return individualPersonRepository.findById(id);
    }

    public List<IndividualPerson> findAll() {
        return individualPersonRepository.findAll();
    }

    public Boolean existBy(Example<IndividualPerson> example) {
        return individualPersonRepository.exists(example);
    }
}
