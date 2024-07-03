package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.dto.PersonDTO;
import com.api.appTransitionBanks.dto.TransferDTO;
import com.api.appTransitionBanks.dto.TransitionHistoryDTO;
import com.api.appTransitionBanks.entities.TransitionsHistory;
import com.api.appTransitionBanks.enums.TypeAccount;
import com.api.appTransitionBanks.fieldQueries.LegalPersonFieldQuery;
import com.api.appTransitionBanks.repository.TransitionsHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.api.appTransitionBanks.enums.TypeAccount.FISICA;
import static com.api.appTransitionBanks.fieldQueries.IndividualPersonFieldQuery.ACCOUNT;
import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;

@Service
@RequiredArgsConstructor
public class TransitionsHistoryServiceImpl {

    private final TransitionsHistoryRepository transitionsHistoryRepository;

    private final IndividualPersonServiceImpl individualPersonService;

    private final LegalPersonServiceImpl legalPersonService;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void processSave(TransitionHistoryDTO historyDTO){
        try{
            var bundle = getBundle("ValidationMessages", getDefault());
            var userSender = historyDTO.accountPersonSender().typeAccount().equals(FISICA)
                    ? individualPersonService.findBy(ACCOUNT.findBy(List.of(historyDTO.accountPersonSender().numberAccount())))
                    : legalPersonService.findBy(LegalPersonFieldQuery.ACCOUNT.findBy(List.of(historyDTO.accountPersonSender().numberAccount())));

            var userReceiver = historyDTO.accountReceiver().typeAccount().equals(FISICA)
                    ? individualPersonService.findBy(ACCOUNT.findBy(List.of(historyDTO.accountReceiver().numberAccount())))
                    : legalPersonService.findBy(LegalPersonFieldQuery.ACCOUNT.findBy(List.of(historyDTO.accountReceiver().numberAccount())));

            save(TransitionsHistory.builder()
                    .valueTransition(historyDTO.valueTransition())
                    .transitionsTypeEnum(historyDTO.transitionsTypeEnum())
                    .personReceiver(userReceiver)
                    .personSender(userSender)
                    .date(new Date())
                    .description(bundle.getString("description-part1") + userSender.getUserInformation().getName() + " "
                            + userSender.getUserInformation().getLastname() + bundle.getString("description-part2") + " " + historyDTO.valueTransition())
                    .build());
        } catch (Exception e){
            throw new RuntimeException("Error while saving history", e);
        }

    }

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public void save(TransitionsHistory transitionsHistory){
        transitionsHistoryRepository.insert(transitionsHistory);
    }

    @Transactional(readOnly = true)
    public TransitionsHistory findTransitionByNumberAccount(PersonDTO personDTO){
        return transitionsHistoryRepository.findByPersonReceiverAndPersonSender(personDTO.bankAccountDTO().numberAccount());
    }

}
