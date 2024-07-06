package com.api.appTransitionBanks.controller;

import com.api.appTransitionBanks.service.impl.TransitionsHistoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transitions")
public class TransitionHistoryController {

    private final TransitionsHistoryServiceImpl transitionsHistory;

}
