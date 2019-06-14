package com.eigaas.imf.exception;

public class SpyNotFoundException extends RuntimeException {
    public SpyNotFoundException() {
    }

    public SpyNotFoundException(String name) {
        super("Spy: " + name +" not found.");
    }
}
