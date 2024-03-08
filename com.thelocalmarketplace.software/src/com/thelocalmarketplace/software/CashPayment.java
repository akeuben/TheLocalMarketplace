package com.thelocalmarketplace.software;

public class CashPayment extends IPayment {
    private long amountPaid;

    public CashPayment(long amountPaid) {
        this.amountPaid = amountPaid;
    }


    /**
     *
     * @param amount given
     * @return T/F the payment has been processed
     */
    @Override
    public boolean processPayment(long amount) {
        if (amountPaid >= amount) {
            System.out.println("Cash Payment Accepted")
            return true;
        } else {
            System.out.println("Insufficent Cash Provided")
            return false;
        }

    }

}