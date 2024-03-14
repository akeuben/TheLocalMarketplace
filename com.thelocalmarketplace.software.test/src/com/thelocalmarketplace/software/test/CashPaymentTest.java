package com.thelocalmarketplace.software.test;

import com.thelocalmarketplace.software.payment.CashPayment;
import com.thelocalmarketplace.software.state.UserSessionState;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class CashPaymentTest {
    @Before
    public void setup() {


    }
    @Test
    public void testNewPayment() {
        CashPayment payment = new CashPayment(BigDecimal.ONE);
        BigDecimal amount = BigDecimal.ONE;
        assertEquals(amount, payment.getAmountPaid());
    }
}
