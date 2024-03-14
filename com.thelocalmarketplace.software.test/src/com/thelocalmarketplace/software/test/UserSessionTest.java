package com.thelocalmarketplace.software.test;

import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.software.payment.Transaction;
import com.thelocalmarketplace.software.session.UserSession;
import com.thelocalmarketplace.software.state.UserSessionState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserSessionTest {

    private UserSession session = new UserSession();
    @Before
    public void setup() {


    }
    @Test
    public void testChangingState() {
        session.setState(UserSessionState.READY_FOR_PAYMENT);
        UserSessionState newstate = session.getState();
        assertEquals(newstate, UserSessionState.READY_FOR_PAYMENT);
    }
}
