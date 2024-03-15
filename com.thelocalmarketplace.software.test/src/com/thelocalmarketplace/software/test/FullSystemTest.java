package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scale.ElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodeScanner;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.tdc.coin.CoinSlot;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration;
import com.thelocalmarketplace.software.payment.Transaction;
import com.thelocalmarketplace.software.session.UserSession;
import com.thelocalmarketplace.software.state.UserSessionState;

public class FullSystemTest {

	Barcode barcode1, barcode2, barcode3;
	BarcodedItem item1, item2, item3;
	BarcodedProduct product1, product2, product3;
	
	@Before
	public void setup() {
		SelfCheckout.initialize(new SelfCheckoutConfiguration());
		
		Barcode barcode1 = new Barcode(new Numeral[] {
			Numeral.one,
			Numeral.one,
			Numeral.one,
			Numeral.one,
			Numeral.one,
			Numeral.one,
			Numeral.one,
			Numeral.one,
			Numeral.one
		});
		
		Barcode barcode2 = new Barcode(new Numeral[] {
			Numeral.two,
			Numeral.two,
			Numeral.two,
			Numeral.two,
			Numeral.two,
			Numeral.two,
			Numeral.two,
			Numeral.two,
			Numeral.two
		});
		
		Barcode barcode3 = new Barcode(new Numeral[] {
			Numeral.three,
			Numeral.three,
			Numeral.three,
			Numeral.three,
			Numeral.three,
			Numeral.three,
			Numeral.three,
			Numeral.three,
			Numeral.three
		});

		product1 = new BarcodedProduct(barcode1, "Fake Product 1", 1099, 100);
		product2 = new BarcodedProduct(barcode2, "Fake Product 2", 2049, 200);
		product3 = new BarcodedProduct(barcode3, "Fake Product 3", 3079, 300);

		item1 = new BarcodedItem(barcode1, new Mass(100.0));
		item2 = new BarcodedItem(barcode2, new Mass(200.0));
		item3 = new BarcodedItem(barcode3, new Mass(300.0));

		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode1, product1);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode2, product2);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode3, product3);
	}
	
	@Test
	public void TestSingleItemTransaction() {
		SelfCheckout sc = SelfCheckout.getInstance();
		UserSession session = sc.startNewSession();
		Transaction transaction = session.getTransaction();

		BarcodeScanner scanner = SelfCheckout.getInstance().getHardware().scanner;
		ElectronicScale baggingArea = SelfCheckout.getInstance().getHardware().baggingArea;
		CoinSlot coinSlot = SelfCheckout.getInstance().getHardware().coinSlot;

		assertEquals(session.getState(), UserSessionState.READY_FOR_ITEM);
		
		// Add coin
		scanner.scan(item1);
		assertEquals(session.getState(), UserSessionState.WAITING_FOR_BAGGING);
		System.out.println(transaction.getTotalCost());
		assertEquals(transaction.getTotalCost().compareTo(BigDecimal.valueOf(10.99)), 0);
		assertEquals(transaction.getExpectedMass().compareTo(new Mass(100.0)), 0);
		
		
	}
}
