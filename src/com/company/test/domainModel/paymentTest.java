package com.company.test.domainModel;

import com.company.domainModel.payment.CashPayment;
import com.company.domainModel.payment.CreditCardPayment;
import com.company.domainModel.payment.PaymentStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class paymentTest {
    private PaymentStrategy paymentStrategy;

    @BeforeEach
    void setUp()
    {
        paymentStrategy=new CashPayment();
    }

    //test che controlla il tipo di pagamento scelto
    @Test
    void testCashPayment(){
        Assertions.assertEquals("No payed",paymentStrategy.getPayState());
    }


    //test che permette di fare un pagamento tramite carta
    @Test
    void changePayment(){
        paymentStrategy=new CreditCardPayment("32434524","3434","34");
        paymentStrategy.pay(23);
        Assertions.assertEquals("Payed Card",paymentStrategy.getPayState());
    }
}
