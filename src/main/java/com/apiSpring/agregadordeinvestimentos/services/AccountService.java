package com.apiSpring.agregadordeinvestimentos.services;

import com.apiSpring.agregadordeinvestimentos.repository.AccountRepository;
import com.apiSpring.agregadordeinvestimentos.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private StockRepository stockRepository;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
    }
}
