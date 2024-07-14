package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.entities.BankAccount;
import com.api.appTransitionBanks.entities.LegalPerson;
import com.api.appTransitionBanks.repository.PersonLegalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.api.appTransitionBanks.enums.TypeAccount.JURIDICA;
import static com.api.appTransitionBanks.fieldQueries.LegalPersonFieldQuery.CNPJ;
import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;

@Service
@RequiredArgsConstructor
public class LegalPersonServiceImpl {

    private final PersonLegalRepository legalRepository;

    private final BankAccountService bankAccountService;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public void processSave(LegalPerson legalPerson){
        var bundle = getBundle("ValidationMessages", getDefault());
        try {
           var userCopy = save(legalPerson);

           var bankAccountCopy = bankAccountService.createAccountBanking(BankAccount.builder()
                    .person(userCopy)
                    .typeAccount(JURIDICA)
                    .build());


          emailService.buildTemplateEmailToSendUser(bankAccountCopy);

          bankAccountCopy.setPasswordApp(passwordEncoder.encode(bankAccountCopy.getPasswordApp()));
          bankAccountService.update(bankAccountCopy);

        } catch (Exception e) {
            throw new RuntimeException(bundle.getString("error.register"));
        }

    }

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public LegalPerson save(LegalPerson legalPerson){
        try {
            return legalRepository.insert(legalPerson);
        } catch (Exception e){
            throw new RuntimeException("Error saving LegalPerson");
        }
    }

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public void processUpdate(LegalPerson legalPerson){
        var bundle = getBundle("ValidationMessages", getDefault());
        try {
            var userCopy = findBy(CNPJ.findBy(List.of(legalPerson.getCnpj())));
            userCopy.getUserInformation().setEmail(legalPerson.getUserInformation().getEmail());
            userCopy.getUserInformation().setName(legalPerson.getUserInformation().getName());
            userCopy.setCorporateReason(legalPerson.getCorporateReason());
            userCopy.getUserInformation().setLastname(legalPerson.getUserInformation().getLastname());
            update(userCopy);
        } catch (Exception e) {
            throw new RuntimeException(bundle.getString("Error in process updating LegalPerson"));
        }

    }

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public LegalPerson update(LegalPerson legalPerson){
        try {
            return legalRepository.save(legalPerson);
        } catch (Exception e){
            throw new RuntimeException("Error updating LegalPerson");
        }
    }

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public Boolean existBy(Example<LegalPerson> example) {
        return legalRepository.exists(example);
    }


    @Transactional(readOnly = true)
    public LegalPerson findBy(Example<LegalPerson> example) {
        return legalRepository.findOne(example).orElse(null);
    }

}
