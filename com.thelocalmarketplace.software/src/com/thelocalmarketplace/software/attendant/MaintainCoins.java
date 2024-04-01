package com.thelocalmarketplace.software.attendant;

import java.util.List;

import com.tdc.CashOverloadException;
import com.tdc.coin.Coin;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import ca.ucalgary.seng300.simulation.InvalidStateSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;

public class MaintainCoins {
	private AbstractSelfCheckoutStation station;
	
	
	/**
	 * Constructor 
	 * @param station
	 * 		The station for which coins need to be maintained
	 */
	public MaintainCoins(AbstractSelfCheckoutStation station) {
		this.station = station;
	}
	
	
	/**
	 * Allows the attendant to add coins 
	 * @param coins
	 * 		can accept multiple coins
	 * @throws CashOverloadException
	 */
	public void addCoins(Coin... coins) throws CashOverloadException {
		
		// Check if there is enough space to add the coin(s)
		if (coins.length > (station.getCoinStorage().getCapacity() - station.getCoinStorage().getCoinCount())) {
			throw new InvalidStateSimulationException("Not enough space to add coins");
		}
		
		// First check if the station is disabled, (Precondition: The station is currently disabled)
		if (station.getCoinStorage().isDisabled()) {
			station.getCoinStorage().load(coins);
		} else {
			throw new InvalidStateSimulationException("Disable the station before adding coins");
		}
	}
	
	
	/**
	 * Allows the attendant to remove coins
	 * @param quantityToRemove
	 * @return
	 * 		List of coins that were removed
	 * @throws SimulationException
	 * @throws CashOverloadException
	 */
	public List<Coin> removeCoins(int quantityToRemove) throws SimulationException, CashOverloadException {
		List<Coin> removed;
		
		if (quantityToRemove > station.getCoinStorage().getCoinCount()) {
			throw new InvalidStateSimulationException("Requested quantity to remove exceeds current quantity");
		}
		
		
		if ((station.getCoinStorage().isDisabled()) && (station.getCoinStorage().getCoinCount() >= quantityToRemove)) {
			removed = station.getCoinStorage().unload();
			int coinsToReturn = removed.size() - quantityToRemove;
			
			for (int i=0; i<coinsToReturn; i++) {
				station.getCoinStorage().load(removed.get(removed.size() - 1));
				removed.remove(removed.size() - 1);
			}
			
		} else {
			throw new InvalidStateSimulationException("Disable the station before removing coins");
		}
		
		return removed;
	}

}
