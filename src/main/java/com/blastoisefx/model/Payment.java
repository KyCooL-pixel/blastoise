package com.blastoisefx.model;

public class Payment {
    private double amount;

    private Method method;

    public enum Method{
        E_WALLET,
        ONLINE_BANKING
    }

    public Payment(double amount, Method method) {
        this.amount = amount;
        this.method = method;
    }

    public double getAmount() {
        return amount;
    }

    public Method getMethod() {
        return method;
    }
}
