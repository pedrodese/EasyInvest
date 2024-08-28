package com.apiSpring.agregadordeinvestimentos.services;

import com.apiSpring.agregadordeinvestimentos.DTOs.CreateStockDto;
import com.apiSpring.agregadordeinvestimentos.entity.Stock;
import com.apiSpring.agregadordeinvestimentos.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    public void createStock(CreateStockDto createStockDto) {
        //DTO -> ENTITY
        var stock = new Stock(
            createStockDto.stockId(),
            createStockDto.description()
        );

        stockRepository.save(stock);

    }
}
