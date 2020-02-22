package com.fcant.java8.stream.bean;

/**
 * Trader
 * <p>
 * encoding:UTF-8
 *
 * @author Fcant 下午 17:47:31 2020/2/22/0022
 */
public class Transaction {

    private Trader trader;
    private Integer year;
    private Integer value;

    public Trader getTrader() {
        return trader;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Transaction() {
    }

    public Transaction(Trader trader, Integer year, Integer value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "trader=" + trader +
                ", year=" + year +
                ", value=" + value +
                '}';
    }
}
