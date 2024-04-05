package com.thelocalmarketplace.software.UI.hardwaresim;

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

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class UIHardwareSimulation extends JFrame {
	private static final long serialVersionUID = -6674014780520716969L;
	
	private UIHardwareSimulation(int machineCount) {
		super("Hardware Simulation");
		
		JTabbedPane stationSelector = new JTabbedPane();
		
		for(int i = 0; i < machineCount; i++) {
			JTabbedPane hardwareSelector = new JTabbedPane();
			hardwareSelector.addTab("Coin system", new CoinSystemTab(i));
			hardwareSelector.addTab("Banknote System", new BanknoteSystemTab(i));
			hardwareSelector.addTab("Scanners", new ScannerTab(i));
			hardwareSelector.addTab("Card", new CardTab(i));
			hardwareSelector.addTab("Scales", new ScaleTab(i));
			hardwareSelector.addTab("Printer", new PrinterTab(i));
			
			stationSelector.add("Checkout " + i, hardwareSelector);
		}
		
		JTabbedPane attendantPane = new JTabbedPane();
		
		attendantPane.add("Product Database", new ProductDatabaseTab());
		attendantPane.add("Card Database", new CardIssuerTab());
		
		stationSelector.add("Attendant Station", attendantPane);
		
		add(stationSelector);
		pack();
		setVisible(true);
	}

	public static void startHardwareSimulationUI(int machineCount) {
		new UIHardwareSimulation(machineCount);
	}
}
