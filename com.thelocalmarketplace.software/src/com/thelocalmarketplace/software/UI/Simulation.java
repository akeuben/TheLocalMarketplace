package com.thelocalmarketplace.software.UI;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration.MachineRating;
import com.thelocalmarketplace.software.UI.hardwaresim.UIHardwareSimulation;

public class Simulation {
	public static void main(String[] args) {
		SelfCheckout.initialize(new SelfCheckoutConfiguration(
			MachineRating.BRONZE, 
			Currency.getInstance(Locale.CANADA), 
			100, 
			1000, 
			25, 
			new BigDecimal[] {
					BigDecimal.valueOf(0.05),
					BigDecimal.valueOf(0.10),
					BigDecimal.valueOf(0.25),
					BigDecimal.valueOf(1.00),
					BigDecimal.valueOf(2.00)
			}, 
			new BigDecimal[] {
					BigDecimal.valueOf(5),
					BigDecimal.valueOf(10),
					BigDecimal.valueOf(20),
					BigDecimal.valueOf(50),
					BigDecimal.valueOf(100)
			}, 
			100, 
			100
		));
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// this should never happen!
			e.printStackTrace();
		}
		UIHardwareSimulation.startHardwareSimulationUI();
	}
}
