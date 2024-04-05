package com.thelocalmarketplace.software.UI.hardwaresim;

import javax.swing.JTabbedPane;

import com.thelocalmarketplace.software.UI.hardwaresim.components.BarcodedProductDatabaseTab;
import com.thelocalmarketplace.software.UI.hardwaresim.components.PLUProductDatabaseTab;

public class ProductDatabaseTab extends AbstractAttendantTab {

	private static final long serialVersionUID = -5221044028265491112L;

	public ProductDatabaseTab() {
		super(1);
		JTabbedPane pane = new JTabbedPane();
		pane.add("Barcoded Products", new BarcodedProductDatabaseTab());
		pane.add("PLU Coded Products", new PLUProductDatabaseTab());
		add(pane);
	}

}
