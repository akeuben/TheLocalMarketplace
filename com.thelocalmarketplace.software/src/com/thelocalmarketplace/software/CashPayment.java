package com.thelocalmarketplace.software;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.Sink;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinValidator;
import com.tdc.coin.CoinValidatorObserver;

public class CashPayment extends IPayment {
    private BigDecimal amountPaid;


    public CashPayment(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }



}