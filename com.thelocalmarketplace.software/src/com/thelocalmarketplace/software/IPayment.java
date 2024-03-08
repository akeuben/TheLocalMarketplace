package com.thelocalmarketplace.software;

/**
 * Abstract class for methods of payment
 */

public abstract class IPayment {
    public abstract boolean processPayment(long amount);
}