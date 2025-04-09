package com.example.AuthenticationProject.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rate.limit")
public class RateLimitProperties {
    private int capacity = 5;
    private int duration = 2; // minutes
    private boolean ipBased = true;

    // Getters and setters
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isIpBased() {
        return ipBased;
    }

    public void setIpBased(boolean ipBased) {
        this.ipBased = ipBased;
    }
}