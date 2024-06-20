package com.api.appTransitionBanks.fieldQueries;

import com.api.appTransitionBanks.entities.IndividualPerson;
import org.springframework.data.domain.Example;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.matchingAny;
import static org.springframework.data.domain.Example.of;

public enum IndividualPersonFieldQuery {

    CPF {
        @Override
        public Example<IndividualPerson> existBy(List<String> values) {
            var individualPerson = new IndividualPerson();
            individualPerson.setCpf(values.get(0));
            return of(individualPerson, matchingAny());
        }

        @Override
        public Example<IndividualPerson> findBy(String values) {
            return null;
        }
    },
    EMAIL{
        @Override
        public Example<IndividualPerson> existBy(List<String> values) {
            var individualPerson = new IndividualPerson();
            individualPerson.getUserInformation().setEmail(values.get(0));
            return of(individualPerson, matchingAny());
        }

        @Override
        public Example<IndividualPerson> findBy(String values) {
            return null;
        }
    };

    public abstract Example<IndividualPerson> existBy(List<String> values);

    public abstract Example<IndividualPerson> findBy(String values);

}
