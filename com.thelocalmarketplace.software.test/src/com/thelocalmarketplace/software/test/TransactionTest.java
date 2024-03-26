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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.tdc.CashOverloadException;
import com.tdc.banknote.Banknote;
import com.tdc.coin.Coin;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration.MachineRating;
import com.thelocalmarketplace.software.payment.CashPayment;
import com.thelocalmarketplace.software.payment.IPayment;
import com.thelocalmarketplace.software.payment.Transaction;
import com.thelocalmarketplace.software.session.UserSession;

public class TransactionTest {
	private UserSession session;
	private Transaction transaction;
	private BarcodedProduct productOne;
	private BarcodedProduct productTwo;
	private BarcodedProduct bulkyItem;
	private Numeral num;
	private Barcode bc;
	
    // simulate a payment by defining a payment stub
    private static class PaymentStub implements IPayment {
        private BigDecimal amountPaid;
        
        public PaymentStub(BigDecimal amountPaid) {
            this.amountPaid = amountPaid;
        }

        @Override
        public BigDecimal getAmountPaid() {
            return amountPaid;
        }
    }
    
	@Before
	public void setup() {
		SelfCheckout.uninitialize();
		SelfCheckout.initialize(new SelfCheckoutConfiguration());
		this.session = SelfCheckout.getInstance().startNewSession();
		this.transaction = session.getTransaction();
		this.num = Numeral.eight;
		this.bc= new Barcode(new Numeral[] {num});
		this.productOne = new BarcodedProduct(bc, "test1", 100, 1);
		this.productTwo = new BarcodedProduct(bc, "test2", 200, 2);
		this.bulkyItem = new BarcodedProduct(bc,"bulky item", 50, 100);
	}
	
	@Test
	public void testNullItemProduct() {
		assertThrows(NullPointerException.class, () -> transaction.addItem(null));
	}
	
	@Test
	public void testNullPayment() {
		assertThrows(NullPointerException.class, () -> transaction.addPayment(null));
	}
	
	@Test
	public void testPositiveWeight() {
		int comparisonResult = transaction.getExpectedMass().compareTo(Mass.ZERO);
		Assert.assertTrue(comparisonResult == 0 || comparisonResult == 1);
	}
	
	@Test
	public void testPositiveCost() {
		Assert.assertTrue(transaction.getTotalCost().compareTo(BigDecimal.ZERO) >= 0);
	}
	
	@Test
	public void testAddOneItemWeight() {
		transaction.addItem(productOne);
		Mass productOneMass = new Mass(productOne.getExpectedWeight());
		Assert.assertTrue(productOneMass.compareTo(transaction.getExpectedMass()) == 0);
	}
	
	@Test
	public void testAddMultipleItemsWeight() {
		transaction.addItem(productOne);
		Mass productOneMass = new Mass(productOne.getExpectedWeight());
		transaction.addItem(productTwo);
		Mass productTwoMass = new Mass(productTwo.getExpectedWeight());
		Mass combinedProductMass = productOneMass.sum(productTwoMass);
		
		Assert.assertTrue(combinedProductMass.compareTo(transaction.getExpectedMass()) == 0);
	}
	
	@Test
	public void testAddOneItemCost() {
		transaction.addItem(productOne);
		Assert.assertEquals(transaction.getTotalCost().compareTo(BigDecimal.valueOf(1.00)), 0);
	}
	
	@Test
	public void testAddMultipleItemsCost() {
		transaction.addItem(productOne);
		transaction.addItem(productTwo);
		Assert.assertEquals(transaction.getTotalCost().compareTo(BigDecimal.valueOf(3.00)), 0);
	}
	
	@Test
	public void testRemoveItemWeight() {
		transaction.addItem(productOne);
		transaction.addItem(productTwo);
		session.getUIHandler().removeItemSelected(productTwo);
		Mass productOneMass = new Mass(productOne.getExpectedWeight());
		Assert.assertTrue(productOneMass.compareTo(transaction.getExpectedMass()) == 0);
	}
	
	@Test
	public void testRemoveItemCost() {
		transaction.addItem(productOne);
		transaction.addItem(productTwo);
		session.getUIHandler().removeItemSelected(productTwo);
		BigDecimal productOneCost = BigDecimal.valueOf(productOne.getPrice()).divide(BigDecimal.valueOf(100));
		Assert.assertTrue(productOneCost.compareTo(transaction.getTotalCost())==0);
	}
	
	@Test
	public void testAddBulkyItemWeight() {
		session.getUIHandler().skipBaggingSelected(bulkyItem);
		Assert.assertTrue(transaction.getExpectedMass().compareTo(new Mass(0))==0);
	}
	
	@Test
	public void testAddBulkyItemCost() {
		session.getTransaction().addItem(bulkyItem);
		session.getUIHandler().skipBaggingSelected(bulkyItem);
		Assert.assertTrue(transaction.getTotalCost().compareTo(BigDecimal.valueOf(bulkyItem.getPrice()).divide(BigDecimal.valueOf(100)))==0);
	}
	
	
	@Test
	public void testValidPayment() {
	    CashPayment valid = new CashPayment(BigDecimal.TEN);
	    BigDecimal tCost = transaction.getTotalCost();
	    transaction.addPayment(valid);
	    assertEquals(valid, transaction.getPayments()[0]);
	    BigDecimal remainder = new BigDecimal(10);
	    BigDecimal newCost = transaction.getTotalCost();
	    newCost = newCost.add(remainder); // Use add() method to add BigDecimal values
	    assertEquals(tCost, newCost); // Use assertEquals for comparison
	}

	
	@Test
    public void testAddItem() {
        transaction.addItem(productOne);
        assertEquals(1, transaction.getProducts().length);
    }

	@Test
    public void testZeroTotalCostChange() throws Exception {
        transaction.calculateChange(); // Calculate change
        // No exception should be thrown because there is no change to dispense
    }
	
    @Test
    public void testZeroChange() {
        transaction.addItem(productOne); // Adding a product with price 1.00
        transaction.addPayment(new PaymentStub(BigDecimal.valueOf(1.00))); // Simulating payment of 1.00
        
        try {
            transaction.calculateChange();
            BigDecimal expectedChange = BigDecimal.valueOf(0.0);
            BigDecimal actualChange = transaction.getTotalCost();
            assertEquals(expectedChange, actualChange);
        } catch (Exception e) {
        }
    }
	
    @Test
    public void testZeroChange2() {
        transaction.addItem(productOne); // Adding a product with price 1.00
        transaction.addItem(productOne); // Adding a product with price 1.00
        transaction.addPayment(new PaymentStub(BigDecimal.valueOf(1.00))); // Simulating payment of 1.00
        transaction.addPayment(new PaymentStub(BigDecimal.valueOf(1.00))); // Simulating payment of 1.00
        
        try {
            transaction.calculateChange();
            BigDecimal expectedChange = BigDecimal.valueOf(0.0);
            BigDecimal actualChange = transaction.getTotalCost();
            assertEquals(expectedChange, actualChange);
        } catch (Exception e) {
        }
    }
	
    @Test
    public void testOneChange() throws Exception {
        transaction.addItem(productOne); // Adding a product with price 1.00
        transaction.addPayment(new PaymentStub(BigDecimal.valueOf(2.00))); // Simulating payment of 2.00
        
        try {
			SelfCheckout.getInstance().getHardware().coinDispensers.get(BigDecimal.ONE).load(new Coin(Currency.getInstance(Locale.CANADA), BigDecimal.ONE));
		} catch (CashOverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        transaction.calculateChange();
        BigDecimal expectedChange = BigDecimal.valueOf(-1.0);
        BigDecimal actualChange = transaction.getTotalCost();
        assertEquals(expectedChange, actualChange);
        
        // Check the coins that were dispensed match the coins that should have been dispensed.
        List<Coin> collectedCoins = SelfCheckout.getInstance().getHardware().coinTray.collectCoins();
        assertEquals(1, collectedCoins.size());
        assertEquals(0, collectedCoins.get(0).getValue().compareTo(BigDecimal.valueOf(1.0)));
    }
	
    @Test
    public void testTwoChange() throws Exception {
        transaction.addItem(productOne); // Adding a product with price 1.00
        transaction.addPayment(new PaymentStub(BigDecimal.valueOf(3.00))); // Simulating payment of 3.00
        
        try {
			SelfCheckout.getInstance().getHardware().coinDispensers.get(BigDecimal.ONE).load(
				new Coin(Currency.getInstance(Locale.CANADA), BigDecimal.ONE),
				new Coin(Currency.getInstance(Locale.CANADA), BigDecimal.ONE)
			);
		} catch (CashOverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        transaction.calculateChange();
        BigDecimal expectedChange = BigDecimal.valueOf(-2.0);
        BigDecimal actualChange = transaction.getTotalCost();
        assertEquals(expectedChange, actualChange);
        List<Coin> collectedCoins = SelfCheckout.getInstance().getHardware().coinTray.collectCoins();
        assertEquals(2, collectedCoins.size());
        assertEquals(0, collectedCoins.get(0).getValue().compareTo(BigDecimal.valueOf(1.0)));
        assertEquals(0, collectedCoins.get(1).getValue().compareTo(BigDecimal.valueOf(1.0)));
    }

	
    @Test
    public void testOptimalChange() throws Exception {
    	SelfCheckout.uninitialize();
    	SelfCheckout.initialize(new SelfCheckoutConfiguration(
    		MachineRating.BRONZE, 
    		Currency.getInstance(Locale.CANADA), 
    		100, 
    		1000, 
    		25, 
    		new BigDecimal[] {BigDecimal.valueOf(2), BigDecimal.valueOf(1)}, 
    		new BigDecimal[] {BigDecimal.valueOf(10)},
    		100, 
    		100
    	));
    	session = SelfCheckout.getInstance().startNewSession();
        transaction = session.getTransaction();
        transaction.addItem(productOne); // Adding a product with price 1.00
        transaction.addPayment(new PaymentStub(BigDecimal.valueOf(4.00))); // Simulating payment of 4.00
        
        try {
			SelfCheckout.getInstance().getHardware().coinDispensers.get(BigDecimal.ONE).load(
				new Coin(Currency.getInstance(Locale.CANADA), BigDecimal.ONE),
				new Coin(Currency.getInstance(Locale.CANADA), BigDecimal.ONE)
			);
			SelfCheckout.getInstance().getHardware().coinDispensers.get(BigDecimal.valueOf(2)).load(
				new Coin(Currency.getInstance(Locale.CANADA), BigDecimal.valueOf(2)),
				new Coin(Currency.getInstance(Locale.CANADA), BigDecimal.valueOf(2))
			);
		} catch (CashOverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        transaction.calculateChange();
        BigDecimal expectedChange = BigDecimal.valueOf(-3.0);
        BigDecimal actualChange = transaction.getTotalCost();
        assertEquals(expectedChange, actualChange);
        List<Coin> collectedCoins = SelfCheckout.getInstance().getHardware().coinTray.collectCoins();
        assertEquals(2, collectedCoins.size());
        int loonieCount = 0, toonieCount = 0;
        for(Coin coin : collectedCoins) {
        	if(coin.getValue().compareTo(BigDecimal.ONE) == 0) loonieCount++;
        	if(coin.getValue().compareTo(BigDecimal.valueOf(2)) == 0) toonieCount++;
        }
        assertEquals(1, loonieCount);
        assertEquals(1, toonieCount);
    }

	
    @Test
    public void testChangeBill() throws Exception {
        transaction.addItem(productOne); // Adding a product with price 1.00
        transaction.addPayment(new PaymentStub(BigDecimal.valueOf(12.00))); // Simulating payment of 12.00
        
        try {
			SelfCheckout.getInstance().getHardware().coinDispensers.get(BigDecimal.ONE).load(
				new Coin(Currency.getInstance(Locale.CANADA), BigDecimal.ONE),
				new Coin(Currency.getInstance(Locale.CANADA), BigDecimal.ONE)
			);
			SelfCheckout.getInstance().getHardware().banknoteDispensers.get(BigDecimal.valueOf(10)).load(
				new Banknote(Currency.getInstance(Locale.CANADA), BigDecimal.valueOf(10)),
				new Banknote(Currency.getInstance(Locale.CANADA), BigDecimal.valueOf(10))
			);
		} catch (CashOverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        transaction.calculateChange();
        BigDecimal expectedChange = BigDecimal.valueOf(-11.0);
        BigDecimal actualChange = transaction.getTotalCost();
        assertEquals(expectedChange, actualChange);
        List<Coin> collectedCoins = SelfCheckout.getInstance().getHardware().coinTray.collectCoins();
        assertEquals(1, collectedCoins.size());
        assertEquals(0, collectedCoins.get(0).getValue().compareTo(BigDecimal.valueOf(1)));
        
        List<Banknote> collectedBanknotes = SelfCheckout.getInstance().getHardware().banknoteOutput.removeDanglingBanknotes();
        assertEquals(1, collectedBanknotes.size());
        assertEquals(0, collectedBanknotes.get(0).getDenomination().compareTo(BigDecimal.valueOf(10)));
    }
        
}

	
