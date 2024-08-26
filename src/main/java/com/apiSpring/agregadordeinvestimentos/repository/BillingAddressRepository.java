package com.apiSpring.agregadordeinvestimentos.repository;

import com.apiSpring.agregadordeinvestimentos.entity.BillingAddress;
import com.apiSpring.agregadordeinvestimentos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, UUID> { }
