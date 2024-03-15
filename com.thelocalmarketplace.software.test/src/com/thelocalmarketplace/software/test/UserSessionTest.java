package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.software.session.UserSession;
import com.thelocalmarketplace.software.state.UserSessionState;

public class UserSessionTest {

    private UserSession session;
    @Before
    public void setup() {
    	session = new UserSession();
    }
    @Test
    public void testInitialState() {
        assertEquals(session.getState(), UserSessionState.READY_FOR_ITEM);
    }
    @Test
    public void testChangingState() {
        session.setState(UserSessionState.READY_FOR_PAYMENT);
        UserSessionState newstate = session.getState();
        assertEquals(newstate, UserSessionState.READY_FOR_PAYMENT);
    }

    @Test
    public void testChangingState2() {
        session.setState(UserSessionState.WAITING_FOR_BAGGING);
        UserSessionState newstate = session.getState();
        assertEquals(newstate, UserSessionState.WAITING_FOR_BAGGING);
    }

    @Test
    public void testChangingState3() {
        session.setState(UserSessionState.WAITING_FOR_BAGGING);
        session.setState(UserSessionState.READY_FOR_ITEM);
        session.setState(UserSessionState.WAITING_FOR_BAGGING);
        session.setState(UserSessionState.READY_FOR_PAYMENT);
        session.setState(UserSessionState.WAITING_FOR_BAGGING);
        UserSessionState newstate = session.getState();
        assertEquals(newstate, UserSessionState.WAITING_FOR_BAGGING);
    }
}
