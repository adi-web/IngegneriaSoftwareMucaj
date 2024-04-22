package com.company.domainModel.payment;

public class BonusPayment implements PaymentStrategy{
    private String cardNumber;
    private String s="No payed";

    public BonusPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String getPayState() {
        return s;
    }


    @Override
    public void pay(float amount) {

        System.out.println("Simulating payment with Credit Card.");
        System.out.println("Card Number: " + cardNumber);
        s="Payed Bonus";

    }
}
