package com.apiSpring.agregadordeinvestimentos.controllers;

import com.apiSpring.agregadordeinvestimentos.services.StockService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private StockService stockService;

    public AccountController(StockService stockService) {
        this.stockService = stockService;
    }
}
