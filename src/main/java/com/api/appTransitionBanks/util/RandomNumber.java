package com.api.appTransitionBanks.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RandomNumber {

    public String randomNumbers(int qtdNumber){
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
