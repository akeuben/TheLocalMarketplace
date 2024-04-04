package com.thelocalmarketplace.software.UI.hardwaresim;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.coin.Coin;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.CoinTray;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.UI.components.ErrorPopup;

import ca.ucalgary.seng300.simulation.SimulationException;

public class CoinSystemTab extends JPanel {
	private static final long serialVersionUID = -7616750139837556826L;
	
	private DefaultListModel<Coin> collectedCoinModel;
	private Map<BigDecimal, JLabel> dispenserLabels;
	
	private JLabel countLabel;

	public CoinSystemTab() {
		setLayout(new GridLayout(0, 2));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		
		dispenserLabels = new HashMap<BigDecimal, JLabel>();
		
		JPanel coinSlotPanel = new JPanel();
		coinSlotPanel.setLayout(new FlowLayout());
		
		JPanel coinDispenserPanel = new JPanel();
		coinDispenserPanel.setLayout(new GridLayout(0, 4));
		
		JScrollPane coinSlotScrollPane = new JScrollPane(coinSlotPanel);
		coinSlotScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		coinSlotScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		coinSlotScrollPane.setBorder(new TitledBorder("Coin Slot"));
		
		JScrollPane coinDispenserScrollPane = new JScrollPane(coinDispenserPanel);
		coinDispenserScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		coinDispenserScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		coinDispenserScrollPane.setBorder(new TitledBorder("Coin Dispensers"));
		
		for(BigDecimal denomination : SelfCheckout.getInstance().getConfiguration().getCoinDenominations()) {
			JButton btn = new JButton("$" + denomination.toPlainString());
			btn.addActionListener((e) -> this.insertCoin(denomination));
			coinSlotPanel.add(btn);
			
			JLabel label = new JLabel("$" + denomination.toPlainString());
			JLabel amount = new JLabel("0");
			JButton reloadBtn = new JButton("reload");
			reloadBtn.addActionListener((e) -> this.reloadDispenser(denomination));
			JButton emitBtn = new JButton("emit");
			emitBtn.addActionListener((e) -> this.emitDispenser(denomination));
			coinDispenserPanel.add(label);
			coinDispenserPanel.add(amount);
			coinDispenserPanel.add(reloadBtn);
			coinDispenserPanel.add(emitBtn);
			dispenserLabels.put(denomination, amount);
		}
		updateCoinDispensers();
		
		add(coinSlotScrollPane);
		
		JPanel coinTrayPanel = new JPanel();
		coinTrayPanel.setBorder(new TitledBorder("Coin Tray"));
		coinTrayPanel.setLayout(new GridLayout(1, 2));
		collectedCoinModel = new DefaultListModel<Coin>();
		JList<Coin> collectedCoinList = new JList<>(collectedCoinModel);
		JScrollPane collectedCoinScrollPane = new JScrollPane(collectedCoinList);
		JButton collectButton = new JButton("Collect Coins");
		collectButton.addActionListener(this::updateCoinTray);
		
		coinTrayPanel.add(collectedCoinScrollPane);
		coinTrayPanel.add(collectButton);
		
		add(coinTrayPanel);
		
		add(coinDispenserScrollPane);
		
		JPanel coinStoragePanel = new JPanel();
		coinStoragePanel.setLayout(new GridLayout(0, 2));
		coinStoragePanel.setBorder(new TitledBorder("Coin Storage"));
		coinStoragePanel.add(new JLabel("Coin Count: "));
		countLabel = new JLabel("");
		updateStorageCount();
		coinStoragePanel.add(countLabel);
		coinStoragePanel.add(new JLabel("Coin Capacity: "));
		JLabel capcityLabel = new JLabel("" + SelfCheckout.getInstance().getConfiguration().coinStorageUnitCapacity);
		coinStoragePanel.add(capcityLabel);
		JButton emptyButton = new JButton("Empty Storage");
		emptyButton.addActionListener((e) -> emptyStorageUnit());
		coinStoragePanel.add(emptyButton);
		
		add(coinStoragePanel);
	}
	
	public void insertCoin(BigDecimal denomination) {
		Currency currency = SelfCheckout.getInstance().getConfiguration().getCurrency();
		Coin coin = new Coin(currency, denomination);
		try {
			SelfCheckout.getInstance().getHardware().getCoinSlot().receive(coin);
		} catch (DisabledException | RuntimeException e) {
			ErrorPopup.showError("Failed to insert coin", "The coin slot is disabled.");
		} catch(CashOverloadException e) {
			ErrorPopup.showError("Failed to insert coin", "The coin slot is overloaded.");
		}
	}
	
	public void updateCoinTray(ActionEvent e) {
		CoinTray tray = SelfCheckout.getInstance().getHardware().getCoinTray();
		List<Coin> collected = tray.collectCoins();
		collectedCoinModel.clear();
		collectedCoinModel.addAll(collected);
	}
	
	public void reloadDispenser(BigDecimal denomination) {
		Currency currency = SelfCheckout.getInstance().getConfiguration().getCurrency();
		ICoinDispenser dispenser = SelfCheckout.getInstance().getHardware().getCoinDispensers().get(denomination);
		while(dispenser.hasSpace()) {
			try {
				dispenser.load(new Coin(currency, denomination));
			} catch (SimulationException | CashOverloadException e) {
				// This should never happen
				e.printStackTrace();
			}
		}
		updateCoinDispensers();
	}
	
	public void emitDispenser(BigDecimal denomination) {
		ICoinDispenser dispenser = SelfCheckout.getInstance().getHardware().getCoinDispensers().get(denomination);
		
		try {
			dispenser.emit();
		} catch (DisabledException e) {
			ErrorPopup.showError("Failed to Dispsense Coin", "The coin dispenser is disabled.");
		} catch (CashOverloadException e) {
			ErrorPopup.showError("Failed to Dispsense Coin", "The coin dispenser is overloaded.");
		} catch (NoCashAvailableException e) {
			ErrorPopup.showError("Failed to Dispsense Coin", "The coin dispenser is empty!");
		}
		updateCoinDispensers();
	}
	
	public void updateCoinDispensers() {
		for(BigDecimal denomination : dispenserLabels.keySet()) {
			ICoinDispenser dispenser = SelfCheckout.getInstance().getHardware().getCoinDispensers().get(denomination);
			int count = dispenser.size();
			dispenserLabels.get(denomination).setText(count + "");
		}
	}
	
	public void updateStorageCount() {
		int count = SelfCheckout.getInstance().getHardware().getCoinStorage().getCoinCount();
		countLabel.setText("" + count);
	}
	
	public void emptyStorageUnit() {
		SelfCheckout.getInstance().getHardware().getCoinStorage().unload();
		updateStorageCount();
	}
}
