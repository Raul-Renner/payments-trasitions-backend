package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.entities.BankAccount;
import com.api.appTransitionBanks.entities.IndividualPerson;
import com.api.appTransitionBanks.repository.IndividualPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.api.appTransitionBanks.enums.TypeAccount.FISICA;
import static com.api.appTransitionBanks.fieldQueries.IndividualPersonFieldQuery.CPF;


@Service
@RequiredArgsConstructor
public class IndividualPersonServiceImpl {

    private final IndividualPersonRepository individualPersonRepository;

    private final BankAccountService bankAccountService;

    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public void processSave(IndividualPerson person){
        try {
            var userCopy = save(person);
            var bankAccountCopy = bankAccountService.createAccountBanking(BankAccount.builder()
                    .person(userCopy)
                    .typeAccount(FISICA)
                    .build());

            bankAccountCopy.setPasswordApp(passwordEncoder.encode(bankAccountCopy.getPasswordApp()));
            bankAccountService.update(bankAccountCopy);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving IndividualPerson", e);
        }

    }

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public IndividualPerson save(IndividualPerson person){
        try {
            return individualPersonRepository.insert(person);
        } catch (Exception e){
            throw new RuntimeException("Error while saving IndividualPerson", e);
        }
    }

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public void processUpdate(IndividualPerson person){
        try {
            var userCopy = findBy(CPF.findBy(List.of(person.getCpf())));
            userCopy.getUserInformation().setEmail(person.getUserInformation().getEmail());
            userCopy.getUserInformation().setName(person.getUserInformation().getName());
            userCopy.getUserInformation().setLastname(person.getUserInformation().getLastname());
            update(userCopy);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving IndividualPerson", e);
        }

    }

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public void update(IndividualPerson person){
        try {
            individualPersonRepository.save(person);
        } catch (Exception e){
            throw new RuntimeException("Error while updating IndividualPerson", e);
        }
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
