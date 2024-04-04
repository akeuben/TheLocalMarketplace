package com.thelocalmarketplace.software.UI.hardwaresim;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.jjjwelectronics.IllegalDigitException;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.UI.components.ErrorPopup;
import com.thelocalmarketplace.software.UI.components.WrappedJComponent;

public class ScannerTab extends JPanel {

	private static final long serialVersionUID = 8792155096453005653L;
	
	private JTextField mainBarcodeField;
	private JTextField handheldBarcodeField;

	public ScannerTab() {
		setLayout(new GridLayout(0, 1));
		setBorder(new EmptyBorder(10, 10, 10, 10));

		mainBarcodeField = new JTextField(10);
		handheldBarcodeField = new JTextField(10);

		WrappedJComponent<JButton> mainScanButton = new WrappedJComponent<JButton>(JButton.class, "Scan");
		mainScanButton.getComponent().addActionListener(this::scanMainScanner);
		WrappedJComponent<JButton> handheldScanButton = new WrappedJComponent<JButton>(JButton.class, "Scan");
		handheldScanButton.getComponent().addActionListener(this::scanHandheldScanner);

		JPanel mainScannerPanel = new JPanel();
		mainScannerPanel.setBorder(new TitledBorder("Main Scanner"));
		mainScannerPanel.add(mainBarcodeField);
		mainScannerPanel.add(mainScanButton);
		
		JPanel handheldScannerPanel = new JPanel();
		handheldScannerPanel.setBorder(new TitledBorder("Handheld Scanner"));
		handheldScannerPanel.add(handheldBarcodeField);
		handheldScannerPanel.add(handheldScanButton);
		
		add(mainScannerPanel);
		add(handheldScannerPanel);
	}
	
	private void scanMainScanner(ActionEvent e) {
		IBarcodeScanner scanner = SelfCheckout.getInstance().getHardware().getMainScanner();
		scan(mainBarcodeField.getText(), scanner);
	}
	
	private void scanHandheldScanner(ActionEvent e) {
		IBarcodeScanner scanner = SelfCheckout.getInstance().getHardware().getHandheldScanner();
		scan(handheldBarcodeField.getText(), scanner);
	}
	
	private void scan(String barcode, IBarcodeScanner scanner) {
		Numeral[] digits = new Numeral[barcode.length()];
		for(int i = 0; i < barcode.length(); i++) {
			try {
				digits[i] = Numeral.valueOf(Byte.parseByte("" + barcode.charAt(i)));
			} catch(NumberFormatException e) {
				ErrorPopup.showError("Invalid Barcode", "The barcode " + barcode + " is not a valid barcode.");
				return;
			}
		}
		Barcode bc = new Barcode(digits);
		scanner.scan(new BarcodedItem(bc, Mass.ONE_GRAM));
	}
}
