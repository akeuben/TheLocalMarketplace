package com.thelocalmarketplace.software.UI.hardwaresim;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class ScaleTab extends JPanel {

	private static final long serialVersionUID = -5582251485153083830L;

	public ScaleTab() {
		setLayout(new GridLayout(0, 2));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JPanel baggingAreaPanel = new JPanel();
		baggingAreaPanel.setBorder(new TitledBorder("Bagging Area"));
		
		JPanel baggingAreaDetailPanel = new JPanel();
		baggingAreaPanel.setLayout(new GridLayout());
	}
}
