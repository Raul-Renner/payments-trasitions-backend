package com.api.appTransitionBanks.entities.auth;

import com.api.appTransitionBanks.entities.BankAccount;
import com.api.appTransitionBanks.entities.Person;
import com.api.appTransitionBanks.enums.TypeAccount;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {


    private String _id;

    private String numberAccount;

    private String passwordApp;

    private Double balance;

    private TypeAccount typeAccount;

    private Person person;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String id, Person person, String numberAccount, Double balance,
                           TypeAccount typeAccount, String passwordApp,
                           Collection<? extends GrantedAuthority> authorities) {
        this._id = id;
        this.person = person;
        this.numberAccount = numberAccount;
        this.balance = balance;
        this.typeAccount = typeAccount;
        this.passwordApp = passwordApp;
        this.authorities = authorities;
    }


    public static UserDetailsImpl build(BankAccount bankAccount){
        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + bankAccount.getTypeAccount().name()));

        return new UserDetailsImpl(
                String.valueOf(bankAccount.get_id()),
                bankAccount.getPerson(),
                bankAccount.getNumberAccount(),
                bankAccount.getBalance(),
                bankAccount.getTypeAccount(),
                bankAccount.getPasswordApp(),
                authorities);

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return passwordApp;
    }

    @Override
    public String getUsername() {
        return numberAccount;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
