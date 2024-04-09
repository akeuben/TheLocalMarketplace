package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.tdc.CashOverloadException;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.BanknoteStorageUnit;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinStorageUnit;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration;
import com.thelocalmarketplace.software.Software;
import com.thelocalmarketplace.software.session.PredictIssue;
import com.thelocalmarketplace.software.session.PredictIssueHandler;
import com.thelocalmarketplace.software.session.UserSession;
import com.thelocalmarketplace.software.test.stubs.TestableAttendantStation;
import com.thelocalmarketplace.software.test.stubs.TestableSelfCheckoutStationGold;

public class PredictIssueTest {
    
    private UserSession session;
    private PredictIssue predictIssue;
    private TestPredictIssueHandler handler;
    
    @Before
    public void setup() {
        Software.uninitialize();
        Software.initialize(new SelfCheckoutConfiguration(
            TestableSelfCheckoutStationGold.class,
            TestableAttendantStation.class,
            Currency.getInstance(Locale.CANADA),
            100,
            1000,
            25,
            new BigDecimal[] {BigDecimal.ONE},
            new BigDecimal[] {BigDecimal.valueOf(10)},
            100,
            100
        ), 1);
        session = Software.getInstance().startNewSession(0);
        predictIssue = new PredictIssue(session);
        handler = new TestPredictIssueHandler();
        predictIssue.register(handler);
    }
    
    @Test
    public void testPredictAllIssuesWithNoIssues() {
        predictIssue.predictAllIssues();
        
        assertFalse(predictIssue.hasIssue);
        assertFalse(predictIssue.fullCoins);
        assertFalse(predictIssue.fullBanknotes);
        assertFalse(predictIssue.lowCoins);
        assertFalse(predictIssue.lowBanknotes);
        
        assertTrue(handler.noIssuesCalled);
        assertFalse(handler.coinsFullCalled);
        assertFalse(handler.banknotesFullCalled);
        assertFalse(handler.coinsLowCalled);
        assertFalse(handler.banknotesLowCalled);
    }
    
    @Test
    public void testPredictAllIssuesWithCoinsFull() {
        AbstractSelfCheckoutStation station = session.getHardware();
        CoinStorageUnit coinStorage = station.getCoinStorage();
        
        // Load the coin storage unit to its maximum capacity
        int maxCapacity = coinStorage.getCapacity();
        try {
            for (int i = 0; i < maxCapacity; i++) {
                coinStorage.load(new Coin(Currency.getInstance(Locale.CANADA), BigDecimal.ONE));
            }
        } catch (CashOverloadException e) {
            System.out.println("Coin storage unit overloaded: " + e.getMessage());
        }
        
        predictIssue.predictAllIssues();
        
        assertTrue(predictIssue.hasIssue);
        assertTrue(predictIssue.fullCoins);
        assertFalse(predictIssue.fullBanknotes);
        assertFalse(predictIssue.lowCoins);
        assertFalse(predictIssue.lowBanknotes);
        
        assertTrue(handler.coinsFullCalled);
        assertFalse(handler.banknotesFullCalled);
        assertFalse(handler.coinsLowCalled);
        assertFalse(handler.banknotesLowCalled);
    }
    
    @Test
    public void testPredictAllIssuesWithBanknotesFull() {
        AbstractSelfCheckoutStation station = session.getHardware();
        BanknoteStorageUnit banknoteStorage = station.getBanknoteStorage();
        
        // Load the banknote storage unit to its maximum capacity
        int maxCapacity = banknoteStorage.getCapacity();
        try {
            for (int i = 0; i < maxCapacity; i++) {
                banknoteStorage.load(new Banknote(Currency.getInstance(Locale.CANADA), BigDecimal.TEN));
            }
        } catch (CashOverloadException e) {
            System.out.println("Banknote storage unit overloaded: " + e.getMessage());
        }
        
        predictIssue.predictAllIssues();
        
        assertTrue(predictIssue.hasIssue);
        assertFalse(predictIssue.fullCoins);
        assertTrue(predictIssue.fullBanknotes);
        assertFalse(predictIssue.lowCoins);
        assertFalse(predictIssue.lowBanknotes);
        
        assertFalse(handler.coinsFullCalled);
        assertTrue(handler.banknotesFullCalled);
        assertFalse(handler.coinsLowCalled);
        assertFalse(handler.banknotesLowCalled);
    }
    
    @Test
    public void testPredictAllIssuesWithLowCoins() {
        AbstractSelfCheckoutStation station = session.getHardware();
        Map<BigDecimal, ICoinDispenser> coinDispensers = station.getCoinDispensers();
        for (ICoinDispenser dispenser : coinDispensers.values()) {
            dispenser.unload();
        }
        
        predictIssue.predictAllIssues();
        
        assertTrue(predictIssue.hasIssue);
        assertFalse(predictIssue.fullCoins);
        assertFalse(predictIssue.fullBanknotes);
        assertTrue(predictIssue.lowCoins);
        assertFalse(predictIssue.lowBanknotes);
        
        assertFalse(handler.coinsFullCalled);
        assertFalse(handler.banknotesFullCalled);
        assertTrue(handler.coinsLowCalled);
        assertFalse(handler.banknotesLowCalled);
    }
    
    @Test
    public void testPredictAllIssuesWithLowBanknotes() {
        AbstractSelfCheckoutStation station = session.getHardware();
        Map<BigDecimal, IBanknoteDispenser> banknoteDispensers = station.getBanknoteDispensers();
        for (IBanknoteDispenser dispenser : banknoteDispensers.values()) {
            dispenser.unload();
        }
        
        predictIssue.predictAllIssues();
        
        assertTrue(predictIssue.hasIssue);
        assertFalse(predictIssue.fullCoins);
        assertFalse(predictIssue.fullBanknotes);
        assertFalse(predictIssue.lowCoins);
        assertTrue(predictIssue.lowBanknotes);
        
        assertFalse(handler.coinsFullCalled);
        assertFalse(handler.banknotesFullCalled);
        assertFalse(handler.coinsLowCalled);
        assertTrue(handler.banknotesLowCalled);
    }
    
    @Test
    public void testDeregister() {
        assertTrue(predictIssue.deregister(handler));
        assertFalse(predictIssue.deregister(handler));
    }
    
    @Test
    public void testDeregisterAll() {
        predictIssue.deregisterAll();
        assertTrue(predictIssue.listeners.isEmpty());
    }
    
    private class TestPredictIssueHandler implements PredictIssueHandler {
        boolean coinsFullCalled = false;
        boolean banknotesFullCalled = false;
        boolean coinsLowCalled = false;
        boolean banknotesLowCalled = false;
        boolean noIssuesCalled = false;
        
        @Override
        public void notifyPredictCoinsFull(UserSession session) {
            coinsFullCalled = true;
        }
        
        @Override
        public void notifyPredictBanknotesFull(UserSession session) {
            banknotesFullCalled = true;
        }
        
        @Override
        public void notifyPredictLowCoins(UserSession session) {
            coinsLowCalled = true;
        }
        
        @Override
        public void notifyPredictLowBanknotes(UserSession session) {
            banknotesLowCalled = true;
        }
        
        @Override
        public void notifyNoIssues(UserSession session) {
            noIssuesCalled = true;
        }
    }
}
