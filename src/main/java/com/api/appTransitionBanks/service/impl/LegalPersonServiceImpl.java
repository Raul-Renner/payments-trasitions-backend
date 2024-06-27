package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.entities.LegalPerson;
import com.api.appTransitionBanks.repository.PersonLegalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.api.appTransitionBanks.enums.TypeAccount.JURIDICA;
import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;

@Service
@RequiredArgsConstructor
public class LegalPersonServiceImpl {

    private final PersonLegalRepository legalRepository;

    private final BankAccountService bankAccountService;


    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public void save(LegalPerson legalPerson){
        var bundle = getBundle("ValidationMessages", getDefault());

        try {
            legalPerson.setBankAccount(bankAccountService.createAccountBanking(JURIDICA));
            legalRepository.insert(legalPerson);
        } catch (Exception e) {
            throw new RuntimeException(bundle.getString("error.register"));
        }

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
            var bundle = getBundle("ValidationMessages", getDefault());
            throw new RuntimeException(bundle.getString("error.delete.user"));
        }
    }

    @Transactional(readOnly = true)
    public LegalPerson findBy(Example<LegalPerson> example) {
        return legalRepository.findOne(example).orElse(null);
    }
    public void update(){}

}
