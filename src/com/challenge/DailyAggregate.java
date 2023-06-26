package com.challenge;

public class DailyAggregate {

    private double openPrice;
    private double closePrice;
    private double highestPrice;
    private double lowestPrice;
    private double dailyTradedVolume;
    private boolean oldValue;

    public DailyAggregate() {
        openPrice = 0;
        closePrice = 0;
        // ensures that any valid price encountered in the data will be greater than the initialized value
        highestPrice = Double.MIN_VALUE;
        // ensures that any valid price encountered will be smaller than the initialized value.
        lowestPrice = Double.MAX_VALUE;
        dailyTradedVolume = 0;
        oldValue = false;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(double highestPrice) {
        this.highestPrice = highestPrice;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public double getDailyTradedVolume() {
        return dailyTradedVolume;
    }

    public void setDailyTradedVolume(double dailyTradedVolume) {
        this.dailyTradedVolume = dailyTradedVolume;
    }

    public boolean isOldValue() {
        return oldValue;
    }

    public void setOldValue(boolean oldValue) {
        this.oldValue = oldValue;
    }
}
