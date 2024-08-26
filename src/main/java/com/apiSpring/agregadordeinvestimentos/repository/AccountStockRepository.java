package com.apiSpring.agregadordeinvestimentos.repository;

import com.apiSpring.agregadordeinvestimentos.entity.AccountStock;
import com.apiSpring.agregadordeinvestimentos.entity.AccountStockId;
import com.apiSpring.agregadordeinvestimentos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> { }
