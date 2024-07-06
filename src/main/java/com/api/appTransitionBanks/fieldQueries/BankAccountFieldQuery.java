package com.api.appTransitionBanks.fieldQueries;

import com.api.appTransitionBanks.entities.BankAccount;
import com.api.appTransitionBanks.enums.TypeAccount;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.ResourceBundle;

import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;
import static org.springframework.data.domain.Example.of;
import static org.springframework.data.domain.ExampleMatcher.matchingAll;
import static org.springframework.data.domain.ExampleMatcher.matchingAny;

public enum BankAccountFieldQuery {

    ACCOUNT {
        @Override
        public Example<BankAccount> existBy(List<String> values) {
            return of(BankAccount.builder().numberAccount(values.get(0)).build(), matchingAny());
        }

        @Override
        public Example<BankAccount> findBy(List<String> values) {
            var bankAccount = BankAccount.builder()
                    .numberAccount(values.get(0))
                    .build();
            return of(bankAccount, matchingAll().withIgnoreNullValues());
        }
    },
    INDIVIDUAL_NUMBER_ACCOUNT {
        @Override
        public Example<BankAccount> existBy(List<String> values) {
            return of(BankAccount.builder().
                    numberAccount(values.get(1))
                    .typeAccount(TypeAccount.valueOf(values.get(0)))
                    .build(), matchingAny());
        }

        @Override
        public Example<BankAccount> findBy(List<String> values) {
            if (values.size() < 2) {
                ResourceBundle bundle = getBundle("ValidationMessages", getDefault());
                throw new RuntimeException(bundle.getString("field-query.validations.invalid.number-parameters"));
            }
            var bankAccount = new BankAccount();
            bankAccount.setTypeAccount(TypeAccount.valueOf(values.get(0)));
            bankAccount.setNumberAccount(values.get(1));

            return of(bankAccount, matchingAny());
        }
    };

    public abstract Example<BankAccount> existBy(List<String> values);

    public abstract Example<BankAccount> findBy(List<String> values);
}
