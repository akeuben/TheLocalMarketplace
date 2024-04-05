package com.thelocalmarketplace.software.UI.hardwaresim.components;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.thelocalmarketplace.software.UI.components.ErrorPopup;
import com.thelocalmarketplace.software.UI.components.WrappedJComponent;

import ca.ucalgary.seng300.simulation.SimulationException;

public class ScannerComponent extends JPanel {

	private static final long serialVersionUID = -2927130360212530663L;

	IBarcodeScanner scanner;
	JTextField input;
	
	public ScannerComponent(IBarcodeScanner scanner) {
		this.scanner = scanner;

		input = new JTextField(10);
		WrappedJComponent<JButton> button = new WrappedJComponent<JButton>(JButton.class, new Object[] {"Scan"}, new Class<?>[] {String.class});
		button.getComponent().addActionListener(this::scan);

		setBorder(new TitledBorder("Main Scanner"));
		add(input);
		add(button);
	}

	private void scan(ActionEvent e) {
		String barcode = input.getText();
		Numeral[] digits = new Numeral[barcode.length()];
		for(int i = 0; i < barcode.length(); i++) {
			try {
				digits[i] = Numeral.valueOf(Byte.parseByte("" + barcode.charAt(i)));
			} catch(NumberFormatException e1) {
				ErrorPopup.showError("Invalid Barcode", "The barcode " + barcode + " is not a valid barcode.");
				return;
			}
		}
		try {
			Barcode bc = new Barcode(digits);
			scanner.scan(new BarcodedItem(bc, Mass.ONE_GRAM));
		} catch(SimulationException e1) {
			ErrorPopup.showError("Invalid Barcode", "The barcode must be at least one digit long!");
		}
	}
}
