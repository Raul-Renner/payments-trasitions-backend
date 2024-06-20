package com.api.appTransitionBanks.fieldQueries;

import com.api.appTransitionBanks.entities.BankAccount;
import org.springframework.data.domain.Example;

import java.util.List;

import static org.springframework.data.domain.Example.of;
import static org.springframework.data.domain.ExampleMatcher.matchingAny;

public enum BankAccountFieldQuery {

    ACCOUNT {
        @Override
        public Example<BankAccount> existBy(List<String> values) {
            return of(BankAccount.builder().numberAccount(values.get(0)).build(), matchingAny());
        }

        @Override
        public Example<BankAccount> findBy(String values) {
            return null;
        }
    };

    public abstract Example<BankAccount> existBy(List<String> values);

    public abstract Example<BankAccount> findBy(String values);
}
