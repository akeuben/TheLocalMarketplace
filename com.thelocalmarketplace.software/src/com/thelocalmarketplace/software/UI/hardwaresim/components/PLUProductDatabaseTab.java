package com.thelocalmarketplace.software.UI.hardwaresim.components;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.UI.components.ErrorPopup;
import com.thelocalmarketplace.software.UI.components.WrappedJComponent;

import ca.ucalgary.seng300.simulation.SimulationException;

public class PLUProductDatabaseTab extends AbstractProductDatabaseTab<PLUCodedProduct> {

	private static final long serialVersionUID = 9124183042212708387L;
	
	private WrappedJComponent<JTextField> description;
	private WrappedJComponent<JTextField> plu;
	private WrappedJComponent<JTextField> pricePerGram;
	
	public PLUProductDatabaseTab() {
		super(ProductDatabases.PLU_PRODUCT_DATABASE.values());
	}

	@Override
	protected Component[] createControlPanel() {
		description = WrappedJComponent.create(JTextField.class, Integer.TYPE, 20);
		pricePerGram = WrappedJComponent.create(JTextField.class, Integer.TYPE, 20);
		plu = WrappedJComponent.create(JTextField.class, Integer.TYPE, 20);
		
		return new Component[] {
				new JLabel("Description:"),
				description,
				new JLabel("PLU:"),
				plu,
				new JLabel("Price ($/gram):"),
				pricePerGram,
		};
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends PLUCodedProduct> list, PLUCodedProduct value,
			int index, boolean isSelected, boolean cellHasFocus) {
		JPanel panel = new JPanel();
		if(isSelected) {
			panel.setBackground(list.getSelectionBackground());
			panel.setForeground(list.getSelectionForeground());
		} else {
			panel.setBackground(list.getBackground());
			panel.setForeground(list.getForeground());
		}
		panel.setLayout(new GridLayout(1, 0));
		panel.add(new JLabel(value.getDescription()));
		panel.add(new JLabel(value.getPLUCode().toString()));
		panel.add(new JLabel(formatPrice(value.getPrice())));
		return panel;
	}
	
	private String formatPrice(long price) {
		long dollars = price / 100;
		long cents = price % 100;
		
		return "$" + dollars + "." + cents + "/g";
	}
	
	private PriceLookUpCode getPLU() {
		String pluText = this.plu.getComponent().getText();

		try {
			return new PriceLookUpCode(pluText);
		} catch(SimulationException e1) {
			ErrorPopup.showError("Invalid PLU", e1.getMessage());
			return null;
		}
	}
	
	private Long getPricePerGram() {
		double price;
		try {
			price = Double.parseDouble(this.pricePerGram.getComponent().getText());
		} catch(NumberFormatException e) {
			ErrorPopup.showError("Invalid price", "The price " + this.pricePerGram.getComponent().getText() + " is not a valid price.");
			return null;
		}
		
		return (long) (price * 100);
	}

	@Override
	protected PLUCodedProduct createProduct() {
		String description = this.description.getComponent().getText();
		if(description == null || description.trim().equals("")) return null;

		PriceLookUpCode plu = getPLU();
		if(plu == null) return null;

		Long pricePerGram = getPricePerGram();
		if(pricePerGram == null) return null;
		
		return new PLUCodedProduct(plu, description, pricePerGram);

	}

	@Override
	protected void addProduct(PLUCodedProduct product) {
		ProductDatabases.PLU_PRODUCT_DATABASE.put(product.getPLUCode(), product);
	}

	@Override
	protected void removeProduct(PLUCodedProduct product) {
		ProductDatabases.PLU_PRODUCT_DATABASE.remove(product.getPLUCode());
	}

	@Override
	protected boolean isProductInDatabase(PLUCodedProduct product) {
		return ProductDatabases.PLU_PRODUCT_DATABASE.containsKey(product.getPLUCode());
	}

}
