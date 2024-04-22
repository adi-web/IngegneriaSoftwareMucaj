package com.company.domainModel.payment;

public class PaymentContext {

    private PaymentStrategy paymentStrategy;

    public PaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void processPayment(float amount) {
        paymentStrategy.pay(amount);
    }
}
