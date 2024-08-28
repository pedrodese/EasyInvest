package com.apiSpring.agregadordeinvestimentos.controllers;

import com.apiSpring.agregadordeinvestimentos.DTOs.CreateStockDto;
import com.apiSpring.agregadordeinvestimentos.DTOs.CreateUserDto;
import com.apiSpring.agregadordeinvestimentos.entity.User;
import com.apiSpring.agregadordeinvestimentos.services.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/stocks")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    //EndPoint para cadastrar uma ação no banco
    @PostMapping()
    public ResponseEntity<Void> createStock(@RequestBody CreateStockDto createStockDto){
        stockService.createStock(createStockDto);
        return ResponseEntity.ok().build();
    }
}
