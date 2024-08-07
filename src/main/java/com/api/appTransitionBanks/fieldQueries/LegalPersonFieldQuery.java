package com.api.appTransitionBanks.fieldQueries;

import com.api.appTransitionBanks.entities.LegalPerson;
import com.api.appTransitionBanks.entities.UserInformation;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;

import java.util.List;

import static org.springframework.data.domain.Example.of;
import static org.springframework.data.domain.ExampleMatcher.matchingAny;

public enum LegalPersonFieldQuery {

    LEGAL_PERSON_ID{
        @Override
        public Example<LegalPerson> existBy(List<String> values) {
            var legalPerson = new LegalPerson();
            legalPerson.set_id(new ObjectId(values.get(0)));
            return of(legalPerson, matchingAny());
        }

        @Override
        public Example<LegalPerson> findBy(List<String> values) {
            return null;
        }
    },
    CNPJ{
        @Override
        public Example<LegalPerson> existBy(List<String> values) {
            var legalPerson = new LegalPerson();
            legalPerson.setCnpj(values.get(0));
            return of(legalPerson, matchingAny());
        }

        @Override
        public Example<LegalPerson> findBy(List<String> values) {
            var legalPerson = new LegalPerson();
            legalPerson.setCnpj(values.get(0));
            return of(legalPerson, matchingAny());
        }
    },
    EMAIL{
        @Override
        public Example<LegalPerson> existBy(List<String> values) {
            var legalPerson = new LegalPerson();
            legalPerson.setUserInformation(UserInformation.builder().email(values.get(0)).build());
            return of(legalPerson, matchingAny());
        }

        @Override
        public Example<LegalPerson> findBy(List<String> values) {
            return null;
        }
    },
    EMAIL_CNPJ{
        @Override
        public Example<LegalPerson> existBy(List<String> values) {
            var legalPerson = new LegalPerson();
            legalPerson.setUserInformation(UserInformation.builder()
                    .email(values.get(0)).build());
            legalPerson.setCnpj(values.get(1));
            return of(legalPerson, matchingAny());
        }

        @Override
        public Example<LegalPerson> findBy(List<String> values) {
            return null;
        }
    };

    public abstract Example<LegalPerson> existBy(List<String> values);

    public abstract Example<LegalPerson> findBy(List<String> values);
}
