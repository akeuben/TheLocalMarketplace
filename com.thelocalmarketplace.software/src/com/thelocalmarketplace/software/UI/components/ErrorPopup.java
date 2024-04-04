package com.thelocalmarketplace.software.UI.components;

import javax.swing.JOptionPane;

public class ErrorPopup {
	public static void showError(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, 0, null);
	}
 }
