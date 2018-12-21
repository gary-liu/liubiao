package com.mimi.netty.client;

/**
 * @author liubiao
 */
public class Time {

    private final long value ;

    public Time() {
        this(System.currentTimeMillis() / 1000L);
    }

    public Time(long value) {
        this.value = value;
    }

       public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Time{" +
                "value=" + value +
                '}';
    }
}
