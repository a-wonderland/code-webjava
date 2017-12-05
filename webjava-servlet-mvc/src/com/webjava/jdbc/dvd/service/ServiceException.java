package com.webjava.jdbc.dvd.service;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    public ServiceException(String msg) {
	super(msg);
    }

    public ServiceException(String msg, Throwable cause) {
	super(msg, cause);
    }
}
