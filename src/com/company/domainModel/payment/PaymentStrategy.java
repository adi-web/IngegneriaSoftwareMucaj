package com.company.domainModel.payment;

public interface PaymentStrategy {
    String getPayState();
    void pay();
}
