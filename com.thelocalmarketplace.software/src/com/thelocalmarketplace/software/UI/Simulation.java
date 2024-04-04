package com.thelocalmarketplace.software.UI;

import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import javax.swing.JFrame;

import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration.MachineRating;
import com.thelocalmarketplace.software.UI.Attendant.AttendantUI;
import com.thelocalmarketplace.software.UI.Attendant.SelfCheckoutComponent;
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
		UIHardwareSimulation.startHardwareSimulationUI();
		SelfCheckoutComponent test = new SelfCheckoutComponent();
		JFrame frame = new JFrame();
		frame.setSize(350,500);
		frame.setLayout(new GridLayout(1,1));
		frame.getContentPane().add(test);
		frame.setVisible(true);
		
		
		
	}
}
