package com.btp.smartvest;

public class Event {
    private float temp;
    private long timestamp;

    public Event() {

    }

    public float getTemp() {
        return (float)(-0.0024 * Math.pow(temp,3) + 0.1896 * Math.pow(temp, 2) - 2.3227 * temp - 21.6311) -2;
    }
    public void setTemp(float temp) {
        this.temp = temp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
