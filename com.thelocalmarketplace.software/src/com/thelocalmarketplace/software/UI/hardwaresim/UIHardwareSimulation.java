package com.thelocalmarketplace.software.UI.hardwaresim;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class UIHardwareSimulation extends JFrame {
	private static final long serialVersionUID = -6674014780520716969L;
	
	private UIHardwareSimulation() {
		super("Hardware Simulation");
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Coin system", new CoinSystemTab());
		tabbedPane.addTab("Banknote System", new BanknoteSystemTab());
		tabbedPane.addTab("Scanners", new ScannerTab());
		tabbedPane.addTab("Card", new CardTab());
		add(tabbedPane);
		pack();
		setVisible(true);
	}

	public static void startHardwareSimulationUI() {
		new UIHardwareSimulation();
	}
}
