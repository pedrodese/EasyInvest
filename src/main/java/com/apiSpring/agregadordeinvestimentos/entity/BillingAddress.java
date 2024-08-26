package com.apiSpring.agregadordeinvestimentos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "tb_billingaddress")
public class BillingAddress {

    @Id
    private UUID id;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;

    public BillingAddress() {
    }

    public BillingAddress(UUID id, String address, Integer number) {
        this.id = id;
        this.street = address;
        this.number = number;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String address) {
        this.street = address;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
