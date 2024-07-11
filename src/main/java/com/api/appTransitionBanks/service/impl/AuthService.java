package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.dto.AuthenticatedResponseDTO;
import com.api.appTransitionBanks.entities.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.api.appTransitionBanks.fieldQueries.BankAccountFieldQuery.ACCOUNT;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final BankAccountService bankAccountService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String numberAccount) throws UsernameNotFoundException {
        try{
            var accountBankUser = bankAccountService.findBy(ACCOUNT.findBy(List.of(numberAccount)));
            if(isNull(accountBankUser)) throw new UsernameNotFoundException("Account not found");

            return UserDetailsImpl.build(accountBankUser);
        }catch (Exception e){
            throw new UsernameNotFoundException(e.getMessage());
        }

    }

    public AuthenticatedResponseDTO authenticatedResponse(String token, UserDetailsImpl userDetailsAuth){

        return AuthenticatedResponseDTO.builder()
                .person(userDetailsAuth.getPerson())
                .balance(userDetailsAuth.getBalance())
                .passwordApp(userDetailsAuth.getPasswordApp())
                .typeAccount(userDetailsAuth.getTypeAccount())
                .numberAccount(userDetailsAuth.getNumberAccount())
                ._id(String.valueOf(userDetailsAuth.get_id()))
                .token(token)
                .build();
    }

}
