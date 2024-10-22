package com.skypro.teamwork.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rule")
public class RuleController {

    private final RuleRepository repository;

    public RuleController(RuleRepository repository) {
        this.repository = repository;
    }

}
