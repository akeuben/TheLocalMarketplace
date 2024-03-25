package com.thelocalmarketplace.software.test;

/**
 * SENG 300 Project - Group 1:
 * 
 * Avery Keuben - 30170731
 * Moiz Siddiqui - 30150291
 * Ammaar Melethil - 30141956
 * Joey Fisher - 30105628
 * Ethan Pangilinan - 30179143
 * Joshua Kraft - 30171525
 * Nathan Vaters - 30121908
 * Max Butcher - 30149202
 * Neeraj Ghansela - 30157473
 * Ansel Sulejmani - 30178521
 * Suleman Basit - 30132816
 * Jacob Boyden - 30193220
 * Cheshta Sharma - 30064538
 * Callum Bates - 30188601
 * Armughan Mustafa - 30154601
 * Connor Ell - 30073291
 * Saif Farag - 30195046
 * Ivan Agalakov - 30172107
 * Samuel Turner - 10064857
 * Stephanie Sevilla - 30176781
 * Winston Wang - 30185321
 */

import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.CardReaderGold;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.SelfCheckout;

import com.thelocalmarketplace.software.SelfCheckoutConfiguration;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration.MachineRating;
import com.thelocalmarketplace.software.payment.BankDataBase;
import com.thelocalmarketplace.software.payment.CardPayment;
import com.thelocalmarketplace.software.payment.Transaction;
import com.thelocalmarketplace.software.session.UserSession;
import com.thelocalmarketplace.software.state.UserSessionState;

import powerutility.NoPowerException;
import powerutility.PowerGrid;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;

import org.junit.Before; 


/**
 * Following class will be for testing transactions specifically related to debit and credit cards
 */
public class CardPaymentTest {

	private Barcode barcode; 
	private BarcodedProduct product; 
	private Card debit; 
	private Card credit; 
	private Card fake; 
	private CardIssuer bank; 
	
	
	@Before
	public void setup() {
		SelfCheckout.uninitialize(); 
		BankDataBase.uninitialize();
		// as of right now this self checkout is bronze, may set to gold to remove some probability
		SelfCheckout.initialize(new SelfCheckoutConfiguration(MachineRating.GOLD, Currency.getInstance(Locale.CANADA), 100, 1000, 25, new BigDecimal[] {BigDecimal.ONE}, new BigDecimal[] {BigDecimal.valueOf(10)}, 100, 100));
		barcode = new Barcode(new Numeral []{Numeral.one, Numeral.two, Numeral.three});
		
		product = new BarcodedProduct(barcode, "item", 1249, 100);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, product); 
		
		bank = new CardIssuer("bank", 10);
		debit = new Card("visa", "1234", "name", "503");
		Calendar expiry = Calendar.getInstance();
		expiry.set(2026, 1, 1);
		bank.addCardData(debit.number, debit.cardholder, expiry, debit.cvv, 100);
		
		credit = new Card("visa", "4321", "name", "405");
		bank.addCardData(credit.number, credit.cardholder, expiry, credit.cvv, 200);
		 
		fake = new Card("card", "1111", "notName", "101");
		
		HashMap<String, CardIssuer> map = new HashMap<>(); 
		map.put("visa", bank); 
		BankDataBase.initialize(map);
			
		
	}
	
	
	@Test 
	public void testDebitSwipePayment() {
		// set up session and self checkout
		SelfCheckout sc = SelfCheckout.getInstance(); 
		UserSession session = sc.startNewSession(); 
		Transaction transaction = session.getTransaction(); 
		
		IBarcodeScanner scanner = sc.getHardware().mainScanner; 
		IElectronicScale baggingArea = sc.getHardware().baggingArea; 
		// scan the product then add it to the baggingArea
		scanner.scan(new BarcodedItem(barcode, new Mass(100)));
		baggingArea.addAnItem(new BarcodedItem(barcode, new Mass(100)));

		System.out.println("Transaction before: " + transaction.getTotalCost().doubleValue());
		session.setState(UserSessionState.READY_FOR_PAYMENT); 
		try {
			sc.getHardware().cardReader.swipe(debit);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		System.out.println("Transaction after: " + transaction.getTotalCost().doubleValue());
		assertNull(sc.getCurrentSession());
		
		
	}
	
	
	@Test
    public void testSwipePayment() {
        SelfCheckout sc = SelfCheckout.getInstance();
        UserSession session = sc.startNewSession();
        Transaction transaction = session.getTransaction();
        IBarcodeScanner scanner = sc.getHardware().mainScanner; 
		IElectronicScale baggingArea = sc.getHardware().baggingArea; 
		// scan the product then add it to the baggingArea
		scanner.scan(new BarcodedItem(barcode, new Mass(100)));
		baggingArea.addAnItem(new BarcodedItem(barcode, new Mass(100)));

        // Prepare card data (simulate swiping the card)
        CardData cardData = null;
		try {
			cardData = debit.swipe();
		} catch (IOException e) {
			e.printStackTrace();
		} 

        // Initialize CardPayment and attempt payment
        CardPayment payment = new CardPayment();
        boolean result = payment.swipePayment(cardData);

        // Assert payment success
        assertTrue("Payment should succeed", result);
        // Verify the amount paid is equal to the transaction amount
        assertEquals("Amount paid should match transaction total", transaction.getTotalCost(), payment.getAmountPaid());
    }
	
	
	@Test
    public void testSwipePaymentOnFake() {
        SelfCheckout sc = SelfCheckout.getInstance();
        UserSession session = sc.startNewSession();
        Transaction transaction = session.getTransaction();
        IBarcodeScanner scanner = sc.getHardware().mainScanner; 
		IElectronicScale baggingArea = sc.getHardware().baggingArea; 
		// scan the product then add it to the baggingArea
		scanner.scan(new BarcodedItem(barcode, new Mass(100)));
		baggingArea.addAnItem(new BarcodedItem(barcode, new Mass(100)));

        // Prepare card data (simulate swiping the card)
        CardData cardData = null;
		try {
			cardData = fake.swipe();
		} catch (IOException e) {
			e.printStackTrace();
		} 

        // Initialize CardPayment and attempt payment
        CardPayment payment = new CardPayment();
        boolean result = payment.swipePayment(cardData);

        // Assert payment fail
        assertFalse("Payment should fail", result);
        
    }
	
	
	/**
	 * Test to see ensure that database will not run twice
	 */
	
	@Test (expected = RuntimeException.class)
	public void testDoubleInitialize() {
		HashMap<String, CardIssuer> map = new HashMap<>(); 
		map.put("visa", bank); 
		BankDataBase.initialize(map);
	}
	
	/**
	 * Tests to see if payment will fail if cardReader attempts to swipe a null card
	 */
	
	@Test
	public void swipeNull() {
		SelfCheckout sc = SelfCheckout.getInstance(); 
		assertThrows(NullPointerException.class, () -> sc.getHardware().cardReader.swipe(null));
	}
	
	/**
	 * Tests to see if payment will fail if cardData is null
	 */
	
	@Test (expected = NullPointerException.class)
	public void cardDataNull() {
		 SelfCheckout sc = SelfCheckout.getInstance();
	        UserSession session = sc.startNewSession();
	        Transaction transaction = session.getTransaction();
	        IBarcodeScanner scanner = sc.getHardware().mainScanner; 
			IElectronicScale baggingArea = sc.getHardware().baggingArea; 
			// scan the product then add it to the baggingArea
			scanner.scan(new BarcodedItem(barcode, new Mass(100)));
			baggingArea.addAnItem(new BarcodedItem(barcode, new Mass(100)));

	        // Prepare card data (simulate swiping the card)
	        CardData cardData = null;
	        CardPayment payment = new CardPayment();
	        payment.swipePayment(cardData);
	}
	
	/**
	 * Tests for noPowerException
	 */
	
	@Test (expected = NoPowerException.class)
	public void noPowerException() {
		 SelfCheckout sc = SelfCheckout.getInstance();
	        UserSession session = sc.startNewSession();
	        sc.getHardware().cardReader.turnOff();
	        Transaction transaction = session.getTransaction();
	        IBarcodeScanner scanner = sc.getHardware().mainScanner; 
			IElectronicScale baggingArea = sc.getHardware().baggingArea; 
			// scan the product then add it to the baggingArea
			scanner.scan(new BarcodedItem(barcode, new Mass(100)));
			baggingArea.addAnItem(new BarcodedItem(barcode, new Mass(100)));

			try {
				sc.getHardware().cardReader.swipe(debit);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	}
	
	
	
	
	
}
