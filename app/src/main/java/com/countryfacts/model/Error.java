package com.countryfacts.model;

/**
 */

public class Error extends java.lang.Error {

    public Type type;

    public Error(Type type) {
        this.type = type;
    }

    public enum Type {
        Network,
        Empty,
        Fetch
    }
}
