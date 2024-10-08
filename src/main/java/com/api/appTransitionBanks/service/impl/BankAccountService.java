package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.dto.DepositeDTO;
import com.api.appTransitionBanks.dto.TransferDTO;
import com.api.appTransitionBanks.entities.*;

import com.api.appTransitionBanks.repository.BankRepository;
import com.api.appTransitionBanks.util.RandomNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.api.appTransitionBanks.enums.NotificationTypeEnum.TRANSITION;
import static com.api.appTransitionBanks.enums.TransitionsTypeEnum.TRANSFER;
import static com.api.appTransitionBanks.fieldQueries.BankAccountFieldQuery.ACCOUNT;
import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;


@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankRepository bankRepository;

    private final TransitionsHistoryServiceImpl transitionsHistoryService;

    private final NotificationServiceImpl notificationService;

    private final MongoTemplate mongoTemplate;

    private final RandomNumber randomNumbers;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public BankAccount createAccountBanking(BankAccount bankAccount){
        try{
            bankAccount.setNumberAccount(randomNumbers.randomNumbers(7));
            bankAccount.setPasswordApp(randomNumbers.randomNumbers(6));
            bankAccount.setBalance(0.0);
           return bankRepository.insert(bankAccount);
        } catch (Exception e) {
            throw new RuntimeException("Error creating account banking account", e);
        }

    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void executetTransfer(TransferDTO transferDTO){
        try {
            var bundle = getBundle("ValidationMessages", getDefault());

            var accountReceive = bankRepository.findByNumberAccount(transferDTO.accountReceiver().numberAccount());
            accountReceive.setBalance(accountReceive.getBalance() + transferDTO.valueTransfer());
            var accountSender = bankRepository.findByNumberAccount(transferDTO.accountSender().numberAccount());
            accountSender.setBalance(accountSender.getBalance() - transferDTO.valueTransfer());

            update(accountReceive);
            update(accountSender);

            transitionsHistoryService.save(
                    TransitionsHistory.builder()
                            .date(new Date())
                            .valueTransition(transferDTO.valueTransfer())
                            .transitionsTypeEnum(TRANSFER)
                            .personReceiver(accountReceive.getPerson())
                            .personSender(accountSender.getPerson())
                            .description(bundle.getString("description-part1") + accountSender.getPerson().getUserInformation().getName() + " "
                              + accountSender.getPerson().getUserInformation().getLastname() + bundle.getString("description-part2") + " " + transferDTO.valueTransfer())
                            .build()
            );

            var userSender = accountSender.getPerson().getUserInformation().getName() + " " + accountSender.getPerson().getUserInformation().getLastname();
            notificationService.save(
                    MenuNotification.builder()
                            .created(new Date())
                            .isNotified(false)
                            .person(accountReceive.getPerson())
                            .notificationType(TRANSITION)
                            .message(bundle.getString("message.notification") + "R$ " + transferDTO.valueTransfer() + " de " + userSender)
                            .build()
            );


        }catch (Exception e){
            throw new RuntimeException("Erro ao executar transferencia!");
        }
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void update(BankAccount bankAccount) {
        try {
            bankRepository.save(bankAccount);
        }catch (Exception e){
            throw new RuntimeException("Erro ao atualizar dados da conta bancaria");
        }
    }


    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public Boolean authorizeTransfer(String numberAccountSender, Double valueTransfer){
        var accountSender = bankRepository.findByNumberAccount(numberAccountSender);
        return accountSender.getBalance() > 0 && accountSender.getBalance() >= valueTransfer
                && accountSender.getTypeAccount().name().equals("FISICA");
    }

    @Transactional(readOnly = true)
    public boolean existBy(Example<BankAccount> example) {
        return bankRepository.exists(example);
    }

    @Transactional(readOnly = true)
    public BankAccount findBy(Example<BankAccount> example) {
        return bankRepository.findOne(example).orElse(null);
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void realizeDeposite(DepositeDTO depositeDTO){
        var accountSender = bankRepository.findByNumberAccount(depositeDTO.accountSender());
        accountSender.setBalance(accountSender.getBalance() + depositeDTO.valueDeposit());
        update(accountSender);
    }

    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public void delete(String numberAccount){
        try {
            var bankDelete = findBy(ACCOUNT.findBy(List.of(numberAccount)));
            var userDeleteId = bankDelete.getPerson().get_id();

            switch (bankDelete.getTypeAccount()){
                case JURIDICA -> mongoTemplate.remove(new Query(Criteria.where("_id").in(userDeleteId)), LegalPerson.class);
                case FISICA -> mongoTemplate.remove(new Query(Criteria.where("_id").in(userDeleteId)), IndividualPerson.class);
            }

            bankRepository.delete(bankDelete);
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting Account Bank", e);
        }
    }
}
