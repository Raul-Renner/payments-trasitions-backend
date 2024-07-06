package com.api.appTransitionBanks.service.impl;


import com.api.appTransitionBanks.entities.TransitionsHistory;

import com.api.appTransitionBanks.repository.TransitionsHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransitionsHistoryServiceImpl {

    private final TransitionsHistoryRepository transitionsHistoryRepository;


    @Transactional(rollbackFor = { Exception.class, Throwable.class })
    public void save(TransitionsHistory transitionsHistory){
        transitionsHistoryRepository.insert(transitionsHistory);
    }


}
