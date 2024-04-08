package com.thelocalmarketplace.software.UI.user.screens;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.thelocalmarketplace.software.Software;
import com.thelocalmarketplace.software.UI.components.TransactionView;

public class ReadyForItemScreen extends AbstractUserScreen {

	private static final long serialVersionUID = 6147707410164322045L;
	
	TransactionView view;
	
	public ReadyForItemScreen(int machineID) {
		super(machineID);
		view = new TransactionView(machineID);

		setLayout(new GridLayout(1, 1));
		
		view.connect(Software.getInstance().getCurrentSession(machineID).getTransaction());
		
		redraw();
	}
	
	
	public void drawMain() {
		add(view);
	}

	@Override
	public void redraw() {
		removeAll();
		
		drawMain();
		
		revalidate();
		repaint();
	}
	
	@Override
	public void onScreenRemoved() {
		view.disconnect(Software.getInstance().getCurrentSession(machineID).getTransaction());
	}
}
