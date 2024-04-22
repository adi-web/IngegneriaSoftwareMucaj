package com.company.domainModel.payment;

public class CreditCardPayment implements PaymentStrategy {

    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private String s="No payed";

    public CreditCardPayment(String cardNumber, String expirationDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }


    @Override
    public String getPayState() {
        return s;
    }

    @Override
    public void pay(float amount) {

        System.out.println("Simulating payment with Credit Card.");
        System.out.println("Card Number: " + cardNumber);
        System.out.println("Expiration Date: " + expirationDate);
        System.out.println("CVV: " + cvv);
        s="Payed Card";


    }


}
