package com.webjava.jdbc.connection.exception;

public class ConnectionException extends Exception {

    private static final long serialVersionUID = 1L;

    public ConnectionException(String msg) {
	super(msg);
    }

    public ConnectionException(String msg, Throwable cause) {
	super(msg, cause);
    }
}
