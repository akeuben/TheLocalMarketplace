package com.thelocalmarketplace.software.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.banknote.Banknote;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.software.PredictIssue;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration;
import com.thelocalmarketplace.software.Software;
import com.thelocalmarketplace.software.test.stubs.TestableAttendantStation;

import powerutility.PowerGrid;

public class PredictIssueTest {

    private AbstractSelfCheckoutStation bronzeStation;
    private AbstractSelfCheckoutStation goldStation;

    @Before
    public void setup() {
        PowerGrid.engageUninterruptiblePowerSource();
        Software.uninitialize();

        SelfCheckoutConfiguration bronzeConfig = new SelfCheckoutConfiguration(
                SelfCheckoutStationBronze.class,
                TestableAttendantStation.class,
                Currency.getInstance(Locale.CANADA),
                50,
                100,
                25,
                new BigDecimal[]{BigDecimal.ONE},
                new BigDecimal[]{BigDecimal.valueOf(5), BigDecimal.valueOf(10)},
                50,
                50,
                BigDecimal.valueOf(1.99)
        );
        Software.initialize(bronzeConfig, 1);
        bronzeStation = Software.getInstance().getHardware(0);

        Software.uninitialize();

        SelfCheckoutConfiguration goldConfig = new SelfCheckoutConfiguration(
                SelfCheckoutStationGold.class,
                TestableAttendantStation.class,
                Currency.getInstance(Locale.CANADA),
                50,
                100,
                25,
                new BigDecimal[]{BigDecimal.ONE},
                new BigDecimal[]{BigDecimal.valueOf(5), BigDecimal.valueOf(10)},
                50,
                50,
                BigDecimal.valueOf(1.99)
        );
        Software.initialize(goldConfig, 1);
        goldStation = Software.getInstance().getHardware(0);
    }

    @Test
    public void testPredictCoinsFull() {
        assertFalse(PredictIssue.predictCoinsFull(bronzeStation));
        assertFalse(PredictIssue.predictCoinsFull(goldStation));

        // Fill the coin storage to capacity
        while (bronzeStation.getCoinStorage().hasSpace()) {
            bronzeStation.getCoinStorage().receive(new com.tdc.coin.Coin(Currency.getInstance(Locale.CANADA), BigDecimal.ONE));
        }
        while (goldStation.getCoinStorage().hasSpace()) {
            goldStation.getCoinStorage().receive(new com.tdc.coin.Coin(Currency.getInstance(Locale.CANADA), BigDecimal.ONE));
        }

        assertTrue(PredictIssue.predictCoinsFull(bronzeStation));
        assertTrue(PredictIssue.predictCoinsFull(goldStation));
    }

    @Test
    public void testPredictBanknotesFull() throws DisabledException, CashOverloadException {
        assertFalse(PredictIssue.predictBanknotesFull(bronzeStation));
        assertFalse(PredictIssue.predictBanknotesFull(goldStation));

        // Fill the banknote storage to capacity
        while (bronzeStation.getBanknoteStorage().hasSpace()) {
            bronzeStation.getBanknoteStorage().receive(new Banknote(Currency.getInstance(Locale.CANADA), BigDecimal.valueOf(5)));
        }
        while (goldStation.getBanknoteStorage().hasSpace()) {
            goldStation.getBanknoteStorage().receive(new Banknote(Currency.getInstance(Locale.CANADA), BigDecimal.valueOf(5)));
        }

        assertTrue(PredictIssue.predictBanknotesFull(bronzeStation));
        assertTrue(PredictIssue.predictBanknotesFull(goldStation));
    }

    @Test
    public void testPredictLowCoins() throws CashOverloadException, NoCashAvailableException, DisabledException {
        assertFalse(PredictIssue.predictLowCoins(bronzeStation));
        assertFalse(PredictIssue.predictLowCoins(goldStation));

        // Remove coins from the dispensers to trigger low coin prediction
        for (BigDecimal denomination : bronzeStation.getCoinDispensers().keySet()) {
            while (bronzeStation.getCoinDispensers().get(denomination).size() > bronzeStation.getCoinDispensers().get(denomination).getCapacity() / 4) {
                bronzeStation.getCoinDispensers().get(denomination).emit();
            }
        }
        for (BigDecimal denomination : goldStation.getCoinDispensers().keySet()) {
            while (goldStation.getCoinDispensers().get(denomination).size() > goldStation.getCoinDispensers().get(denomination).getCapacity() / 4) {
                goldStation.getCoinDispensers().get(denomination).emit();
            }
        }

        assertTrue(PredictIssue.predictLowCoins(bronzeStation));
        assertTrue(PredictIssue.predictLowCoins(goldStation));
    }

    @Test
    public void testPredictLowBankNotes() throws NoCashAvailableException, DisabledException, CashOverloadException {
        assertFalse(PredictIssue.predictLowBankNotes(bronzeStation));
        assertFalse(PredictIssue.predictLowBankNotes(goldStation));

        // Remove banknotes from the dispensers to trigger low banknote prediction
        for (BigDecimal denomination : bronzeStation.getBanknoteDispensers().keySet()) {
            while (bronzeStation.getBanknoteDispensers().get(denomination).size() > bronzeStation.getBanknoteDispensers().get(denomination).getCapacity() / 4) {
                bronzeStation.getBanknoteDispensers().get(denomination).emit();
            }
        }
        for (BigDecimal denomination : goldStation.getBanknoteDispensers().keySet()) {
            while (goldStation.getBanknoteDispensers().get(denomination).size() > goldStation.getBanknoteDispensers().get(denomination).getCapacity() / 4) {
                goldStation.getBanknoteDispensers().get(denomination).emit();
            }
        }

        assertTrue(PredictIssue.predictLowBankNotes(bronzeStation));
        assertTrue(PredictIssue.predictLowBankNotes(goldStation));
    }

    @Test
    public void testPredictAllIssues() throws NoCashAvailableException, DisabledException, CashOverloadException {
        assertFalse(PredictIssue.predictAllIssues(bronzeStation));
        assertFalse(PredictIssue.predictAllIssues(goldStation));

        // Trigger all issues
        while (bronzeStation.getCoinStorage().hasSpace()) {
            bronzeStation.getCoinStorage().receive(new com.tdc.coin.Coin(Currency.getInstance(Locale.CANADA), BigDecimal.ONE));
        }
        while (bronzeStation.getBanknoteStorage().hasSpace()) {
            bronzeStation.getBanknoteStorage().receive(new Banknote(Currency.getInstance(Locale.CANADA), BigDecimal.valueOf(5)));
        }
        for (BigDecimal denomination : bronzeStation.getCoinDispensers().keySet()) {
            while (bronzeStation.getCoinDispensers().get(denomination).size() > bronzeStation.getCoinDispensers().get(denomination).getCapacity() / 4) {
                bronzeStation.getCoinDispensers().get(denomination).emit();
            }
        }
        for (BigDecimal denomination : bronzeStation.getBanknoteDispensers().keySet()) {
            while (bronzeStation.getBanknoteDispensers().get(denomination).size() > bronzeStation.getBanknoteDispensers().get(denomination).getCapacity() / 4) {
                bronzeStation.getBanknoteDispensers().get(denomination).emit();
            }
        }

        while (goldStation.getCoinStorage().hasSpace()) {
            goldStation.getCoinStorage().receive(new com.tdc.coin.Coin(Currency.getInstance(Locale.CANADA), BigDecimal.ONE));
        }
        while (goldStation.getBanknoteStorage().hasSpace()) {
            goldStation.getBanknoteStorage().receive(new Banknote(Currency.getInstance(Locale.CANADA), BigDecimal.valueOf(5)));
        }
        for (BigDecimal denomination : goldStation.getCoinDispensers().keySet()) {
            while (goldStation.getCoinDispensers().get(denomination).size() > goldStation.getCoinDispensers().get(denomination).getCapacity() / 4) {
                goldStation.getCoinDispensers().get(denomination).emit();
            }
        }
        for (BigDecimal denomination : goldStation.getBanknoteDispensers().keySet()) {
            while (goldStation.getBanknoteDispensers().get(denomination).size() > goldStation.getBanknoteDispensers().get(denomination).getCapacity() / 4) {
                goldStation.getBanknoteDispensers().get(denomination).emit();
            }
        }

        assertTrue(PredictIssue.predictAllIssues(bronzeStation));
        assertTrue(PredictIssue.predictAllIssues(goldStation));
    }
}