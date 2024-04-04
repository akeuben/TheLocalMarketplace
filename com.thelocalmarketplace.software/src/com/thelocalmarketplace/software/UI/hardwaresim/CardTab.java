package com.thelocalmarketplace.software.UI.hardwaresim;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.jjjwelectronics.card.Card;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.UI.components.ErrorPopup;

public class CardTab extends JPanel {
	
	private enum CardKind {
		VISA, MASTERCARD, AMEX, DEBIT
	}

	private static final long serialVersionUID = 1881179373704340614L;

	JComboBox<CardKind> cardKindField; 
	JTextField cardNumberField; 
	JTextField cardholderField; 
	JTextField ccvField; 
	JTextField pinField; 
	JCheckBox tapEnabledField; 
	JCheckBox hasChipField; 

	public CardTab() {
		setLayout(new GridLayout(0, 1));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JPanel cardInputPanel = new JPanel();
		cardInputPanel.setBorder(new TitledBorder("Card Details"));
		cardInputPanel.setLayout(new GridLayout(0, 2));
		cardKindField = new JComboBox<CardTab.CardKind>(CardKind.values());
		cardNumberField = new JTextField(10);
		cardholderField = new JTextField(10);
		ccvField = new JTextField(3);
		pinField = new JTextField(4);
		tapEnabledField = new JCheckBox("Tap Enabled");
		hasChipField = new JCheckBox("Has Chip");
		
		cardInputPanel.add(new JLabel("Card Kind:"));
		cardInputPanel.add(cardKindField);
		cardInputPanel.add(new JLabel("Card Number:"));
		cardInputPanel.add(cardNumberField);
		cardInputPanel.add(new JLabel("Cardholder:"));
		cardInputPanel.add(cardholderField);
		cardInputPanel.add(new JLabel("CCV:"));
		cardInputPanel.add(ccvField);
		cardInputPanel.add(new JLabel("Pin:"));
		cardInputPanel.add(pinField);
		cardInputPanel.add(tapEnabledField);
		cardInputPanel.add(hasChipField);
		
		add(cardInputPanel);
		
		JPanel cardPayPanel = new JPanel();
		cardPayPanel.setBorder(new TitledBorder("Pay"));
		JButton swipeButton = new JButton("Swipe");
		swipeButton.addActionListener(this::swipe);
		JButton insertButton = new JButton("Insert");
		insertButton.addActionListener(this::insert);
		JButton tapButton = new JButton("Tap");
		tapButton.addActionListener(this::tap);
		cardPayPanel.add(swipeButton);
		cardPayPanel.add(insertButton);
		cardPayPanel.add(tapButton);
		
		add(cardPayPanel);
	}
	
	private Card getEnteredCard() {
		CardKind kind = (CardKind) cardKindField.getSelectedItem();
		String cardholder = cardholderField.getText();
		String number = cardNumberField.getText();
		String ccv = ccvField.getText();
		String pin = pinField.getText();
		boolean tap = tapEnabledField.isSelected();
		boolean chip = hasChipField.isSelected();
		Card card = new Card(kind.toString(), cardholder, number, ccv, pin, tap, chip);
		return card;
	}
	
	private void swipe(ActionEvent e) {
		Card card = getEnteredCard();
		try {
			SelfCheckout.getInstance().getHardware().getCardReader().swipe(card);
		} catch (IOException | RuntimeException e1) {
			ErrorPopup.showError("Failed to swipe card", e1.getStackTrace().toString());
		}
	}
	
	private void insert(ActionEvent e) {
		Card card = getEnteredCard();
		String pin = JOptionPane.showInputDialog("Enter Pin");
		try {
			SelfCheckout.getInstance().getHardware().getCardReader().insert(card, pin);
		} catch (IOException | RuntimeException e1) {
			StringBuilder sb = new StringBuilder();
			for(StackTraceElement elem : e1.getStackTrace()) {
				sb.append(elem.toString());
				sb.append("\n");
			}
			ErrorPopup.showError("Failed to insert card", sb.toString());
		} finally {
			SelfCheckout.getInstance().getHardware().getCardReader().remove();
		}
	}
	
	private void tap(ActionEvent e) {
		Card card = getEnteredCard();
		try {
			SelfCheckout.getInstance().getHardware().getCardReader().tap(card);
		} catch (IOException | RuntimeException e1) {
			ErrorPopup.showError("Failed to tap card", e1.getStackTrace().toString());
		}
	}
}
