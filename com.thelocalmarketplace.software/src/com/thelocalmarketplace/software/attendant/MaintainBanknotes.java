package com.thelocalmarketplace.software.attendant;

/**
 * SENG 300 Project - Group 1:
 * 
 * Avery Keuben - 30170731
 * Moiz Siddiqui - 30150291
 * Ammaar Melethil - 30141956
 * Joey Fisher - 30105628
 * Ethan Pangilinan - 30179143
 * Joshua Kraft - 30171525
 * Nathan Vaters - 30121908
 * Max Butcher - 30149202
 * Neeraj Ghansela - 30157473
 * Ansel Sulejmani - 30178521
 * Suleman Basit - 30132816
 * Jacob Boyden - 30193220
 * Cheshta Sharma - 30064538
 * Callum Bates - 30188601
 * Armughan Mustafa - 30154601
 * Connor Ell - 30073291
 * Saif Farag - 30195046
 * Ivan Agalakov - 30172107
 * Samuel Turner - 10064857
 * Stephanie Sevilla - 30176781
 * Winston Wang - 30185321
 */

import java.util.List;

import com.tdc.CashOverloadException;
import com.tdc.banknote.Banknote;


import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;


public class MaintainBanknotes {
	private AbstractSelfCheckoutStation station;
	
	/**
	 * Constructor
	 * @param station
	 * 		The station for which bank notes need to be maintained
	 */
	public MaintainBanknotes(AbstractSelfCheckoutStation station) {
		this.station = station;
	}
	
	/**
	 * Allows the attendant to add bank notes
	 * @param banknotes
	 * 		can accept multiple banknotes
	 * @throws SimulationException
	 * @throws CashOverloadException
	 */
	public void addBanknotes(Banknote... banknotes) throws SimulationException, CashOverloadException {
		
		// Check if there is enough space to add the bank note(s)
		if (banknotes.length > (station.getBanknoteStorage().getCapacity() - station.getBanknoteStorage().getBanknoteCount())) {
			throw new InvalidStateSimulationException("Not enough space to add banknotes");
		}
		
		// Check if the station is disabled, then proceed to add bank note(s)
		if (station.getBanknoteStorage().isDisabled()) {
			station.getBanknoteStorage().load(banknotes);
		} else {
			throw new InvalidStateSimulationException("Disable the station before adding banknotes");
		}	
	}
	
	/**
	 * Allows the attendant to remove bank notes
	 * @param quantityToRemove
	 * @return
	 * 		List of bank notes that were removed
	 * @throws SimulationException
	 * @throws CashOverloadException
	 */
	public List<Banknote> removeBanknotes(int quantityToRemove) throws SimulationException, CashOverloadException {
		List<Banknote> removed;
		
		// First Check if there are enough bank notes to remove 
		if (quantityToRemove > station.getBanknoteStorage().getBanknoteCount()) {
			throw new InvalidStateSimulationException("Requested quantity to remove exceeds current quantity");
		}
		
		// Check if station is disabled then proceed to remove bank notes
		if ((station.getBanknoteStorage().isDisabled()) && (station.getBanknoteStorage().getBanknoteCount() >= quantityToRemove)) {
			// Unload all bank notes then add back ones that need not be removed
			removed = station.getBanknoteStorage().unload();
			int banknotesToReturn = removed.size() - quantityToRemove;
			
			for (int i=0; i<banknotesToReturn; i++) {
				station.getBanknoteStorage().load(removed.get(removed.size() - 1));
				removed.remove(removed.size() - 1);
			}
		} else {
			throw new InvalidStateSimulationException("Disable the station before removing banknotes");
		}
	
		return removed;
	}
	

}
