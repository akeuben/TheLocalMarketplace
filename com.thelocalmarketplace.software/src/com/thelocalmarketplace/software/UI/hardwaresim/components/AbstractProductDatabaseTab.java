package com.thelocalmarketplace.software.UI.hardwaresim.components;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.TitledBorder;

import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.software.UI.components.ErrorPopup;
import com.thelocalmarketplace.software.UI.hardwaresim.AbstractAttendantTab;

public abstract class AbstractProductDatabaseTab<T extends Product> extends AbstractAttendantTab implements ListCellRenderer<T> {

	private static final long serialVersionUID = -7987102304845860524L;

	JList<T> productList;
	DefaultListModel<T> productListModel;
	
	public AbstractProductDatabaseTab(Collection<T> initialList) {
		super(2);
		
		productListModel = new DefaultListModel<T>();
		productListModel.addAll(initialList);
		productList = new JList<T>(productListModel);
		productList.setCellRenderer(this);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1, 20, 20));
		panel.setBorder(new TitledBorder("Create new Entry"));
		JPanel entries = new JPanel();
		entries.setLayout(new GridLayout(0, 2, 20, 20));
		for(Component comp : createControlPanel()) {
			entries.add(comp);
		}
		panel.add(entries);
		JButton addProduct = new JButton("Add to Database");
		addProduct.addActionListener(this::handleAddProduct);
		JButton removeProduct = new JButton("Remove Selected");
		removeProduct.addActionListener(this::handleRemoveProduct);
		
		panel.add(addProduct);
		panel.add(removeProduct);
		
		add(productList);
		add(panel);
		
	}
	
	protected abstract Component[] createControlPanel();
	
	protected abstract T createProduct();
	protected abstract boolean isProductInDatabase(T product);
	
	protected abstract void addProduct(T product);
	protected abstract void removeProduct(T product);
	
	private void handleAddProduct(ActionEvent e) {
		T product = createProduct();
		if(product == null) {
			ErrorPopup.showError("Invalid Product", "The entered product information is invalid");
			return;
		}
		if(isProductInDatabase(product)) {
			ErrorPopup.showError("Invalid Product", "The product is already in the database.");
			return;
		}
		productListModel.addElement(product);
		addProduct(product);
	}
	
	private void handleRemoveProduct(ActionEvent e) {
		T product = productList.getSelectedValue();
		if(product == null) {
			ErrorPopup.showError("Invalid Product", "Please select a product to remove");
			return;
		}
		productListModel.removeElement(product);
		removeProduct(product);
	}
	
}
