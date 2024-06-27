package com.api.appTransitionBanks.service.impl;

import com.api.appTransitionBanks.entities.MenuNotification;
import com.api.appTransitionBanks.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl {

    private final NotificationRepository notificationRepository;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void save(MenuNotification menuNotification){
        notificationRepository.insert(menuNotification);
    }

}
