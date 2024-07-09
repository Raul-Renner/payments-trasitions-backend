package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.dto.DepositeDTO;
import com.api.appTransitionBanks.dto.TransferDTO;
import com.api.appTransitionBanks.entities.BankAccount;
import com.api.appTransitionBanks.entities.MenuNotification;
import com.api.appTransitionBanks.entities.TransitionsHistory;

import com.api.appTransitionBanks.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.api.appTransitionBanks.enums.NotificationTypeEnum.TRANSITION;
import static com.api.appTransitionBanks.enums.TransitionsTypeEnum.TRANSFER;
import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;


@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankRepository bankRepository;

    private final TransitionsHistoryServiceImpl transitionsHistoryService;

    private final NotificationServiceImpl notificationService;


    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void createAccountBanking(BankAccount bankAccount){
        bankAccount.setNumberAccount(randomNumbers(7));
        bankAccount.setPasswordApp(randomNumbers(6));
        bankAccount.setBalance(0.0);
        bankRepository.insert(bankAccount);
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

    private String randomNumbers(int qtdNumber){
        var chars = "0123456789";
        var numbers = "";
        var digit = "";
        for(int i = 0; i < qtdNumber; i++){
            var caracter = (int) (Math.random() * chars.length());
            numbers += chars.substring(caracter, caracter + 1);
            digit = chars.substring(caracter, caracter + 1);
        }
        if(qtdNumber == 7){
            numbers = numbers + "-" + digit;
        }
        return numbers;
    }

}
