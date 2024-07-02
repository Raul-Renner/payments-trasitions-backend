package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.dto.DepositeDTO;
import com.api.appTransitionBanks.dto.TransferDTO;
import com.api.appTransitionBanks.entities.BankAccount;
import com.api.appTransitionBanks.entities.MenuNotification;
import com.api.appTransitionBanks.entities.TransitionsHistory;
import com.api.appTransitionBanks.enums.TypeAccount;
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

    private final UserServiceImpl userService;


    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public BankAccount createAccountBanking(TypeAccount typeAccount){
        return bankRepository.insert(BankAccount.builder()
                        .numberAccount(randomNumbers(7))
                        .passwordApp(randomNumbers(6))
                        .typeAccount(typeAccount)
                        .balance(0.0)
                        .build());
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void executetTransfer(TransferDTO transferDTO){
        var bundle = getBundle("ValidationMessages", getDefault());

        try {
            var accountReceive = bankRepository.findByNumberAccount(transferDTO.accountReceiver());
            accountReceive.setBalance(accountReceive.getBalance() + transferDTO.valueTransfer());
            var accountSender = bankRepository.findByNumberAccount(transferDTO.accountSender());
            accountSender.setBalance(accountSender.getBalance() - transferDTO.valueTransfer());

            update(accountReceive);
            update(accountSender);

            var userSender = userService.findBy(transferDTO.accountSender());
            var userReceiver = userService.findBy(transferDTO.accountReceiver());

            transitionsHistoryService.save(
                    TransitionsHistory.builder()
                            .transitionsTypeEnum(TRANSFER)
                            .valueTransition(transferDTO.valueTransfer())
                            .personSender(userSender)
                            .personReceiver(userReceiver)
                            .date(new Date())
                            .description(bundle.getString("description-part1") + userSender.getUserInformation().getName() + " "
                            + userSender.getUserInformation().getLastname() + bundle.getString("description-part2") + " " + transferDTO.valueTransfer())
                            .build());

            notificationService.save(MenuNotification.builder()
                            .notificationType(TRANSITION)
                            .created(new Date())
                            .person(userReceiver)
                            .message(bundle.getString("description-part1") + userSender.getUserInformation().getName() + " "
                                    + userSender.getUserInformation().getLastname() + bundle.getString("description-part2") + " " + transferDTO.valueTransfer())
                            .isNotified(false)
                            .build());


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
    public Boolean authorizeTransfer(TransferDTO transferDTO){
        var accountSender = bankRepository.findByNumberAccount(transferDTO.accountSender());
        return accountSender.getBalance() > 0 && accountSender.getBalance() >= transferDTO.valueTransfer();
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
