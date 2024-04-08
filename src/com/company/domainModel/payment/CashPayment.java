package com.company.domainModel.payment;

public class CashPayment implements PaymentStrategy{

    private String s="No payed";

    @Override
    public String getPayState() {
        return s;
    }


    @Override
    public void pay() {
        // scrivere in OrderDao una funzione che mi faccia cambiare nel db come sarà il pagamento o se è stato fatto
        //mentre in order controller ci sara la funzione paga e in base a cosa il cliente sceglie si passa un PaymentStrategy

        s="Pay Cash";
    }
}
