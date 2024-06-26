package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.entities.LegalPerson;
import com.api.appTransitionBanks.repository.PersonLegalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.api.appTransitionBanks.enums.TypeAccount.JURIDICA;

@Service
@RequiredArgsConstructor
public class LegalPersonServiceImpl {

    private final PersonLegalRepository legalRepository;

    private final BankAccountService bankAccountService;

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public void save(LegalPerson legalPerson){
        legalPerson.setBankAccount(bankAccountService.createAccountBanking(JURIDICA));
        legalRepository.insert(legalPerson);
    }

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public Boolean existBy(Example<LegalPerson> example) {
        return legalRepository.exists(example);
    }

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public void deleteProfile(String id){
        try {
            legalRepository.deleteById(id);
        } catch (Exception e) {

        }
    }

    @Transactional(readOnly = true)
    public LegalPerson findBy(Example<LegalPerson> example) {
        return legalRepository.findOne(example).orElse(null);
    }
    public void update(){}

}
