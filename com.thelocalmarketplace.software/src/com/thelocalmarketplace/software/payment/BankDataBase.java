package com.thelocalmarketplace.software.payment;

import java.util.HashMap;

import com.thelocalmarketplace.hardware.external.CardIssuer;

/**
 * Singleton that represents all the possible banks (CardIssuers) that can be accessed, 
 * Is a hashmap with the keys as the type of the card and values being the associated bank 
 * ex. if the card is visa then associated cardissuer will be visa
 */

public class BankDataBase {

	private static BankDataBase instance; 
	private HashMap<String, CardIssuer> database; 
	
	
	private BankDataBase(HashMap<String, CardIssuer>  database) {
		this.database = database; 
	}
	
	public static void initialize(HashMap<String, CardIssuer> database) throws RuntimeException {
		if(instance != null){
			throw new RuntimeException("Database already exists");
		}
		
		instance = new BankDataBase(database);
	}
	
	public static BankDataBase getInstance() {
		return instance; 
	}
	
	public HashMap<String, CardIssuer> getDataBase(){
		return this.database; 
	}
	
	/**
	 * Uninitializes BankDataBase
	 * @return
	 */
	
	public static void uninitialize() {
		if(instance == null) return;
		
		instance = null;
	}
	

}
