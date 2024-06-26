package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.dto.TransferDTO;
import com.api.appTransitionBanks.entities.BankAccount;
import com.api.appTransitionBanks.enums.TypeAccount;
import com.api.appTransitionBanks.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankRepository bankRepository;

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
        try {
            var accountReceive = bankRepository.findByNumberAccount(transferDTO.accountReceiver());
            accountReceive.setBalance(accountReceive.getBalance() + transferDTO.valueTransfer());
            var accountSender = bankRepository.findByNumberAccount(transferDTO.accountSender());
            accountSender.setBalance(accountSender.getBalance() - transferDTO.valueTransfer());

            update(accountReceive);
            update(accountSender);

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
