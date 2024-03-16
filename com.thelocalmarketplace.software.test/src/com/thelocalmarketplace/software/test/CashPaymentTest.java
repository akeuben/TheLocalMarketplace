package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.software.payment.CashPayment;

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
