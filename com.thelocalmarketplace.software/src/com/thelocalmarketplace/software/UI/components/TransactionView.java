package com.thelocalmarketplace.software.UI.components;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.TitledBorder;

import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.software.Software;
import com.thelocalmarketplace.software.UI.components.TransactionView.TransactionItem.Type;
import com.thelocalmarketplace.software.payment.IPayment;
import com.thelocalmarketplace.software.payment.Transaction;
import com.thelocalmarketplace.software.payment.TransactionObserver;
import com.thelocalmarketplace.software.session.UserSession;

public class TransactionView extends JPanel implements TransactionObserver, ListCellRenderer<TransactionView.TransactionItem > {
	private static final long serialVersionUID = 2859060838426821981L;

	static class TransactionItem {
		
		public enum Type {
			BARCODED,
			PLUCODED
		}
		
		public String description;
		public String price;
		public Product product;
		public Type type;
		
		private TransactionItem(Product product, Type type, String description, String price) {
			this.description = description;
			this.price = price;
			
			this.product = product;
			this.type = type;
		}
		
		private static String formatPrice(long price) {
			long dollars = price / 100;
			long cents = price % 10;
			
			return "$" + dollars + "." + cents;
		}
		
		public static TransactionItem from(BarcodedProduct product) {
			return new TransactionItem(product, Type.BARCODED, product.getDescription(), formatPrice(product.getPrice()));
		}
		
		public static TransactionItem from(PLUCodedProduct product) {
			return new TransactionItem(product, Type.BARCODED, product.getDescription(), formatPrice(product.getPrice()));
		}
	}
	
	DefaultListModel<TransactionItem> model;
	JList<TransactionView.TransactionItem> list;
	
	private int machineID;
	
	public TransactionView(int machineID) {
		this.machineID = machineID;
		
		setLayout(new GridLayout(1, 1));
		model = new DefaultListModel<TransactionView.TransactionItem>();
		list = new JList<TransactionView.TransactionItem>(model);
		list.setCellRenderer(this);
		
		setBorder(new TitledBorder("Test"));
		
		add(list);
	}
	
	public void connect(Transaction transaction) {
		transaction.register(this);
		model.removeAllElements();
		
		for(BarcodedProduct product : transaction.getBarcodedProducts()) {
			model.addElement(TransactionItem.from(product));
		}
	}
	
	public void disconnect(Transaction transaction) {
		transaction.deregister(this);
	}

	@Override
	public void barcodedProductAdded(BarcodedProduct product) {
		model.addElement(TransactionItem.from(product));
	}

	@Override
	public void barcodedProductRemoved(BarcodedProduct product) {
		model.removeElement(TransactionItem.from(product));
	}

	@Override
	public void plucodedProductAdded(PLUCodedProduct product, Mass weight) {
		model.addElement(TransactionItem.from(product));
	}

	@Override
	public void plucodedProductRemoved(PLUCodedProduct product, Mass weight) {
		model.removeElement(TransactionItem.from(product));
	}

	@Override
	public void paymentAdded(IPayment payment) {
	}

	@Override
	public void bagAdded(Mass bagMass) {
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends TransactionItem> list, TransactionItem value,
			int index, boolean isSelected, boolean cellHasFocus) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(new JLabel(value.description), gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		panel.add(new JLabel(value.price), gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.EAST;
		panel.add(new JButton("Remove"), gbc);
		
		return panel;
	}
	
	public void deleteSelected() {
		TransactionItem item = list.getSelectedValue();
		if(item == null) return;
		
		UserSession session = Software.getInstance().getCurrentSession(machineID);
		if(item.type == Type.BARCODED) session.getUIHandler().removeItemSelected((BarcodedProduct) item.product);
	}
}