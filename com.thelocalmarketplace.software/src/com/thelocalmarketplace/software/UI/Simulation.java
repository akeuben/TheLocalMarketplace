package com.thelocalmarketplace.software.UI;

import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.thelocalmarketplace.hardware.AttendantStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration;
import com.thelocalmarketplace.software.Software;
import com.thelocalmarketplace.software.UI.Attendant.SelfCheckoutComponent;
import com.thelocalmarketplace.software.UI.hardwaresim.UIHardwareSimulation;

public class Simulation {
	public static void main(String[] args) {
		Software.initialize(new SelfCheckoutConfiguration(
			SelfCheckoutStationGold.class,
			AttendantStation.class,
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
		), 2);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// this should never happen!
			e.printStackTrace();
		}
		UIHardwareSimulation.startHardwareSimulationUI(2);
		JFrame test = new JFrame();
		test.setSize(475, 600);
		test.setLayout(new GridLayout(1,1));
		test.add(new SelfCheckoutComponent());
		test.setVisible(true);
		
	}
}