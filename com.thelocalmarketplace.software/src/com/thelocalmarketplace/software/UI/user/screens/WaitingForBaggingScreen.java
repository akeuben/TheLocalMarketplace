package com.thelocalmarketplace.software.UI.user.screens;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.thelocalmarketplace.software.Software;
import com.thelocalmarketplace.software.UI.components.TransactionView;

public class WaitingForBaggingScreen extends AbstractUserScreen {

	private static final long serialVersionUID = 6147707410164322045L;
	
	TransactionView view;
	
	public WaitingForBaggingScreen(int machineID) {
		super(machineID);

		setLayout(new GridLayout(1, 1));

		view = new TransactionView(machineID);
		view.connect(Software.getInstance().getCurrentSession(machineID).getTransaction());

		redraw();
	}

	@Override
	public void redraw() {
		removeAll();
		
		add(view);
		
		revalidate();
		repaint();
	}
	
	@Override
	public void onScreenRemoved() {
		view.disconnect(Software.getInstance().getCurrentSession(machineID).getTransaction());
	}
}
