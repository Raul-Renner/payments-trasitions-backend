package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.entities.IndividualPerson;
import com.api.appTransitionBanks.repository.IndividualPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.api.appTransitionBanks.enums.TypeAccount.FISICA;

@Service
@RequiredArgsConstructor
public class IndividualPersonServiceImpl {


    private final IndividualPersonRepository individualPersonRepository;

    private final BankAccountService bankAccountService;

    public void save(IndividualPerson person){
        person.setBankAccount(bankAccountService.createAccountBanking(FISICA));
        individualPersonRepository.insert(person);
    }

    public void transferMoney(){

    }

    public void deleteAccount(String id){
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
