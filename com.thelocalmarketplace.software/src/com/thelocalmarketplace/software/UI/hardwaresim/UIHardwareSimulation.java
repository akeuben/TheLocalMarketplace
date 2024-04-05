package com.thelocalmarketplace.software.UI.hardwaresim;

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
		
		stationSelector.add("Attendant Station", attendantPane);
		
		add(stationSelector);
		pack();
		setVisible(true);
	}

	public static void startHardwareSimulationUI(int machineCount) {
		new UIHardwareSimulation(machineCount);
	}
}
