package com.thelocalmarketplace.software;

import com.tdc.coin.CoinValidator;

/**
 * Abstract class for methods of payment
 */

public abstract class IPayment {
    public abstract boolean processPayment(long amount);
    public abstract boolean processPayment(CoinValidator cv);
}