package com.api.appTransitionBanks.fieldQueries;

import com.api.appTransitionBanks.entities.BankAccount;
import com.api.appTransitionBanks.entities.IndividualPerson;
import com.api.appTransitionBanks.entities.LegalPerson;
import com.api.appTransitionBanks.entities.UserInformation;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.ResourceBundle;

import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;
import static org.springframework.data.domain.ExampleMatcher.matchingAny;
import static org.springframework.data.domain.Example.of;

public enum IndividualPersonFieldQuery {


    INDIVIDUAL_PERSON_ID{
        @Override
        public Example<IndividualPerson> existBy(List<String> values) {
            var individualPerson = new IndividualPerson();
            individualPerson.set_id(new ObjectId(values.get(0)));
            return of(individualPerson, matchingAny());
        }

        @Override
        public Example<IndividualPerson> findBy(List<String> values) {
            return null;
        }
    },

    CPF_NUMBER_ACCOUNT{
        @Override
        public Example<IndividualPerson> existBy(List<String> values) {
            return null;
        }

        @Override
        public Example<IndividualPerson> findBy(List<String> values) {
            if (values.size() < 2) {
                ResourceBundle bundle = getBundle("ValidationMessages", getDefault());
                throw new RuntimeException(bundle.getString("field-query.validations.invalid.number-parameters"));
            }
            var individualPerson = new IndividualPerson();
            individualPerson.setCpf(values.get(0));
            individualPerson.setBankAccount(BankAccount.builder().numberAccount(values.get(1)).build());

            return of(individualPerson, matchingAny());
        }
    },
    CPF {
        @Override
        public Example<IndividualPerson> existBy(List<String> values) {
            var individualPerson = new IndividualPerson();
            individualPerson.setCpf(values.get(0));
            return of(individualPerson, matchingAny());
        }

        @Override
        public Example<IndividualPerson> findBy(List<String> values) {
            return null;
        }
    },
    EMAIL{
        @Override
        public Example<IndividualPerson> existBy(List<String> values) {
            var individualPerson = new IndividualPerson();
            individualPerson.setUserInformation(UserInformation.builder().email(values.get(0)).build());
            return of(individualPerson, matchingAny());
        }

        @Override
        public Example<IndividualPerson> findBy(List<String> values) {
            return null;
        }
    };

    public abstract Example<IndividualPerson> existBy(List<String> values);

    public abstract Example<IndividualPerson> findBy(List<String> values);

}
